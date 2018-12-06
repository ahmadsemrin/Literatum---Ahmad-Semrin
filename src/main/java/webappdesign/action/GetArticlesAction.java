package webappdesign.action;

import java.io.File;
import java.util.*;

public class GetArticlesAction {
    public List<File> getAllArticles() {
        File articlesFile = new File("/home/asemrin/Documents/IdeaProjects/Maven Projects/Literatum - " +
                "AhmadSemrin/src/main/webapp/articles");
        File[] articles = articlesFile.listFiles();

        return Arrays.asList(articles);
    }
}
