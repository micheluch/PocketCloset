package com.example.michael.myapplication;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import com.example.michael.myapplication.Entry;


public class Outfit extends Entry{

    private List<Clothing> clothingList;
    private String description;
    private final int DEFAULT_INVALID_CONDITION = -1;

    public Outfit( String outfitName, List<Clothing> clothingList, String description, int thumbnail) {
        super(outfitName,thumbnail, -999999);
        this.clothingList = clothingList;
        this.description = description;
        this.thumbnail = thumbnail;
        super.setEntryId(DEFAULT_INVALID_CONDITION);
    }

    public Outfit(String outfitName) {
        this(outfitName, new ArrayList<Clothing>(), null, -1);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addClothingToOutfit(Clothing clothingToAdd){
        this.clothingList.add(clothingToAdd);
    }

    public List<Clothing> getClothingList() {
        return clothingList;
    }

}
