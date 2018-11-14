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
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        /*
         * uri is in this form: /contextName/resourceName,
         * for example: /appdesign1/input-product.
         * However, in the event of a default context, the
         * context name is empty, and uri has this form
         * /resourceName, e.g.: /input-product
         */
        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);
        // execute an action
        String dispatchUrl = null;
        if ("login".equals(action)) {
            UserForm userForm = new UserForm();
            userForm.setEmail(req.getParameter("email"));
            System.out.println(userForm.getEmail());
            userForm.setPassword(req.getParameter("password"));

            User user = new User();
            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPassword());

            System.out.println(user.getEmail() + " " + user.getPassword());
            HttpSession userSession = request.getSession();
            userSession.setAttribute("user", user);

            // no action class, just forward
            dispatchUrl = "/jsp/login_page/login.jsp";
        } else if ("loggedIn".equals(action)) {
            HttpSession userSession = request.getSession();
            User user = (User) userSession.getAttribute("user");

            LoginAction loginAction = new LoginAction();
            currentUser = loginAction.login(user);

            if (currentUser != null) {
                System.out.println(currentUser.getRole());
            } else {
                System.out.println("No such user.");
            }
            dispatchUrl = "/jsp/wat_page/wat.jsp";
        }

        if (dispatchUrl != null) {
            RequestDispatcher rd =
                    req.getRequestDispatcher(dispatchUrl);
            rd.forward(req, resp);
        } else {
            // let static contents pass
            chain.doFilter(req, resp);
        }


        //forwardToPage(req, resp, chain);

            //if ("login".equals(action)) {
                /*UserForm userForm = new UserForm();
                userForm.setEmail(request.getParameter("email"));
                userForm.setPassword(request.getParameter("password"));

                User newUser = new User();
                newUser.setEmail(userForm.getEmail());
                newUser.setPassword(userForm.getPassword());

                LoginAction loginAction = new LoginAction();
                currentUser = loginAction.login(newUser);

                if (currentUser != null) {
                    System.out.println(currentUser.getRole());
                } else {
                    System.out.println("No such user.");
                }*/
    }

    public void init(FilterConfig config) throws ServletException {

    }

    /*private void forwardToPage(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String pageURI = uri.substring(lastIndex + 1);

        String dispatchUrl = null;
        if ("login".equals(pageURI)) {
            UserForm userForm = new UserForm();
            userForm.setEmail(request.getParameter("email"));
            System.out.println(userForm.getEmail());
            userForm.setPassword(request.getParameter("password"));

            newUser = new User();
            newUser.setEmail(userForm.getEmail());
            newUser.setPassword(userForm.getPassword());

            LoginAction loginAction = new LoginAction();
            currentUser = loginAction.login(newUser);

            dispatchUrl = "/jsp/login_page/login.jsp";
        } else if ("loggedIn".equals(pageURI)) {
            if (currentUser != null) {
                System.out.println(currentUser.getRole());
            } else {
                System.out.println("No such user.");
            }

            dispatchUrl = "/jsp/wat_page/wat.jsp";
        } else if ("admin".equals(pageURI)) {
            dispatchUrl = "/jsp/wat_page/wat.jsp";
        } else if ("sign-up".equals(pageURI)) {
            dispatchUrl = "/jsp/sign_up_page/sign_up.jsp";
        } else if ("upload".equals(pageURI)) {
            dispatchUrl = "/jsp/upload_file_page/upload.jsp";
        } else if ("uploaded".equals(pageURI)) {
            dispatchUrl = "/jsp/upload_file_page/uploaded.jsp";

            ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
            try {
                List<FileItem> multiFiles = servletFileUpload.parseRequest(request);

                UploadFileAction uploadFileAction = new UploadFileAction();
                uploadFileAction.uploadFiles(multiFiles);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (dispatchUrl != null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(dispatchUrl);
            requestDispatcher.forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }*/

}
