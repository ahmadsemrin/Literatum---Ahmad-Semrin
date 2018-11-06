package webappdesign.controller;

import webappdesign.action.LoginAction;
import webappdesign.form.UserForm;
import webappdesign.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = {"/login"})
public class LoginControllerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
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
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchUrl);
            requestDispatcher.forward(request, response);
        }
    }
}
