package com.example.michael.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Clothing {

    private int clothingId;
    private String clothingName;
    private int thumbnail;
    private String clothingCondition;
    private List<Outfit> associatedOutfits;
    private List<Closet> associatedClosets;

    public Clothing(String clothingName, int thumbnail) {
        this.clothingName = clothingName;
        this.thumbnail = thumbnail;
        associatedClosets = new ArrayList<>();
        associatedOutfits = new ArrayList<>();
    }

    public static Clothing createFromString(String properties) {
        /* Properties is ordered, separated with commas, as follows:
        *   Name
        *   Id
        *   Thumbnail (identifier)
        *   Condition
        */
        String[] vals = properties.split(",");
        return new Clothing(vals[0], Integer.parseInt(vals[1]));
    }

    public int getClothingId() {
        return clothingId;
    }

    public void setClothingId(int clothingId) {
        this.clothingId = clothingId;
    }

    public String getClothingName() {
        return clothingName;
    }

    public void setClothingName(String clothingName) {
        this.clothingName = clothingName;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getClothingCondition() {
        return clothingCondition;
    }


    public List<Outfit> getAssociatedOutfits() {
        return associatedOutfits;
    }

    public void setAssociatedOutfits(List<Outfit> associatedOutfits) {
        this.associatedOutfits = associatedOutfits;
    }

    public List<Closet> getAssociatedClosets() {
        return associatedClosets;
    }

    //TODO: Add to associated closets and outfits
}
