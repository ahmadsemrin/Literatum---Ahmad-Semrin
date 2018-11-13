package webappdesign.action;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.List;

public class UploadFileAction {
    public void uploadFiles(List<FileItem> files) throws Exception {
        for (FileItem item : files) {
            item.write(new File("/home/ahmadsemrin/Documents/IdeaProjects/Maven Projects/Literatum - " +
                    "Ahmad Semrin/Uploaded Files/" + item.getName()));
        }

        System.out.println("File uploaded");
    }
}
