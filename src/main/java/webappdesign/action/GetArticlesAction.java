package webappdesign.action;

import webappdesign.enums.Directories;

import java.io.File;
import java.util.*;

public class GetArticlesAction {
    public List<File> getAllArticles() {
        File articlesFile = new File(Directories.ARTICLES_PATH.getDirectory());
        File[] articles = articlesFile.listFiles();

        return Arrays.asList(articles);
    }
}
