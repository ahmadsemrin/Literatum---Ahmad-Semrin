package webappdesign.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import webappdesign.action.LoginAction;
import webappdesign.action.UploadFileAction;
import webappdesign.form.UserForm;
import webappdesign.model.User;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.util.List;

@WebFilter(filterName = "DispatcherFilter", urlPatterns = {"/*"})
@MultipartConfig(fileSizeThreshold = 6291456, // 6 MB
        maxFileSize = 10485760L, // 10 MB
        maxRequestSize = 20971520L // 20 MB
)
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
        } else if ("uploaded".equals(action)) {
            dispatchUrl = "/jsp/upload_file_page/uploaded.jsp";
        }

        if (dispatchUrl != null) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher(dispatchUrl);
            requestDispatcher.forward(req, resp);

            if ("uploaded".equals(action)) {
                ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
                try {
                    List<FileItem> multiFiles = servletFileUpload.parseRequest(request);

                    UploadFileAction uploadFileAction = new UploadFileAction();
                    uploadFileAction.uploadFiles(multiFiles);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}
