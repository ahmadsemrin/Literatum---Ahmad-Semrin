package webappdesign.action;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class UnzipFileAction {
    private static final String OUTPUT_FOLDER = "/home/asemrin/Documents/IdeaProjects/Maven Projects/Literatum - " +
            "AhmadSemrin/Uploaded Files/" + new Date().getTime();
    private static List<File> files = new ArrayList<>();

    public static boolean unzipFile(String zipFile) {
        boolean result = false;
        byte[] buffer = new byte[1024];

        try {
            //create output directory is not exists
            File folder = new File(OUTPUT_FOLDER);
            if (!folder.exists()) {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(OUTPUT_FOLDER + File.separator + fileName);

                System.out.println("file unzip : " + newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();

                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            File newFile = new File(OUTPUT_FOLDER);
            readFilesFromFolder(newFile);

            result = findAndEdit(files);

            System.out.println("Done");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private static void readFilesFromFolder(File folder) {
        if(folder.isDirectory()) {
            File[] files = folder.listFiles();
            for(File currFile : files) {
                readFilesFromFolder(currFile);
            }
        } else {
            files.add(folder);
        }
    }

    private static boolean findAndEdit(List<File> files) throws IOException {
        for (File fileEntry : files) {
            String extension;
            int i = fileEntry.getName().lastIndexOf('.');
            if (i > 0) {
                extension = fileEntry.getName().substring(i + 1);

                if (extension.equals("xml")) {
                    FileReader fileReader = new FileReader(fileEntry.getAbsoluteFile());
                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    List<String> lines = new ArrayList<>();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.contains("JATS-archivearticle1.dtd")) {
                            line = line.replace("JATS-archivearticle1.dtd",
                                    "/home/asemrin/Documents/IdeaProjects/Maven Projects/Literatum - " +
                                            "AhmadSemrin/jats.dtd");
                        }

                        lines.add(line);
                    }

                    bufferedReader.close();
                    fileReader.close();

                    FileWriter fileWriter = new FileWriter(fileEntry.getAbsoluteFile());
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    for (String ln : lines) {
                        bufferedWriter.write(ln);
                    }

                    bufferedWriter.close();
                    fileWriter.close();

                    try {
                        if (fileEntry.getName().equals("192536211700700101.xml") &&
                                CheckFileValidityAction.validateWithDTDUsingDOM(fileEntry.getAbsolutePath())) {
                            System.out.println("YES");
                            return true;
                        }
                    } catch (ParserConfigurationException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return false;
    }
}
