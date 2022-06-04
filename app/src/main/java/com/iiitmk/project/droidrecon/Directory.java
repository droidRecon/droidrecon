package com.iiitmk.project.droidrecon;

import java.util.List;

public class Directory {
    String path;
    String status;
    List<String> urls;

    public Directory() {
    }

    public Directory(String path, String status, List<String> urls) {
        this.path = path;
        this.status = status;
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
