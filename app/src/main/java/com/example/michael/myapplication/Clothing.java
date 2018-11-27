package com.example.michael.myapplication;
import com.example.michael.myapplication.Closet;
import com.example.michael.myapplication.Entry;
import com.example.michael.myapplication.Outfit;

import java.util.ArrayList;
import java.util.List;

public class Clothing extends Entry {

    //Clothing-only data members
    private int id;
    private String clothingCondition;
    public enum clothingType {shirt, pants, shoes, other}
    private clothingType type;
    private List<Outfit> associatedOutfits;
    private List<Closet> associatedClosets;

    public Clothing(String entryName, int thumbnail, String clothingCondition, clothingType type) {
        super(entryName, thumbnail);
        this.clothingCondition = clothingCondition;
        this.type = type;
    }

    public Clothing(String clothingName, int thumbnail)
    {
        this(clothingName, thumbnail, DEFAULT_CONDITION, clothingType.other);
    }

    public Clothing(String clothingName, clothingType clothingType) {
        this(clothingName, 0, DEFAULT_CONDITION, clothingType);
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

    //"Extended" methods from Entry
    public int getClothingId() {
        return getEntryId();
    }
    public void setClothingId(int clothingId) {
        setEntryId(clothingId);
    }
    public String getClothingName() {
        return getEntryName();
    }
    public void setClothingName(String clothingName) { setEntryName(clothingName); }

    //Clothing-only methods


    public void setClothingCondition(String clothingCondition) {
        this.clothingCondition = clothingCondition;
    }

    public int getType() {
        return type.ordinal();
    }

    public void setType(clothingType type) {
        this.type = type;
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
    //TODO: Add to associated closets and outfitsPock
    public void addClothing(Clothing newClothing)
    {

    }
}