package webappdesign.enums;

public enum Attribute {
    ARTICLES_ATTRIBUTE("articles"),
    UPLOADED_FILES_ATTRIBUTE("uploadedFiles");

    String attribute;

    Attribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
