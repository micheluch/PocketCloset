package com.example.michael.myapplication;
import com.example.michael.myapplication.Closet;
import com.example.michael.myapplication.Entry;
import com.example.michael.myapplication.Outfit;

import java.util.ArrayList;
import java.util.List;

public class Clothing extends Entry {

    //Clothing-only data members

    public enum clothingType {shirt, pants, shoes, other} //can get integer value by doing getOrdinal();
    private clothingType type;
    public enum clothingColor {white, black, grey, red, orange, yellow, green, blue, purple, pink, brown}
    private clothingColor color;
    public enum clothingCondition {dirty, borrowed, ready}
    private clothingCondition condition;
    private int Xcoordinate;
    private int Ycoordinate;

    public Clothing(String entryName, String path, int id, clothingType type, clothingColor color,
                    clothingCondition condition, int xcoordinate, int ycoordinate) {
        super(entryName, path, id);
        setEntryType(pocketClassType.CLOTHING_TYPE);
        this.type = type;
        this.color = color;
        this.condition = condition;
        Xcoordinate = xcoordinate;
        Ycoordinate = ycoordinate;
    }

    public Clothing(String entryName, String path, int id, clothingType type, clothingColor color, clothingCondition condition) {
        this(entryName, path, id, type, color, condition, 0, 0);
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


    public clothingColor getColor() {
        return color;
    }

    public void setColor(clothingColor color) {
        this.color = color;
    }

    public clothingCondition getCondition() {
        return condition;
    }

    public void setCondition(clothingCondition condition) {
        this.condition = condition;
    }

    public int getType() {
        return type.ordinal();
    }

    public void setType(clothingType type) {
        this.type = type;
    }

    public int getXcoordinate() {
        return Xcoordinate;
    }

    public void setXcoordinate(int xcoordinate) {
        Xcoordinate = xcoordinate;
    }

    public int getYcoordinate() {
        return Ycoordinate;
    }

    public void setYcoordinate(int ycoordinate) {
        Ycoordinate = ycoordinate;
    }
}
