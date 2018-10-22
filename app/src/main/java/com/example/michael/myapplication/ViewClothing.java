package com.example.michael.myapplication;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewClothing extends AppCompatActivity {

    private TextView tvName, tvDescription;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clothing);

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
}
