package com.example.michael.myapplication;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

public abstract class Entry {
    //Data members shared by Clothing and Outfit
    protected int id;
    protected String name;
    protected Bitmap image;
    protected String path;
    protected enum pocketClassType{INVALID_TYPE, CLOTHING_TYPE, OUTFIT_TYPE, CLOSET_TYPE}
    protected pocketClassType type;


    public Entry(String entryName, String path, int id, pocketClassType type) {
        this.name = entryName;
        this.id = id;
        this.path = path;
        this.type = type;
    }

    //Methods that are shared by Clothing and Outfit
    public int getEntryId() {
        return id;
    }

    public void setEntryId(int id) {
        this.id = id;
    }

    public String getEntryName() {
        return name;
    }

    public void setEntryName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage() {
        this.image = retrieveImageFromFolder();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public pocketClassType getEntryType() {
        return type;
    }

    public void setEntryType(pocketClassType type) {
        this.type = type;
    }

    public Bitmap retrieveImageFromFolder() {

        try {
            File f = new File(this.path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            b = ExifUtil.rotateBitmap(f.getAbsolutePath(), b);
            this.image = b;
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


}
