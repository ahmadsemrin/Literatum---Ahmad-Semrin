package webappdesign.util;

import org.apache.commons.fileupload.FileItem;
import org.xml.sax.*;
import webappdesign.action.*;
import webappdesign.enums.Directory;
import webappdesign.model.UploadedFile;
import webappdesign.model.data_access_object.file.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.zip.*;

public final class FileUtil {
    private static List<File> fileList = new ArrayList<>();

    private static List<File> getAllFiles(File folder) {
        if(folder.isDirectory()) {
            File[] files = folder.listFiles();
            for(File currFile : files) {
                getAllFiles(currFile);
            }
        } else {
            fileList.add(folder);
        }

        return fileList;
    }

    public static List<File> getFilesFromFolder(File folder) {
        fileList = new ArrayList<>();

        return getAllFiles(folder);
    }

    public static File findJATS(List<File> files) {
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

    public static File toXSLT(Source xsltFile, File xmlFile, Source destFile) {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = null;
        File newFile = null;
        try {
            transformer = factory.newTransformer(xsltFile);

            newFile = new File(Directory.ARTICLES_PATH.getDirectory() + File.separator +
                    xmlFile.getName().substring(0, xmlFile.getName().lastIndexOf('.')) + ".html");
            transformer.transform(destFile, new StreamResult(newFile));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return newFile;
    }

    public static String getArticleTitle(File xmlFile) {
        FileReader fileReader;
        String title = "";
        try {
            fileReader = new FileReader(xmlFile);
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

        return title;
    }

    public static String createNewFileWithUniqueName(String oldName) {
        String newName = oldName;

        //create output directory is not exists
        File folder = new File(oldName);
        if (!folder.exists()) {
            folder.mkdir();
        } else {
            for (int i = 1; ; i++) {
                newName = oldName.concat("(" + i + ")");
                folder = new File(newName);
                if (!folder.exists()) {
                    folder.mkdir();

                    break;
                }
            }
        }

        return newName;
    }

    public static void unzipFile(String zippedFile, String outputFolder) {
        //get the zip file content
        ZipInputStream zis = null;
        //get the zipped file list entry
        ZipEntry ze = null;
        try {
            zis = new ZipInputStream(new FileInputStream(zippedFile));
            ze = zis.getNextEntry();

            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                System.out.println("file unzip : " + newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                byte[] buffer = new byte[1024];
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();

                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void modifyJATS(File jatsFile) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(jatsFile.getAbsoluteFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("JATS-archivearticle1.dtd")) {
                    line = line.replace("JATS-archivearticle1.dtd",
                            Directory.JATS_DTD_PATH.getDirectory());
                }

                if (line.contains("<self-uri content-type=\"pdf\" xlink:href=\"")) {
                    String path = jatsFile.getAbsolutePath().substring(0, jatsFile.getAbsolutePath().lastIndexOf("/"));
                    line = line.replace("<self-uri content-type=\"pdf\" xlink:href=\"",
                            "<self-uri content-type=\"pdf\" xlink:href=\"" + path);
                }

                lines.add(line);
            }

            bufferedReader.close();
            fileReader.close();

            FileWriter fileWriter = new FileWriter(jatsFile.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String ln : lines) {
                bufferedWriter.write(ln);
            }

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDTD(List<File> files) {
        File jatsFile = FileUtil.findJATS(files);
        FileUtil.modifyJATS(jatsFile);

        ActionContext actionContext = new ActionContext(new ActionValidateFileWithDTDUsingDOM());

        return (boolean) actionContext.executeAction(jatsFile.getAbsolutePath());
    }

    public static void uploadFiles(List<FileItem> files) {
        for (FileItem item : files) {
            String filePath = Directory.UPLOADED_FILES_PATH.getDirectory() + File.separator + item.getName();
            try {
                item.write(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }

            UploadedFile uploadedFile = extractFile(item, filePath);

            IFileDAO fileDAO = FileDAO.getInstance();
            fileDAO.insertFile(uploadedFile);
        }
    }

    private static UploadedFile extractFile(FileItem file, String filePath) {
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setName("SAGE");
        uploadedFile.setEmail("sage@atypon.com");
        uploadedFile.setFile(file.getName());
        uploadedFile.setDate(new Date().toString());

        ActionContext actionContext = new ActionContext(new ActionUnzipFile());
        Object[] results = (Object[]) actionContext.executeAction(filePath);
        if ((boolean) results[0]) {
            uploadedFile.setStatus("Processed");
        } else {
            uploadedFile.setStatus("Denied");
        }

        uploadedFile.setExtractedFile((String) results[1]);

        return uploadedFile;
    }

    public static boolean isValidJATS(String xmlFile) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();

            builder.setErrorHandler(new ErrorHandler() {
                public void warning(SAXParseException exception) throws SAXException {
                    System.out.println("WARNING: " + exception.getMessage());
                }

                public void error(SAXParseException exception) throws SAXException {
                    System.out.println("ERROR: " + exception.getMessage());

                    throw exception;
                }

                public void fatalError(SAXParseException exception) throws SAXException {
                    System.out.println("FATAL: " + exception.getMessage());

                    throw exception;
                }
            });

            builder.parse(new InputSource(xmlFile));

            return true;
        } catch (ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            return false;
        }

        return false;
    }
}
