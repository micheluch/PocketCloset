package com.example.michael.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchClothesForOutfit extends AppCompatActivity {

    List<Clothing> clothingList; //switch to wearable later for clothing and outfits
    private DBManager manager;
    private final static int ROWS_WIDE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entries_to_closet_recycler_view);

        clothingList = new ArrayList<>();
        manager = new DBManager(this, null, null, 1);
        clothingList.addAll(manager.getAllClothes());

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.add_to_closet_recyclerview_id);
        ClothingRecyclerViewAdapter myAdapter = new ClothingRecyclerViewAdapter(this, clothingList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this, ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

    }
}
