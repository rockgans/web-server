package com.gans.server.model;

/**
 * represents the data of a single file inside the root.
 */
public class DirectoryItem {

    //name of the file
    private String name;
    //path to the file
    private String path;
    //extension of the file
    private String ext;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
