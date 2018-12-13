package webappdesign.action;

import webappdesign.enums.Directory;
import webappdesign.model.Article;
import webappdesign.util.FileUtil;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.*;

public class ActionTransformFileToXSLT implements IAction {
    @Override
    public Object doAction(Object object) {
        String fileName = (String) object;

        Source xslt = new StreamSource(new File(Directory.JATS_XSLT_PATH.getDirectory()));

        List<File> files = FileUtil.getFilesFromFolder(new File(
                Directory.UPLOADED_FILES_PATH.getDirectory() + File.separator + fileName));

        File xml = FileUtil.findJATS(files);
        Source text = new StreamSource(Objects.requireNonNull(xml));

        File newFile = FileUtil.toXSLT(xslt, xml, text);

        System.out.println("Transformed!");

        String title = FileUtil.getArticleTitle(xml);

        Article article = new Article();
        article.setArticleName(newFile.getName());
        article.setTitle(title);
        article.setPublishDate(new Date());
        article.setPrice(0);

        return article;
    }
}
