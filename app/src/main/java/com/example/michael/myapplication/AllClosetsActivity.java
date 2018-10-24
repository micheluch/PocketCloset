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



public class AllClosetsActivity extends AppCompatActivity{

    List<Closet> closetList;
    private final static int ROWS_WIDE = 3;

    private Button returnHomeButton;
    private Button addClothingButton;
    private Button deleteClothingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet_recyclerview_layout);

        closetList = new ArrayList<>();
        closetList.add(new Closet("Grandma's Upstairs Closet","The closet at Grandma's House",
                R.drawable.double_closet_doors, 'W'));
        closetList.add(new Closet("Grandma's Ski Resort","Michael's ski resort",
                R.drawable.wooden_closet_doors_1, 'W'));
        closetList.add(new Closet("Grandma's Downstairs Closet","The guest bedroom closet at Grandma's House",
                R.drawable.white_bypass_doors, 'W'));
        closetList.add(new Closet("Grandpa's Forbidden Closet","The guest bedroom closet at Grandma's House",
                R.drawable.dark_wood_doors, 'W'));
        closetList.add(new Closet("Grandpa's Stylin' Closet ","The guest bedroom closet at Grandma's House",
                R.drawable.mirrored_wooden_doors, 'W'));
        closetList.add(new Closet("Toy Closet","The guest bedroom closet at Grandma's House",
                R.drawable.red_stainless_doors, 'W'));
        closetList.add(new Closet("Great Aunt Susie's Guest Bedroom","The guest bedroom closet at Grandma's House",
                R.drawable.modern_bar_door, 'W'));

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.closet_recyclerview_id);
        AllClosetsRecyclerViewAdapter myAdapter = new AllClosetsRecyclerViewAdapter(this,closetList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);
    }

}
