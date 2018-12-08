package com.example.michael.myapplication;

import java.security.InvalidParameterException;
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

    public boolean addEntryToCloset(Entry entry) throws IllegalArgumentException {
        if (entry.type == pocketClassType.CLOSET_TYPE)
            throw new IllegalArgumentException("Attempting to add Entry of type pocketClassType.CLOSET_TYPE to a Closet's contentList.");
        else
            return contentList.add(entry);
    }

}
