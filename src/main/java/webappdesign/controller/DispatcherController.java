package webappdesign.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import webappdesign.action.LoginAction;
import webappdesign.action.SignUpAction;
import webappdesign.action.TransformFileToXSLTAction;
import webappdesign.action.UploadFileAction;
import webappdesign.enums.Directories;
import webappdesign.enums.Pages;
import webappdesign.form.UserForm;
import webappdesign.model.Article;
import webappdesign.model.UploadedFile;
import webappdesign.model.User;
import webappdesign.model.data_access_object.file.FileDAO;
import webappdesign.model.data_access_object.file.IFileDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "DispatcherController", urlPatterns = {"/*"})
public class DispatcherController implements Filter {
    private User currentUser;
    private List<UploadedFile> uploadedFiles;
    private IFileDAO fileDAO;
    private Article article;
    private List<Article> articleList;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        forwardToPage(req, resp, chain);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private void forwardToPage(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String pageURI = uri.substring(lastIndex + 1);

        String dispatchUrl = null;
        if ("login".equals(pageURI)) {
            dispatchUrl = Pages.LOGIN_PAGE.getPage();
        } else if ("loggedIn".equals(pageURI)) {
            UserForm userForm = new UserForm();
            userForm.setEmail(request.getParameter("email"));
            userForm.setPassword(request.getParameter("password"));

            User newUser = new User();
            newUser.setEmail(userForm.getEmail());
            newUser.setPassword(userForm.getPassword());

            LoginAction loginAction = new LoginAction();
            currentUser = loginAction.login(newUser);

            if (currentUser != null) {
                if (currentUser.getRole().equals("super")) {
                    dispatchUrl = Pages.SUPER_ADMIN_PAGE.getPage();
                } else if (currentUser.getRole().equals("admin")) {
                    dispatchUrl = Pages.ADMIN_PAGE.getPage();
                } else if (currentUser.getRole().equals("basic")) {
                    req.setAttribute("articles", articleList);

                    dispatchUrl = Pages.BASIC_USER_PAGE.getPage();
                }
            } else {
                req.setAttribute("hiddenFieldLogin", "Make sure you inserted the right email and password.");

                dispatchUrl = Pages.LOGIN_PAGE.getPage();
            }
        } else if ("super".equals(pageURI)) {
            if (currentUser == null) {
                req.setAttribute("hiddenFieldLogin", "You must login first.");

                dispatchUrl = Pages.LOGIN_PAGE.getPage();
            } else if (currentUser.getRole().equals("admin")) {
                dispatchUrl = Pages.SUPER_ADMIN_PAGE.getPage();
            } else {
                req.setAttribute("hiddenFieldLogin", "You must login first.");

                dispatchUrl = Pages.LOGIN_PAGE.getPage();
            }
        } else if ("admin".equals(pageURI)) {
            if (currentUser == null) {
                req.setAttribute("hiddenFieldLogin", "You must login first.");

                dispatchUrl = Pages.LOGIN_PAGE.getPage();
            } else if (currentUser.getRole().equals("admin")) {
                dispatchUrl = Pages.ADMIN_PAGE.getPage();
            } else {
                req.setAttribute("hiddenFieldLogin", "You must login first.");

                dispatchUrl = Pages.LOGIN_PAGE.getPage();
            }
        } else if ("basic".equals(pageURI)) {
            if (currentUser == null) {
                req.setAttribute("hiddenFieldLogin", "You must login first.");

                dispatchUrl = Pages.LOGIN_PAGE.getPage();
            } else if (currentUser.getRole().equals("basic")){
                req.setAttribute("articles", articleList);

                dispatchUrl = Pages.BASIC_USER_PAGE.getPage();
            } else {
                req.setAttribute("hiddenFieldLogin", "You must login first.");

                dispatchUrl = Pages.LOGIN_PAGE.getPage();
            }
        } else if ("sign-up".equals(pageURI)) {
            dispatchUrl = Pages.SIGN_UP_PAGE.getPage();
        } else if ("signed".equals(pageURI)) {
            UserForm userForm = new UserForm();
            userForm.setEmail(request.getParameter("email"));
            userForm.setPassword(request.getParameter("password"));

            if (req.getParameter("password2").equals(userForm.getPassword())) {
                User newUser = new User();
                newUser.setEmail(userForm.getEmail());
                newUser.setPassword(userForm.getPassword());
                if (newUser.getEmail().startsWith("admin")) {
                    newUser.setRole("admin");
                } else {
                    newUser.setRole("basic");
                }

                SignUpAction signUpAction = new SignUpAction();
                signUpAction.signUp(newUser);

                currentUser = null;
                dispatchUrl = Pages.LOGIN_PAGE.getPage();
            } else {
                req.setAttribute("hiddenFieldSignUp", "Passwords are not the same.");

                dispatchUrl = Pages.SIGN_UP_PAGE.getPage();
            }
        } else if ("upload".equals(pageURI)) {
            dispatchUrl = Pages.UPLOAD_FILE_PAGE.getPage();
        } else if ("uploaded".equals(pageURI)) {
            ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
            try {
                List<FileItem> multiFiles = servletFileUpload.parseRequest(request);

                UploadFileAction uploadFileAction = new UploadFileAction();
                uploadFileAction.uploadFiles(multiFiles);
            } catch (Exception e) {
                e.printStackTrace();
            }

            fileDAO = FileDAO.getInstance();
            uploadedFiles = fileDAO.findAll();

            request.setAttribute("uploadedFiles", uploadedFiles);

            dispatchUrl = Pages.UPLOADED_FILES_PAGE.getPage();
        } else if ("table".equals(pageURI)) {
            fileDAO = FileDAO.getInstance();
            uploadedFiles = fileDAO.findAll();

            request.setAttribute("uploadedFiles", uploadedFiles);

            dispatchUrl = Pages.UPLOADED_FILES_PAGE.getPage();
        } else if ("transform".equals(pageURI)) {
            TransformFileToXSLTAction transformFileToXSLTAction = new TransformFileToXSLTAction();
            try {
                String uploadedFile = req.getParameter("transformFile");
                article = transformFileToXSLTAction.transform(uploadedFile);

                if (articleList == null) {
                    articleList = new ArrayList<>();
                }

                articleList.add(article);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        } else if ("article".equals(pageURI)) {
            String articleName = req.getParameter("articleName");

            article = new Article();
            article.setArticleName(articleName);
        } else if ("addAdmin".equals(pageURI)) {
            dispatchUrl = Pages.SIGN_UP_PAGE.getPage();
        }

        if (dispatchUrl != null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(dispatchUrl);
            requestDispatcher.forward(req, resp);
        } else {
            if ("transform".equals(pageURI) || "article".equals(pageURI)) {
                findFileAndView(article.getArticleName(), request, response);
            } else {
                chain.doFilter(req, resp);
            }
        }
    }

    private void findFileAndView(String fileName, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        File articlesFolder = new File(Directories.ARTICLES_PATH.getDirectory());
        File[] articles = articlesFolder.listFiles();

        for (File file : articles) {
            if (file.getName().equals(fileName)) {
                FileReader fileReader = new FileReader(file.getAbsoluteFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    writer.println(line);
                }

                bufferedReader.close();
                fileReader.close();
            }
        }
    }

}
