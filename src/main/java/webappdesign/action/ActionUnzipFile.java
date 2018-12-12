package webappdesign.action;

import webappdesign.enums.Directory;
import webappdesign.util.FileUtil;

import java.io.*;
import java.util.List;

public class ActionUnzipFile implements IAction {
    private static final String OUTPUT_FOLDER = Directory.UPLOAD_FILE_PATH.getDirectory();

    @Override
    public Object doAction(Object object) {
        String zipFile = (String) object;

        String newOutputFolder = FileUtil.createNewFileWithUniqueName(OUTPUT_FOLDER);

        FileUtil.unzipFile(zipFile, newOutputFolder);

        File newFile = new File(newOutputFolder);
        List<File> files = FileUtil.getFilesFromFolder(newFile);

        boolean result = FileUtil.checkDTD(files);

        int i = newOutputFolder.lastIndexOf('/');

        Object[] ob = new Object[2];
        ob[0] = result;
        ob[1] = newOutputFolder.substring(i + 1);

        return ob;
    }
}
