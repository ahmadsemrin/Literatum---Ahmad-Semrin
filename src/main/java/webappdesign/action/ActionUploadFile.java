package webappdesign.action;

import org.apache.commons.fileupload.FileItem;
import webappdesign.enums.Directories;
import webappdesign.model.UploadedFile;
import webappdesign.model.data_access_object.file.FileDAO;
import webappdesign.model.data_access_object.file.IFileDAO;

import java.io.File;
import java.util.Date;
import java.util.List;

public class ActionUploadFile implements IAction {
    @Override
    public Object doAction(Object object) {
        List<FileItem> files = (List<FileItem>) object;

        for (FileItem item : files) {
            String filePath = Directories.UPLOADED_FILES_PATH.getDirectory() + File.separator + item.getName();
            try {
                item.write(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }

            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setName("SAGE");
            uploadedFile.setEmail("sage@atypon.com");
            uploadedFile.setFile(item.getName());
            uploadedFile.setDate(new Date().toString());

            ActionContext actionContext = new ActionContext(new ActionUnzipFile());
            Object[] results = (Object[]) actionContext.executeAction(filePath);
            if ((boolean) results[0]) {
                uploadedFile.setStatus("Processed");
            } else {
                uploadedFile.setStatus("Denied");
            }

            uploadedFile.setExtractedFile((String) results[1]);

            IFileDAO fileDAO = FileDAO.getInstance();
            fileDAO.insertFile(uploadedFile);
        }

        System.out.println("File uploaded");

        return null;
    }
}
