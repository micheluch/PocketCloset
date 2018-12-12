package com.example.michael.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RowItem {
    private Bitmap imageId;
    private String title;
    private String path;

    public RowItem(String title, String desc) {
        this.title = title;
        this.path = desc;
    }
    public Bitmap getImageId() {

        try {
            File f = new File(this.path, title + ".png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            this.imageId = b;
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    public void setImageId(Bitmap imageId) {
        this.imageId = getImageId();
    }
    public String getPath() {
        return path;
    }
    public void setPath(String desc) {
        this.path = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
