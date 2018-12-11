package webappdesign.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static List<File> fileList = new ArrayList<>();

    public static List<File> getFiles(File folder) {
        if(folder.isDirectory()) {
            File[] files = folder.listFiles();
            for(File currFile : files) {
                getFiles(currFile);
            }
        } else {
            fileList.add(folder);
        }

        return fileList;
    }
}
