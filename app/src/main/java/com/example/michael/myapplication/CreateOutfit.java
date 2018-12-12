package com.example.michael.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateOutfit extends AppCompatActivity {

    public static final int SELECT_STICKER_REQUEST_CODE = 123;
    protected MotionView motionView;
    //Button addButton;
    protected ImageView imageEntity;
    Dialog dialog;
    Bitmap outfitImage;
    Outfit outfit;
    DBManager dbManager;
    List <Clothing> clothingList = new ArrayList<>();
    private final MotionView.MotionViewCallback motionViewCallback = new MotionView.MotionViewCallback() {

        @Override
        public void onEntitySelected(@Nullable MotionEntity entity) {
            //not sure if i need this yet
        }
        @Override
        public void onEntityDoubleTap(@NonNull MotionEntity entity) {
                if(entity instanceof ImageEntity){
                    motionView.removeEntity(entity);
                    clothingList.remove(entity);
                }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_outfit);

        motionView = (MotionView) findViewById(R.id.main_motion_view);
        motionView.setMotionViewCallback(motionViewCallback);

        dbManager = new DBManager(this, null, null, 1);
        this.outfit = new Outfit("",0,"");
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
        ViewGroup v = (ViewGroup) findViewById(R.id.main_motion_view);
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        this.outfitImage = bitmap;
    }

    public void showSaveOutfitPopup (View v) {

        TextView txtclose;
        Button btnSave;
        final TextInputLayout textInputOutfitName;
        final TextInputLayout textInputDescription;

        dialog.setContentView(R.layout.save_outfit_popup);
        txtclose = (TextView) dialog.findViewById(R.id.txtClosetclose);
        btnSave = (Button) dialog.findViewById(R.id.saveOutfitButton);
        textInputOutfitName = dialog.findViewById(R.id.text_input_outfitName);
        textInputDescription = dialog.findViewById(R.id.text_input_description);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName(textInputOutfitName) | !validateDescription(textInputDescription)){

                    return;
                }

                outfit.setEntryName(textInputOutfitName.getEditText().getText().toString().trim());


                //save image
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("outfits", Context.MODE_PRIVATE);
                if(!directory.exists()){
                    directory.mkdir();
                }
                File mypath = new File(directory, outfit.getEntryName() + ".png");
                FileOutputStream fos = null;
                try{
                    fos = new FileOutputStream(mypath);
                    outfitImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();

                }catch(Exception e){

                    e.printStackTrace();
                }

                outfit.setPath(directory.getAbsolutePath() + "/" + outfit.getEntryName() + ".png");
                //SQLiteDatabase db = dbManager.getWritableDatabase();
                //set image
                outfit.setDescription(textInputDescription.getEditText().getText().toString().trim());
                outfit.setImage();

                for(int i = 0; i < clothingList.size(); i++){

                    outfit.addClothingToOutfit((Clothing) clothingList.get(i));

                }
                dbManager.addOutfit(outfit);
                //get image

                ImageView img = (ImageView) dialog.findViewById(R.id.viewImage);
                img.setImageBitmap(outfit.getImage());

                dialog.dismiss();
                Intent i = new Intent(CreateOutfit.this,OutfitActivity.class);
                CreateOutfit.this.startActivity(i);
            }
        });
        dbManager.close();
        dialog.show();
    }

    private boolean validateName(TextInputLayout inputname){

        String name = inputname.getEditText().getText().toString().trim();

        if(name.isEmpty()){
            inputname.setError("Enter a name.");
            return false;
        }else{

            inputname.setError(null);
            return true;
        }
    }

    private boolean validateDescription(TextInputLayout inputdescription){

        String description = inputdescription.getEditText().getText().toString().trim();

        if(description.isEmpty()){
            inputdescription.setError("Enter a name.");
            return false;
        }else{

            inputdescription.setError(null);
            return true;
        }
    }

    private void addSticker(final String stickerResId) {
        motionView.post(new Runnable() {
            @Override
            public void run() {
                Layer layer = new Layer();
                //Bitmap item = BitmapFactory.decodeResource(getResources(), stickerResId);
                Bitmap item = dbManager.getClothing(stickerResId).getImage();
                clothingList.add(dbManager.getClothing(stickerResId));

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
                    String stickerId = data.getStringExtra(CreateOutfitSticker.EXTRA_STICKER_ID);
                    if (stickerId != null) {
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
