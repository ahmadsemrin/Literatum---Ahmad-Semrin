package webappdesign.enums;

import java.io.File;
import java.util.Date;

public enum Directories {
    HOME_PATH("/home/asemrin"),
    PROJECT_PATH(HOME_PATH.getDirectory() + "/Documents/IdeaProjects/Maven Projects/Literatum - AhmadSemrin"),
    UPLOADED_FILES_PATH(PROJECT_PATH.getDirectory() + "/Uploaded Files"),
    JATS_DTD_PATH(PROJECT_PATH.getDirectory() + "/jats.dtd"),
    JATS_XSLT_PATH(PROJECT_PATH.getDirectory() + "/src/main/webapp/xslt/jats2.xsl"),
    ARTICLES_PATH(PROJECT_PATH.getDirectory() + "/src/main/webapp/articles"),
    JSP_PATH("/jsp"),
    UPLOAD_FILE_PATH(UPLOADED_FILES_PATH.getDirectory() + File.separator + (new Date().getYear() + 1990) +
            (new Date().getMonth() + 1) + (new Date().getDate()));

    String directory;

    Directories(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
