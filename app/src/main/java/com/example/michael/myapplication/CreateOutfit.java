package com.example.michael.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateOutfit extends AppCompatActivity {

    public static final int SELECT_STICKER_REQUEST_CODE = 123;
    protected MotionView motionView;
    //Button addButton;
    protected ImageView imageEntity;
    Dialog dialog;

    private final MotionView.MotionViewCallback motionViewCallback = new MotionView.MotionViewCallback() {

        @Override
        public void onEntitySelected(@Nullable MotionEntity entity) {
            //not sure if i need this yet
        }
        @Override
        public void onEntityDoubleTap(@NonNull MotionEntity entity) {
                if(entity instanceof ImageEntity){
                    motionView.removeEntity(entity);
                }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_outfit);

        motionView = (MotionView) findViewById(R.id.main_motion_view);
        motionView.setMotionViewCallback(motionViewCallback);


        //addButton = (Button)findViewById(R.id.addClothesButton);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.create_outfit_toolbar_id);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new Dialog(this);

    }

    //public void addClothes(View view){
        //Intent intent = new Intent(this, CreateOutfitSticker.class);
        //startActivityForResult(intent, SELECT_STICKER_REQUEST_CODE);

    //}

    public void addtoOutfitButton(View view){
        Intent intent = new Intent(this, CreateOutfitSticker.class);
        startActivityForResult(intent, SELECT_STICKER_REQUEST_CODE);
    }

    public void saveOutfitButton(View view){
        showSaveOutfitPopup(view);
    }

    public void showSaveOutfitPopup (View v) {

        TextView txtclose;
        Button btnAdd;
        dialog.setContentView(R.layout.save_outfit_popup);
        txtclose = (TextView) dialog.findViewById(R.id.txtClosetclose);
        btnAdd = (Button) dialog.findViewById(R.id.saveOutfitButton);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    private void addSticker(final int stickerResId) {
        motionView.post(new Runnable() {
            @Override
            public void run() {
                Layer layer = new Layer();
                Bitmap item = BitmapFactory.decodeResource(getResources(), stickerResId);

                ImageEntity entity = new ImageEntity(layer, item, motionView.getWidth(), motionView.getHeight());

                motionView.addEntityAndPosition(entity);
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.create_outfit_menu, menu);
//        return true;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_STICKER_REQUEST_CODE) {
                if (data != null) {
                    int stickerId = data.getIntExtra(CreateOutfitSticker.EXTRA_STICKER_ID, 0);
                    if (stickerId != 0) {
                        addSticker(stickerId);
                    }
                }
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_add_sticker) {
            Intent intent = new Intent(this, CreateOutfitSticker.class);
            startActivityForResult(intent, SELECT_STICKER_REQUEST_CODE);
            return true;
        } else if (item.getItemId() == R.id.main_save_outfit) {
            //save
        }
        return super.onOptionsItemSelected(item);
    }
}
