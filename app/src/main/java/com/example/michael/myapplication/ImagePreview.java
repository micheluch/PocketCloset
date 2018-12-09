package com.example.michael.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImagePreview extends AppCompatActivity {

    private Button okButton, refuseButton;
    private Bitmap bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_preview);

        try {
            File file = (File)getIntent().getSerializableExtra("image");
            bit = BitmapFactory.decodeStream(new FileInputStream(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageView view = (ImageView)findViewById(R.id.imageView);
        view.setImageBitmap(bit);
        okButton = (Button)findViewById(R.id.confirmImage);
        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",true);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });

        refuseButton = (Button)findViewById(R.id.refuseImage);
        refuseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",false);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}