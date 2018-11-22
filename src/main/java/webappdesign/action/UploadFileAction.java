package webappdesign.action;

import org.apache.commons.fileupload.FileItem;
import webappdesign.model.UploadedFile;
import webappdesign.model.data_access_object.file.FileDAO;
import webappdesign.model.data_access_object.file.IFileDAO;

import java.io.File;
import java.util.Date;
import java.util.List;

public class UploadFileAction {
    private IFileDAO fileDAO;

    public void uploadFiles(List<FileItem> files) throws Exception {
        fileDAO = FileDAO.getInstance();

        for (FileItem item : files) {
            String filePath = "/home/asemrin/Documents/IdeaProjects/Maven Projects/Literatum - " +
                    "AhmadSemrin/Uploaded Files/" + item.getName();
            item.write(new File(filePath));

            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setName("SAGE");
            uploadedFile.setEmail("sage@atypon.com");
            uploadedFile.setFile(item.getName());
            uploadedFile.setDate(new Date().toString());

            if (UnzipFileAction.unzipFile(filePath)) {
                uploadedFile.setStatus("Processed");
            } else {
                uploadedFile.setStatus("Denied");
            }

            fileDAO.insertFile(uploadedFile);
        }

        System.out.println("File uploaded");
    }
}
