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
            userForm.setUsername(request.getParameter("username"));
            userForm.setPassword(request.getParameter("password"));

            User newUser = new User();
            newUser.setUsername(userForm.getUsername());
            newUser.setPassword(userForm.getPassword());

            LoginAction loginAction = new LoginAction();
            loginAction.login(newUser);

            // request.setAttribute("User", newUser);
            dispatchUrl = "/jsp/login_page/LoginPage.jsp";
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