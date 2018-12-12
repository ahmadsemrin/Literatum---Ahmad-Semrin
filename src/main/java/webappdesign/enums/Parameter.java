package webappdesign.enums;

public enum Parameter {
    EMAIL_PARAMETER("email"),
    PASSWORD_PARAMETER("password"),
    PASSWORD2_PARAMETER("password2"),
    TRANSFORM_FILE_PARAMETER("transformFile"),
    ARTICLE_NAME_PARAMETER("articleName");

    String parameter;

    Parameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
