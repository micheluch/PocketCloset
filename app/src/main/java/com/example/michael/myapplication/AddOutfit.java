package com.example.michael.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AddOutfit extends AppCompatActivity implements View.OnClickListener{

    private TextView tvName, tvDescription;
    private ImageView img;
    private CardView outfitCard;
    String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
            "eiusmod tempor incididunt ut labore et dolore magna aliqua. Lut enim ad minim " +
            "veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum " +
            "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, " +
            "sunt in culpa qui officiae deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lut enim ad minim ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_outfit);

        outfitCard = (CardView) findViewById(R.id.outfit_item_card);
        outfitCard.setOnClickListener(this);

        tvName = (TextView) findViewById(R.id.name_id);
        tvDescription = (TextView) findViewById(R.id.description_id);
        img = (ImageView) findViewById(R.id.clothing_thumbnail);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("Name");
        String description = intent.getExtras().getString("Description");
        int image = intent.getIntExtra("Thumbnail", 0);

        tvName.setText(name);
        tvDescription.setText(description);
        img.setImageResource(image);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ViewClothing.class);
        intent.putExtra("Name", "Clothing Name");
        intent.putExtra("Description", loremIpsum);
        intent.putExtra("Thumbnail", R.drawable.shirt1);
        startActivity(intent);

    }
}
