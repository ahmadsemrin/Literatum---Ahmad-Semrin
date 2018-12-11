package webappdesign.action;

import webappdesign.enums.Directories;
import webappdesign.model.Article;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionTransformFileToXSLT implements IAction {
    private static List<File> files = new ArrayList<>();

    @Override
    public Object doAction(Object object) {
        String fileName = (String) object;

        Source xslt = new StreamSource(new File(Directories.JATS_XSLT_PATH.getDirectory()));

        readFilesFromFolder(new File(Directories.UPLOADED_FILES_PATH.getDirectory() + File.separator +
                fileName));
        File xml = findFile(files);
        Source text = new StreamSource(xml);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        File newFile = null;
        try {
            transformer = factory.newTransformer(xslt);

            newFile = new File(Directories.ARTICLES_PATH.getDirectory() + File.separator +
                    xml.getName().substring(0, xml.getName().lastIndexOf('.')) + ".html");
            transformer.transform(text, new StreamResult(newFile));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        System.out.println("Transformed!");

        FileReader fileReader = null;
        String title = "";
        try {
            fileReader = new FileReader(xml);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("<article-title>")) {
                    int i = line.indexOf("<article-title>");
                    int j = line.indexOf("</article-title>");
                    title = line.substring(i,j);
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Article article = new Article();
        article.setArticleName(newFile.getName());
        article.setTitle(title);
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
