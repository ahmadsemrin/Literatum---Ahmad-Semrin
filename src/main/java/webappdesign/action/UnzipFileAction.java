package webappdesign.action;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class UnzipFileAction {
    private static final String OUTPUT_FOLDER = "/home/asemrin/Documents/IdeaProjects/Maven Projects/Literatum - " +
            "AhmadSemrin/Uploaded Files/" + new Date().getTime();

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
            result = readFilesFromFolder(newFile);

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private static boolean readFilesFromFolder(File folder) throws IOException {
        boolean result = false;
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                readFilesFromFolder(fileEntry);
            } else if (fileEntry.isFile()){
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
                                line = line.replace("JATS-archivearticle1.dtd", "jats.dtd");
                            }

                            lines.add(line);
                        }

                        FileWriter fileWriter = new FileWriter(fileEntry.getAbsoluteFile());
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(lines.toString());

                        try {
                            if (CheckFileValidityAction.validateWithDTDUsingDOM(fileEntry.getAbsolutePath())) {
                                result = true;
                            }
                        } catch (ParserConfigurationException | IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                bufferedReader.close();
                                bufferedWriter.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        return result;
    }
}
