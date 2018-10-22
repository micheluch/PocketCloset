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
        setContentView(R.layout.closet_recyclerview_layout);

        wearableList = new ArrayList<>();
        wearableList.add(new Clothing("Grandma's Clubbing Fit",
                R.drawable.ic_launcher_background));

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.closet_recyclerview_id);
        OneClosetRecyclerViewAdapter myAdapter = new OneClosetRecyclerViewAdapter(this,wearableList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

    }
}
