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
        wearableList.add(new Clothing("Grandma's Clubbing Fit",
                R.drawable.ic_launcher_background));
        wearableList.add(new Clothing("Grandma's Clubbing Fit", R.drawable.shirt1));
        wearableList.add(new Clothing("Grandma's January Rave Attire", R.drawable.outfit1));
        wearableList.add(new Clothing("Grandma's Formal Wear", R.drawable.outfit2));
        wearableList.add(new Clothing("Grandma's Graduation Gown", R.drawable.pants));
        wearableList.add(new Clothing("Grandma's Bad Mood Outfit", R.drawable.closet1));
        wearableList.add(new Clothing("Grandma's Workout Clothes", R.drawable.closet2));
        wearableList.add(new Clothing("Grandma's Easter Mass Fit", R.drawable.icon));
        wearableList.add(new Clothing("Grandma's Halloween 2018", R.drawable.pc_icon));
        wearableList.add(new Clothing("Grandma's Happy Day", R.drawable.outfit2));
        wearableList.add(new Clothing("Grandma's Cookie Baking Fit", R.drawable.outfit1));
        wearableList.add(new Clothing("Grandma's Breakdance Gear", R.drawable.shirt1));
        wearableList.add(new Clothing("Grandma's Fire Fighter Outfit", R.drawable.hanger_icon));

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.clothing_recyclerview_id);
        OneClosetRecyclerViewAdapter myAdapter = new OneClosetRecyclerViewAdapter(this,wearableList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

    }
}
