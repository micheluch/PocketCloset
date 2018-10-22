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



public class OutfitActivity extends AppCompatActivity{

    List<Outfit> outfitList; //switch to wearable later for clothing and outfits
    private final static int ROWS_WIDE = 3;

    private Button returnHomeButton;
    private Button addClothingButton;
    private Button deleteClothingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfit_recyclerview);

        outfitList = new ArrayList<>();
        outfitList.add(new Outfit("Grandma's Clubbing Fit", R.drawable.shirt1));
        outfitList.add(new Outfit("Grandma's January Rave Attire", R.drawable.outfit1));
        outfitList.add(new Outfit("Grandma's Formal Wear", R.drawable.outfit2));
        outfitList.add(new Outfit("Grandma's Graduation Gown", R.drawable.pants));
        outfitList.add(new Outfit("Grandma's Bad Mood Outfit", R.drawable.closet1));
        outfitList.add(new Outfit("Grandma's Workout Clothes", R.drawable.closet2));
        outfitList.add(new Outfit("Grandma's Easter Mass Fit", R.drawable.icon));
        outfitList.add(new Outfit("Grandma's Halloween 2018", R.drawable.pc_icon));
        outfitList.add(new Outfit("Grandma's Happy Day", R.drawable.outfit2));
        outfitList.add(new Outfit("Grandma's Cookie Baking Fit", R.drawable.outfit1));
        outfitList.add(new Outfit("Grandma's Breakdance Gear", R.drawable.shirt1));
        outfitList.add(new Outfit("Grandma's Fire Fighter Outfit", R.drawable.hanger_icon));


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.outfit_recyclerview_id);
        OutfitRecyclerViewAdapter myAdapter = new OutfitRecyclerViewAdapter(this,outfitList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

    }
}
