package webappdesign.enums;

public enum PageURI {
    LOGIN_URI("login"),
    LOGGED_IN("loggedIn"),
    SUPER_ADMIN_URI("super"),
    ADMIN_URI("admin"),
    BASIC_USER_URI("basic"),
    SIGN_UP_URI("sign-up"),
    SIGNED_URI("signed"),
    UPLOAD_URI("upload"),
    UPLOADED_URI("uploaded"),
    UPLOADED_FILES_URI("table"),
    TRANSFORM_TO_XSLT("transform"),
    ARTICLES_URI("article"),
    ADD_ADMIN_URI("addAdmin");

    String pageURI;

    PageURI(String pageURI) {
        this.pageURI = pageURI;
    }

    public String getPageURI() {
        return pageURI;
    }
}
