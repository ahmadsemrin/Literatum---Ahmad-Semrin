package webappdesign.controller;

import webappdesign.action.LoginAction;
import webappdesign.form.UserForm;
import webappdesign.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "DispatcherFilter", urlPatterns = {"/*"})
public class DispatcherFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException,
            IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);

        String dispatchUrl = null;
        if ("login".equals(action)) {
            UserForm userForm = new UserForm();
            userForm.setEmail(request.getParameter("email"));
            userForm.setPassword(request.getParameter("password"));

            User newUser = new User();
            newUser.setEmail(userForm.getEmail());
            newUser.setPassword(userForm.getPassword());

            LoginAction loginAction = new LoginAction();
            loginAction.login(newUser);

            // request.setAttribute("User", newUser);
            dispatchUrl = "/jsp/login_page/login.jsp";
        } else if ("admin".equals(action)) {
            dispatchUrl = "/jsp/wat_page/wat.jsp";
        } else if ("sign-up".equals(action)) {
            dispatchUrl = "/jsp/sign_up_page/sign_up.jsp";
        } else if ("upload".equals(action)) {
            dispatchUrl = "/jsp/upload_file_page/upload.jsp";
        }

        if (dispatchUrl != null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(dispatchUrl);
            requestDispatcher.forward(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
