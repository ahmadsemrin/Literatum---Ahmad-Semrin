package webappdesign.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import webappdesign.action.LoginAction;
import webappdesign.action.UploadFileAction;
import webappdesign.form.UserForm;
import webappdesign.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@WebFilter(filterName = "DispatcherFilter", urlPatterns = {"/*"})
public class DispatcherFilter implements Filter {
    User currentUser;

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
            dispatchUrl = "/jsp/login_page/login.jsp";
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
                if (currentUser.getRole().equals("admin")) {
                    dispatchUrl = "/jsp/wat_page/wat.jsp";
                } else if (currentUser.getRole().equals("super")) {
                    dispatchUrl = "/jsp/wat_page/wat.jsp";
                } else if (currentUser.getRole().equals("basic")) {
                    dispatchUrl = "/jsp/upload_page/upload.jsp";
                }
            } else {
                req.setAttribute("hiddenField", "Make sure you inserted the right email and password.");

                dispatchUrl = "/jsp/login_page/login.jsp";
            }
        } else if ("admin".equals(pageURI)) {
            dispatchUrl = "/jsp/wat_page/wat.jsp";
        } else if ("sign-up".equals(pageURI)) {
            dispatchUrl = "/jsp/sign_up_page/sign_up.jsp";
        } else if ("upload".equals(pageURI)) {
            dispatchUrl = "/jsp/upload_file_page/upload.jsp";
        } else if ("uploaded".equals(pageURI)) {
            ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
            try {
                List<FileItem> multiFiles = servletFileUpload.parseRequest(request);

                UploadFileAction uploadFileAction = new UploadFileAction();
                uploadFileAction.uploadFiles(multiFiles);
            } catch (Exception e) {
                e.printStackTrace();
            }

            dispatchUrl = "/jsp/upload_file_page/uploaded.jsp";
        }

        if (dispatchUrl != null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(dispatchUrl);
            requestDispatcher.forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

}
