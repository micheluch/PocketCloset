package com.example.michael.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Outfit {

    private int outfitId;
    private String outfitName;
    private Clothing hat;
    private Clothing top; //shirt blouse etc
    private Clothing bottom;
    private Clothing shoes;
    private Clothing outerwear;

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    private List<Clothing> accessories;
    int thumbnail;

    public Outfit(String outfitName, int thumbnail) {
        this.outfitName = outfitName;
        accessories = new ArrayList<>();
        this.thumbnail = thumbnail;
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
