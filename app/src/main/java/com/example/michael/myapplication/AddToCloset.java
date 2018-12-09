package com.example.michael.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddToCloset extends AppCompatActivity {
    List<Entry> allPossibleItems;
    DBManager dbManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allPossibleItems = new ArrayList<>();
        this.dbManager = new DBManager(this,null,null,1);
        populateEntryList();
    }

    public void populateEntryList(){
        allPossibleItems.addAll(dbManager.getAllOutfits());
        allPossibleItems.addAll(dbManager.getAllClothes());
    }
}
