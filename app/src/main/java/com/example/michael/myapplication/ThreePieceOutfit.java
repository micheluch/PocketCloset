package com.example.michael.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreePieceOutfit extends AppCompatActivity implements View.OnClickListener{

   private CardView outfitCard, topCard, accessoryCard, shoeCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_piece_outfit);


        topCard = (CardView) findViewById(R.id.top_card);
        //bottomCard = (CardView) findViewById(R.id.bottoms_card);
        accessoryCard = (CardView) findViewById(R.id.accessory_card);
        shoeCard = (CardView) findViewById(R.id.shoes_card);

        topCard.setOnClickListener(this);
        //bottomCard.setOnClickListener(this);
        accessoryCard.setOnClickListener(this);
        shoeCard.setOnClickListener(this);
        //outfitCard.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ViewClothing.class);
        startActivity(intent);

    }


}
