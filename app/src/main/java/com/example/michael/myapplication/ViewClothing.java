package com.example.michael.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

public class ViewClothing extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvName, tvLocation, tvColor, tvCondition;
    private ImageView img;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Dialog dialog;
    private DBManager manager;
    private Clothing clothing;
    private Spinner spinnerType, spinnerColor, spinnerCondition;
    private Clothing.clothingType type;
    private Clothing.clothingColor color;
    private Clothing.clothingCondition condition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_clothing);

        manager = new DBManager(this, null, null, 1);

        tvName = (TextView) findViewById(R.id.name_id);
        //tvLocation = (TextView) findViewById(R.id.location_id);
        tvColor = (TextView) findViewById(R.id.color);
        tvCondition = (TextView) findViewById(R.id.condition);
        //tvSearchTitle = (TextView) findViewById(R.id.toolbar_id);
        img = (ImageView) findViewById(R.id.clothing_thumbnail);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        //String description = intent.getExtras().getString("Description");
        //int image = intent.getIntExtra("Thumbnail", 0);

        clothing = manager.getClothing(name);
        tvName.setText(name);
        String jsef = clothing.getColor().toString();
        tvColor.setText(clothing.getColor().toString());
        tvCondition.setText(clothing.getCondition().toString());
        img.setImageBitmap(clothing.retrieveImageFromFolder());

        //tvDescription.setText(description);
        //tvSearchTitle.setText(name);
        //img.setImageResource(image);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

        drawerLayout = (DrawerLayout) findViewById(R.id.viewClothingDrawerLayoutId);
        toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.viewClothingNavId);
        navigationView.setNavigationItemSelectedListener(this);

        dialog = new Dialog(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();
        Intent intent;
        switch(id)
        {
            case R.id.home:
                intent = new Intent(ViewClothing.this,HomePage.class);
                startActivity(intent);
                break;
            case R.id.edit:
                //intent = new Intent(DisplayOutfit.this,DisplayOutfit.class);
                //startActivity(intent);
                View view = menuItem.getActionView();
                showEditClothingPopup(view);
                break;
            case R.id.delete:
                //intent = new Intent(ViewClothing.this,ClothingActivity.class);
                //startActivity(intent);
                manager.deleteEntry(clothing);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.viewClothingDrawerLayoutId);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showEditClothingPopup (View v) {

        TextView txtclose;
        Button btnAdd;
        dialog.setContentView(R.layout.add_clothing_popup);
        txtclose = (TextView) dialog.findViewById(R.id.txtclose);

        spinnerType = dialog.findViewById(R.id.type);
        spinnerColor = dialog.findViewById(R.id.color);
        spinnerCondition = dialog.findViewById(R.id.condition);

        spinnerType.setAdapter(new ArrayAdapter<Clothing.clothingType>(this, R.layout.support_simple_spinner_dropdown_item, Clothing.clothingType.values()));
        spinnerCondition.setAdapter(new ArrayAdapter<Clothing.clothingCondition>(this, R.layout.support_simple_spinner_dropdown_item, Clothing.clothingCondition.values()));
        spinnerColor.setAdapter(new ArrayAdapter<Clothing.clothingColor>(this, R.layout.support_simple_spinner_dropdown_item, Clothing.clothingColor.values()));

        final TextInputLayout textInputName  = dialog.findViewById(R.id.text_input_ClothingName);
        btnAdd = (Button) dialog.findViewById(R.id.addButton);

        //TextView tvName = findViewById(R.id.);

        //spinnerType.setSelection(clothing.getType());
        //spinnerColor.setSelection(clothing.getColor());
        //spinnerCondition.setSelection(clothing.getCondition());


        //textInputName.;
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = (Clothing.clothingType)spinnerType.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color = (Clothing.clothingColor)spinnerColor.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                condition = (Clothing.clothingCondition)spinnerCondition.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText entryBox = (EditText) dialog.findViewById(R.id.nameEntryBox);
                //String name = entryBox.getText().toString();
                //Clothing entry = new Clothing(name, R.drawable.taco_socks);
                //manager.addClothing(entry);

                if(!validateName(textInputName)){

                    return;
                }


                Clothing clothing = new Clothing(textInputName.getEditText().getText().toString().trim(), "", 0, type, color, condition, 0, 0);
                //clothingList.add(clothing);

                SQLiteDatabase db = manager.getWritableDatabase();
                manager.addClothing(clothing);

                String query = "SELECT * FROM " + DBManager.TABLE_CLOTHING;

                Cursor cursor = db.rawQuery(query, null);
                int numberOfTableElements = cursor.getCount();
                cursor.close();


                dialog.dismiss();
            }
        });

        txtclose = (TextView) dialog.findViewById(R.id.txtclose);

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    private boolean validateLocation(TextInputLayout inputLocation){

        String description = inputLocation.getEditText().getText().toString().trim();

        if(description.isEmpty()){
            inputLocation.setError("Enter a location.");
            return false;
        }else{

            inputLocation.setError(null);
            return true;
        }
    }
}
