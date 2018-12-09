package com.example.michael.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class ImagePreview extends AppCompatActivity {

    private Button okButton, refuseButton;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_preview);

        File file = (File)getIntent().getSerializableExtra("image");

        okButton = (Button)dialog.findViewById(R.id.saveButton);
        okButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        refuseButton = (Button)dialog.findViewById(R.id.refuseImage);
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