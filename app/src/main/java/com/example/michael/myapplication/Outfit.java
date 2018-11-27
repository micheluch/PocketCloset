package com.example.michael.myapplication;

import java.util.ArrayList;
import java.util.List;
import com.example.michael.myapplication.Entry;


public class Outfit {

    private int outfitId;
    private String outfitName;
    private Clothing hat;
    private Clothing top; //shirt blouse etc
    private Clothing bottom;
    private Clothing shoes;
    private Clothing outerwear;
    private final int DEFAULT_INVALID_CONDITION = -1;


    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    private List<Clothing> accessories;
    int thumbnail;

    public Outfit(int outfitId, String outfitName, Clothing hat, Clothing top,
                  Clothing bottom, Clothing shoes, Clothing outerwear, int thumbnail) {
        this.outfitId = outfitId;
        this.outfitName = outfitName;
        this.hat = hat;
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
        this.outerwear = outerwear;
        this.accessories = null;
        this.thumbnail = thumbnail;
    }

    public Outfit(String outfitName) {
        this(-1, outfitName, null, null, null, null, null,
                -1);
    }

    public int getOutfitId() {
        return outfitId;
    }

    public void setOutfitId(int outfitId) {
        this.outfitId = outfitId;
    }

    public String getOutfitName() {
        return outfitName;
    }

    public void setOutfitName(String outfitName) {
        this.outfitName = outfitName;
    }

    public Clothing getHat() {
        return hat;
    }

    public void setHat(Clothing hat) {
        this.hat = hat;
    }

    public Clothing getTop() {
        return top;
    }

    public void setTop(Clothing top) {
        this.top = top;
    }

    public Clothing getBottom() {
        return bottom;
    }

    public void setBottom(Clothing bottom) {
        this.bottom = bottom;
    }

    public Clothing getShoes() {
        return shoes;
    }

    public void setShoes(Clothing shoes) {
        this.shoes = shoes;
    }

    public Clothing getOuterwear() {
        return outerwear;
    }

    public void setOuterwear(Clothing outerwear) {
        this.outerwear = outerwear;
    }

    public List<Clothing> getAccessories() {
        return accessories;
    }

    public Clothing addAccessory(Clothing newAccessory) {
        accessories.add(newAccessory);
        return newAccessory;
    }
}
