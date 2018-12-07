package webappdesign.enums;

public enum Pages {
    JSP_DIRECTORY("/jsp"),
    LOGIN_PAGE(JSP_DIRECTORY.getPage() + "/login_page/login.jsp"),
    SIGN_UP_PAGE(JSP_DIRECTORY.getPage() + "/sign_up_page/sign_up.jsp"),
    WAT_PAGE(JSP_DIRECTORY.getPage() + "/wat_page/wat.jsp"),
    UPLOAD_FILE_PAGE(JSP_DIRECTORY.getPage() + "/upload_file_page/upload.jsp"),
    UPLOADED_FILES_PAGE(JSP_DIRECTORY.getPage() + "/wat_page/table.jsp");


    String page;

    Pages(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }
}
