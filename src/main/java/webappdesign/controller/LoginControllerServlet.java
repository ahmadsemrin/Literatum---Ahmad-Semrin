package webappdesign.controller;

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
            // no action class, just forward
            dispatchUrl = "/jsp/login_page/LoginPage.jsp";
        }

        if (dispatchUrl != null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(dispatchUrl);
            requestDispatcher.forward(request, response);
        }
    }
}
