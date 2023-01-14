package com.example.smartclassroutinefinalinterface;

public class FileClass {
    String description,url;

    public FileClass() {
    }

    public FileClass(String description, String url) {
        this.description = description;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
