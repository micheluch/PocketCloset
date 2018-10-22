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
        outfitList.add(new Outfit("Grandma's Clubbing Fit", R.drawable.ic_launcher_foreground));

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.outfit_recyclerview_id);
        OutfitRecyclerViewAdapter myAdapter = new OutfitRecyclerViewAdapter(this,outfitList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

    }
}
