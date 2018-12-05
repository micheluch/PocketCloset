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


public class Outfit extends Entry{

    private List<Clothing> clothingList;
    private String description;
    private final int DEFAULT_INVALID_CONDITION = -1;
    private Bitmap outfitImage;
    private String path;
    private Context context;

    public Outfit( String outfitName, List<Clothing> clothingList, String description, int id, int thumbnail) {
        super(outfitName,thumbnail, id);
        this.clothingList = clothingList;
        this.description = description;
    }

    public Outfit(String outfitName, int id) {
        this(outfitName, new ArrayList<Clothing>(), null, id, -1); //need to get rid of thumbnail from everywher
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

    public void setOutfitImage(Bitmap image){
        outfitImage = image;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public Bitmap retrieveImageFromFolder(){

        try{
            //ContextWrapper c = new ContextWrapper(context.getApplicationContext());
            //File path1 = c.getDir("outfits", Context.MODE_PRIVATE);

            File f = new File(this.path, getEntryName() + ".png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return null;
    }


}
