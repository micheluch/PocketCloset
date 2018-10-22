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



public class ClothingActivity extends AppCompatActivity{

    List<Clothing> clothingList; //switch to wearable later for clothing and outfits
    private final static int ROWS_WIDE = 3;

    private Button returnHomeButton;
    private Button addClothingButton;
    private Button deleteClothingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfit_recyclerview);

        clothingList = new ArrayList<>();
        clothingList.add(new Clothing("Grandma's Clubbing Pants",
                R.drawable.ic_launcher_background));

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.outfit_recyclerview_id);
        ClothingRecyclerViewAdapter myAdapter = new ClothingRecyclerViewAdapter(this,clothingList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

    }
}
