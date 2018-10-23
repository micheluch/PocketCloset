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
        closetList.add(new Closet("Grandma's Closet","The closet at Grandma's House",
                R.drawable.ic_launcher_background, 'W'));
        closetList.add(new Closet("Jessica's Closet","Jessica's Home",
                R.drawable.ic_launcher_background, 'W'));
        closetList.add(new Closet("Samier's Closet","Samier's beach house",
                R.drawable.ic_launcher_background, 'W'));
        closetList.add(new Closet("J's Closet","J's Mansion",
                R.drawable.ic_launcher_background, 'W'));
        closetList.add(new Closet("Michael's Closet","Michael's ski resort",
                R.drawable.ic_launcher_background, 'W'));
        closetList.add(new Closet("Grandma's Downstairs","The guest bedroom closet at Grandma's House",
                R.drawable.ic_launcher_background, 'W'));

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.closet_recyclerview_id);
        AllClosetsRecyclerViewAdapter myAdapter = new AllClosetsRecyclerViewAdapter(this,closetList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);
    }

}
