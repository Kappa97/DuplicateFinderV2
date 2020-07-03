package licenta.DuplicateFinder;

import javafx.beans.property.SimpleStringProperty;

public class MyFileObject {
    private String fileName;
    private String filePath;
    private double fileSize;
    private int fileGroup;
    private String fileLastModifiedDate;



    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public double getFileSize() {
        return fileSize;
    }
    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }
    public int getFileGroup() {
        return fileGroup;
    }
    public void setFileGroup(int fileGroup) {
        this.fileGroup = fileGroup;
    }
    public String getFileLastModifiedDate() {
        return fileLastModifiedDate;
    }
    public void setFileLastModifiedDate(String fileLastModifiedDate) {
        this.fileLastModifiedDate = fileLastModifiedDate;
    }

}
