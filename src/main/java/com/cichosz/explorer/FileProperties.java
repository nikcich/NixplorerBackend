package com.cichosz.explorer;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class FileProperties {
    private String filePath;
    private long fileSize;
    private String fileName;
    private String fileExtension;
    private long createdDate;
    private long modifiedDate;
    private long accessedDate;

    // Constructor
    public FileProperties() {
    }

    // Getters and Setters
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public long getAccessedDate() {
        return accessedDate;
    }

    public void setAccessedDate(long accessedDate) {
        this.accessedDate = accessedDate;
    }
}
