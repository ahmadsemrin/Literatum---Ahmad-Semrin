package webappdesign.model;

import java.io.Serializable;
import java.util.List;

public class FileBean implements Serializable {
    private List<UploadedFile> uploadedFiles;

    public List<UploadedFile> getUploadedFiles() {
        return uploadedFiles;
    }

    public void setUploadedFiles(List<UploadedFile> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }
}
