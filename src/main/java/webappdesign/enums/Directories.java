package webappdesign.enums;

public enum Directories {
    HOME_PATH("/home/asemrin"),
    PROJECT_PATH(HOME_PATH.getDirectory() + "/Documents/IdeaProjects/Maven Projects/Literatum - AhmadSemrin"),
    UPLOADED_FILES_PATH(PROJECT_PATH.getDirectory() + "/Uploaded Files"),
    JATS_DTD_PATH(PROJECT_PATH.getDirectory() + "/jats.dtd"),
    JATS_XSLT_PATH(PROJECT_PATH.getDirectory() + "/src/main/webapp/xslt/jats2.xsl"),
    ARTICLES_PATH(PROJECT_PATH.getDirectory() + "/src/main/webapp/articles"),
    JSP_PATH("/jsp");

    String directory;

    Directories(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
