package com.gans.server.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds the list of files that are available in a folder.
 */
public class DirectoryListing {

    public DirectoryListing(String p, List<File> files) {
        this.path = p;
        this.files = process(files);
    }

    private List<DirectoryItem> process(List<File> files) {
        List<DirectoryItem> items = new ArrayList<>();
        for (File file : files) {
            DirectoryItem directoryItem = new DirectoryItem();
            directoryItem.setName(file.getName());
            try {
                directoryItem.setPath(URLEncoder.encode(file.getName(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            directoryItem.setExt(getFileExtension(file));
            items.add(directoryItem);
        }
        return items;
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        return name.substring(lastIndexOf);
    }

    private String path;

    private List<DirectoryItem> files;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public List<DirectoryItem> getFiles() {
        return files;
    }

    public void setFiles(List<DirectoryItem> files) {
        this.files = files;
    }
}
