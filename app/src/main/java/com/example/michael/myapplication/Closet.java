package com.example.michael.myapplication;

public class Closet {

    private String closetName;
    private String description;
    private int thumbnail;

    public Closet(String closetName, String description, int thumbnail) {
        this.closetName = closetName;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public String getClosetName() {
        return closetName;
    }

    public String getDescription() {
        return description;
    }


    public int getThumbnail() {
        return thumbnail;
    }

    public void setClosetName(String closetName) {
        this.closetName = closetName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
