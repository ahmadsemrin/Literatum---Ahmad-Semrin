package webappdesign.action;

import webappdesign.enums.Directories;

import java.io.File;
import java.util.*;

public class GetArticlesAction implements IAction {
    @Override
    public Object doAction(Object object) {
        File articlesFile = new File(Directories.ARTICLES_PATH.getDirectory());
        File[] articles = articlesFile.listFiles();

        return Arrays.asList(articles);
    }
}
