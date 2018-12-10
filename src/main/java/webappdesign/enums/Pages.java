package webappdesign.enums;

public enum Pages {
    LOGIN_PAGE(Directories.JSP_PATH.getDirectory() + "/login_page/login.jsp"),
    SIGN_UP_PAGE(Directories.JSP_PATH.getDirectory() + "/sign_up_page/sign_up.jsp"),
    SUPER_ADMIN_PAGE(Directories.JSP_PATH.getDirectory() + "/wat_page/wat.jsp"),
    ADMIN_PAGE(Directories.JSP_PATH.getDirectory() + "/admin_page/admin.jsp"),
    BASIC_USER_PAGE(Directories.JSP_PATH.getDirectory() + "/basic_user_page/basic_user.jsp"),
    UPLOAD_FILE_PAGE(Directories.JSP_PATH.getDirectory() + "/upload_file_page/upload.jsp"),
    UPLOADED_FILES_PAGE(Directories.JSP_PATH.getDirectory() + "/wat_page/table.jsp");

    String page;

    Pages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
