package webappdesign.model.data_access_object.file;

import webappdesign.model.UploadedFile;

import java.util.List;

public interface IFileDAO {
    List<UploadedFile> findAll();
    void insertFile(UploadedFile file);
    void deleteFile(UploadedFile file);
}
