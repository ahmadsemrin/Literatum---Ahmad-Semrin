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

                /*String extension;
                int i = newFile.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = newFile.getName().substring(i+1);

                    if (extension.equals("xml")) {
                        System.out.println(newFile.getName());

                        FileReader fileReader = new FileReader(newFile.getAbsoluteFile());
                        BufferedReader bufferedReader = new BufferedReader(fileReader);

                        List<String> lines = new ArrayList<>();
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            if (line.contains("JATS-archivearticle1.dtd")) {
                                line = line.replace("JATS-archivearticle1.dtd", "jats.dtd");
                            }

                            lines.add(line);
                        }

                        FileWriter fileWriter = new FileWriter(newFile.getAbsoluteFile());
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(lines.toString());

                        try {
                            if (CheckFileValidityAction.validateWithDTDUsingDOM(newFile.getAbsolutePath())) {
                                result = true;
                            }
                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                        }
                    }
                }*/
            }

            zis.closeEntry();
            zis.close();

            File newFile = new File(OUTPUT_FOLDER);
            for (File file : newFile.listFiles()) {
                if (file.isFile()) {
                    String extension;
                    int i = newFile.getName().lastIndexOf('.');
                    if (i > 0) {
                        extension = newFile.getName().substring(i + 1);

                        if (extension.equals("xml")) {
                            System.out.println(newFile.getName());

                            FileReader fileReader = new FileReader(newFile.getAbsoluteFile());
                            BufferedReader bufferedReader = new BufferedReader(fileReader);

                            List<String> lines = new ArrayList<>();
                            String line = null;
                            while ((line = bufferedReader.readLine()) != null) {
                                if (line.contains("JATS-archivearticle1.dtd")) {
                                    line = line.replace("JATS-archivearticle1.dtd", "jats.dtd");
                                }

                                lines.add(line);
                            }

                            FileWriter fileWriter = new FileWriter(newFile.getAbsoluteFile());
                            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                            bufferedWriter.write(lines.toString());

                            try {
                                if (CheckFileValidityAction.validateWithDTDUsingDOM(newFile.getAbsolutePath())) {
                                    result = true;
                                }
                            } catch (ParserConfigurationException e) {
                                e.printStackTrace();
                            } finally {
                                bufferedReader.close();
                                bufferedWriter.close();
                            }
                        }
                    }
                }
            }

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
