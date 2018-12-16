package webappdesign.controller;

import webappdesign.action.*;
import webappdesign.enums.*;
import webappdesign.form.UserForm;
import webappdesign.model.*;
import webappdesign.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@WebFilter(filterName = "DispatcherController", urlPatterns = {"/*"})
public class DispatcherController implements Filter {
    private User currentUser;
    private Article article;
    public static List<Article> articleList;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        forwardToPage(req, resp, chain);
    }

    public void init(FilterConfig config) {

    }

    private void forwardToPage(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String pageURI = getURIPage(request);

        String dispatchUrl = getDispatcherPage(pageURI, request);

        goToPage(dispatchUrl, request, response, chain, pageURI);
    }

    private String getURIPage(HttpServletRequest request) {
        String fullURI = getFullURI(request);

        return StringUtil.getURIPageFromFullURI(fullURI);
    }

    private String getFullURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    private String getDispatcherPage(String pageURI, HttpServletRequest request) {
        if (StringUtil.isURI(PageURI.LOGIN_URI.getPageURI(), pageURI)) {
            return Page.LOGIN_PAGE.getPage();
        } else if (StringUtil.isURI(PageURI.LOGGED_IN.getPageURI(), pageURI)) {
            UserForm userForm = new UserForm();
            userForm.setEmail(ParameterUtil.getParameter(Parameter.EMAIL_PARAMETER.getParameter(), request));
            userForm.setPassword(ParameterUtil.getParameter(Parameter.PASSWORD_PARAMETER.getParameter(), request));

            User newUser = new User();
            newUser.setEmail(userForm.getEmail());
            newUser.setPassword(userForm.getPassword());

            ActionContext actionContext = new ActionContext(new ActionLogin());
            currentUser = (User) actionContext.executeAction(newUser);

            if (currentUser != null) {
                if (isSuperAdmin()) {
                    return Page.SUPER_ADMIN_PAGE.getPage();
                } else if (isAdmin()) {
                    return Page.ADMIN_PAGE.getPage();
                } else if (isBasicUser()) {
                    AttributeUtil.setAttribute(Attribute.ARTICLES_ATTRIBUTE.getAttribute(), articleList, request);

                    return Page.BASIC_USER_PAGE.getPage();
                }
            } else {
                ErrorHandlerUtil.setLoginInsertionError(request);

                return Page.LOGIN_PAGE.getPage();
            }
        } else if (StringUtil.isURI(PageURI.SUPER_ADMIN_URI.getPageURI(), pageURI)) {
            if (currentUser == null) {
                ErrorHandlerUtil.setLoginFirstError(request);

                return Page.LOGIN_PAGE.getPage();
            } else if (isSuperAdmin()) {
                return Page.SUPER_ADMIN_PAGE.getPage();
            } else {
                ErrorHandlerUtil.setLoginFirstError(request);

                return Page.LOGIN_PAGE.getPage();
            }
        } else if (StringUtil.isURI(PageURI.ADMIN_URI.getPageURI(), pageURI)) {
            if (currentUser == null) {
                ErrorHandlerUtil.setLoginFirstError(request);

                return Page.LOGIN_PAGE.getPage();
            } else if (isAdmin()) {
                return Page.ADMIN_PAGE.getPage();
            } else {
                ErrorHandlerUtil.setLoginFirstError(request);

                return Page.LOGIN_PAGE.getPage();
            }
        } else if (StringUtil.isURI(PageURI.BASIC_USER_URI.getPageURI(), pageURI)) {
            if (currentUser == null) {
                ErrorHandlerUtil.setLoginFirstError(request);

                return Page.LOGIN_PAGE.getPage();
            } else if (isBasicUser()){
                AccessControlUtil.viewArticles(currentUser, request);

                return Page.BASIC_USER_PAGE.getPage();
            } else {
                ErrorHandlerUtil.setLoginFirstError(request);

                return Page.LOGIN_PAGE.getPage();
            }
        } else if (StringUtil.isURI(PageURI.SIGN_UP_URI.getPageURI(), pageURI)) {
            return Page.SIGN_UP_PAGE.getPage();
        } else if (StringUtil.isURI(PageURI.SIGNED_URI.getPageURI(), pageURI)) {
            UserForm userForm = new UserForm();
            userForm.setEmail(ParameterUtil.getParameter(Parameter.EMAIL_PARAMETER.getParameter(), request));
            userForm.setPassword(ParameterUtil.getParameter(Parameter.PASSWORD_PARAMETER.getParameter(), request));

            if (StringUtil.areStringsEqual(ParameterUtil.getParameter(Parameter.PASSWORD2_PARAMETER.getParameter(),
                    request), userForm.getPassword())) {
                User newUser = new User();
                newUser.setEmail(userForm.getEmail());
                newUser.setPassword(userForm.getPassword());

                if (currentUser != null && isSuperAdmin()) {
                    newUser.setRole("admin");
                } else {
                    newUser.setRole("basic");
                }

                ActionContext actionContext = new ActionContext(new ActionSignUp());
                actionContext.executeAction(newUser);

                currentUser = null;
                return Page.LOGIN_PAGE.getPage();
            } else {
                ErrorHandlerUtil.setSignUpPasswordNotSameError(request);

                return Page.SIGN_UP_PAGE.getPage();
            }
        } else if (StringUtil.isURI(PageURI.UPLOAD_URI.getPageURI(), pageURI)) {
            return Page.UPLOAD_FILE_PAGE.getPage();
        } else if (StringUtil.isURI(PageURI.UPLOADED_URI.getPageURI(), pageURI)) {
            AccessControlUtil.uploadFile(currentUser, request);

            return Page.UPLOADED_FILES_PAGE.getPage();
        } else if (StringUtil.isURI(PageURI.UPLOADED_FILES_URI.getPageURI(), pageURI)) {
            AccessControlUtil.viewUploadedFiles(currentUser, request);

            return Page.UPLOADED_FILES_PAGE.getPage();
        } else if (StringUtil.isURI(PageURI.TRANSFORM_TO_XSLT.getPageURI(), pageURI)) {
            String uploadedFile = ParameterUtil.getParameter(Parameter.TRANSFORM_FILE_PARAMETER.getParameter(), request);

            ActionContext actionContext = new ActionContext(new ActionTransformFileToXSLT());
            article = (Article) actionContext.executeAction(uploadedFile);

            if (articleList == null) {
                articleList = new ArrayList<>();
            }

            articleList.add(article);
        } else if (StringUtil.isURI(PageURI.ARTICLES_URI.getPageURI(), pageURI)) {
            String articleName = ParameterUtil.getParameter(Parameter.ARTICLE_NAME_PARAMETER.getParameter(), request);

            article = new Article();
            article.setArticleName(articleName);
        } else if (StringUtil.isURI(PageURI.ADD_ADMIN_URI.getPageURI(), pageURI)) {
            return Page.SIGN_UP_PAGE.getPage();
        }

        return null;
    }

    private boolean isSuperAdmin() {
        return AccessControlUtil.isSuperAdmin(currentUser);
    }

    private boolean isAdmin() {
        return AccessControlUtil.isAdmin(currentUser);
    }

    private boolean isBasicUser() {
        return AccessControlUtil.isBasicUser(currentUser);
    }



    private void goToPage(String dispatchURL, HttpServletRequest request, HttpServletResponse response,
                          FilterChain chain, String pageURI) throws IOException, ServletException {
        if (dispatchURL != null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchURL);
            requestDispatcher.forward(request, response);
        } else {
            if (StringUtil.isURI(PageURI.TRANSFORM_TO_XSLT.getPageURI(), pageURI) || StringUtil.isURI(
                    PageURI.ARTICLES_URI.getPageURI(), pageURI)) {
                findFileAndView(article.getArticleName(), response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    private void findFileAndView(String fileName, HttpServletResponse response)
            throws IOException {
        File articlesFolder = new File(Directory.ARTICLES_PATH.getDirectory());
        File[] articles = articlesFolder.listFiles();

        for (File file : Objects.requireNonNull(articles)) {
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
