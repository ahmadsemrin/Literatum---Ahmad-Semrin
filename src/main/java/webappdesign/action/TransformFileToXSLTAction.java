package webappdesign.action;

import webappdesign.enums.Directories;
import webappdesign.model.Article;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class TransformFileToXSLTAction {
    private static List<File> files = new ArrayList<>();

    public Article transform(String fileName) throws TransformerException {
        Source xslt = new StreamSource(new File(Directories.JATS_XSLT_PATH.getDirectory()));

        readFilesFromFolder(new File(Directories.UPLOADED_FILES_PATH.getDirectory() + File.separator +
                fileName));
        File xml = findFile(files);
        Source text = new StreamSource(xml);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(xslt);

        File newFile = new File(Directories.ARTICLES_PATH.getDirectory() + File.separator +
                xml.getName().substring(0, xml.getName().lastIndexOf('.')) + ".html");
        transformer.transform(text, new StreamResult(newFile));

        System.out.println("Transformed!");

        Article article = new Article();
        article.setArticleName(newFile.getName());
        article.setArticleSection("");
        article.setPublishDate(new Date());
        article.setPrice(0);

        return article;
    }

    private static void readFilesFromFolder(File folder) {
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File currFile : files) {
                readFilesFromFolder(currFile);
            }
        } else {
            files.add(folder);
        }
    }

    private static File findFile(List<File> files) {
        for (File fileEntry : files) {
            String extension;
            int i = fileEntry.getName().lastIndexOf('.');
            if (i > 0) {
                extension = fileEntry.getName().substring(i + 1);

                if (extension.equals("xml")) {
                    String regexStr = "^(\\d)+(\\.xml)$";
                    Pattern pattern = Pattern.compile(regexStr);
                    Matcher matcher = pattern.matcher(fileEntry.getName());
                    if (matcher.matches()) {
                        return fileEntry;
                    }
                }
            }
        }

        return null;
    }
}