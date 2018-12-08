package com.example.michael.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Closet extends Entry {

    protected List<Entry> contentList;
    private String description;

    public Closet(String closetName, String description, pocketClassType type, String path) {
        super(closetName, path, 0, type);
        this.description = description;
        contentList = new ArrayList<>();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
