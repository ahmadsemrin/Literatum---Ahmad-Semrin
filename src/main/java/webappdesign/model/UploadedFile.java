package webappdesign.model;

import java.util.Date;

public class UploadedFile {
    private String name;
    private String email;
    private String file;
    private String date;
    private String status;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFile() {
        return file;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
