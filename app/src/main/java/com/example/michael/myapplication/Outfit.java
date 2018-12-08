package com.example.michael.myapplication;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.example.michael.myapplication.Entry;


public class Outfit extends Entry {

    private List<Clothing> clothingList;
    private String description;

    public Outfit(String outfitName, List<Clothing> clothingList, String description, int id, String path) {
        super(outfitName, path, id);
        setEntryType(pocketClassType.OUTFIT_TYPE);
        this.clothingList = clothingList;
        this.description = description;
    }

    public Outfit(String outfitName, int id, String path) {
        this(outfitName, new ArrayList<Clothing>(), null, id, path); //need to get rid of thumbnail from everywher
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addClothingToOutfit(Clothing clothingToAdd) {
        this.clothingList.add(clothingToAdd);
    }

    public List<Clothing> getClothingList() {
        return clothingList;
    }

}
