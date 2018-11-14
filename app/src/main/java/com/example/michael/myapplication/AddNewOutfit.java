package com.example.michael.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class AddNewOutfit extends AppCompatActivity implements View.OnClickListener {

    private CardView outfitCard, topCard, bottomCard, accessoryCard, shoeCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_outfit);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ViewClothing.class);
        startActivity(intent);
    }
}
