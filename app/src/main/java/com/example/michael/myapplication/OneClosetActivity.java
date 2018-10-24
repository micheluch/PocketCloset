package com.example.michael.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;



public class OneClosetActivity extends AppCompatActivity{

    List<Clothing> wearableList; //switch to wearable later for clothing and outfits
    private final static int ROWS_WIDE = 3;

    private Button returnHomeButton;
    private Button addClothingButton;
    private Button deleteClothingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clothing_recyclerview);

        wearableList = new ArrayList<>();
        wearableList.add(new Clothing("Grandma's Clubbing Fit", R.drawable.clubbing_outfit));

        wearableList.add(new Clothing("Grandma's Cookie Baking Fit", R.drawable.grandmas_cooking_outfit));
        wearableList.add(new Clothing("Grandma's Breakdance Gear", R.drawable.grandmas_breakdance_gear));
        wearableList.add(new Clothing("Grandma's Fire Fighter Outfit", R.drawable.firefighter_outfit));
        wearableList.add(new Clothing("Grandma's Graduation Gown", R.drawable.graduation_gown));
        wearableList.add(new Clothing("Grandma's Halloween 2018", R.drawable.halloween_outfit));
        wearableList.add(new Clothing("Grandma's Bad Mood Outfit", R.drawable.teacher_outfit));
        wearableList.add(new Clothing("Grandma's Formal Clothes", R.drawable.closet2));
        wearableList.add(new Clothing("Grandma's Easter Mass Fit", R.drawable.easter_mass_outfit));
        wearableList.add(new Clothing("Grandma's Spring Attire", R.drawable.outfit1));
        wearableList.add(new Clothing("Grandma's Casual Wear", R.drawable.outfit2));
        wearableList.add(new Clothing("Grandma's Happy Day", R.drawable.happy_outfit));


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.clothing_recyclerview_id);
        OneClosetRecyclerViewAdapter myAdapter = new OneClosetRecyclerViewAdapter(this,wearableList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

    }
}
