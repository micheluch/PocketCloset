package com.example.michael.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

public class DisplayOutfit extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private TextView tvName, tvDescription;
    private CardView outfitCard, topCard, bottomCard, accessoryCard, shoeCard;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Outfit outfit;
    private ImageView img;
    private DBManager manager;
    private ImageButton imageButton;
    private LinearLayout layout;
    private BottomSheetBehavior bab;
    private ListView listView;
    boolean b;
    String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do " +
            "eiusmod tempor incididunt ut labore et dolore magna aliqua. Lut enim ad minim " +
            "veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo " +
            "consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum " +
            "dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, " +
            "sunt in culpa qui officiae deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Lut enim ad minim ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_outfit);

        manager = new DBManager(this, null, null, 1);
        outfitCard = (CardView) findViewById(R.id.outfit_card);
        outfitCard.setOnClickListener(this);

        tvName = (TextView) findViewById(R.id.name_id);
        tvDescription = (TextView) findViewById(R.id.description_id);
        img = (ImageView) findViewById(R.id.outfit_image);

        Intent intent = getIntent();
        //outfit = (Outfit)intent.getSerializableExtra("outfit");

        String name = intent.getExtras().getString("Name");
        outfit = manager.getOutfit(name);
        //String description = intent.getExtras().getString("Description");
        //int image = intent.getIntExtra("Thumbnail", 0);

        tvName.setText(name);
        tvDescription.setText(outfit.getDescription());
        img.setImageBitmap(outfit.retrieveImageFromFolder());

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

        drawerLayout = (DrawerLayout) findViewById(R.id.addOufitDrawerLayoutId);
        toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.addOufitNavViewId);
        navigationView.setNavigationItemSelectedListener(this);

        //listview = findViewById(R.id.list);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ViewClothing.class);
        intent.putExtra("Name", "Clothing Name");
        intent.putExtra("Description", loremIpsum);
        intent.putExtra("Thumbnail", R.drawable.shirt1);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item))
        {
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
                intent = new Intent(DisplayOutfit.this,HomePage.class);
                startActivity(intent);
                break;
            case R.id.edit:
                intent = new Intent(DisplayOutfit.this,EditOutfit.class);
                startActivity(intent);
                break;
            case R.id.add:
                intent = new Intent(DisplayOutfit.this,ClothingActivity.class);
                startActivity(intent);
                break;
            case R.id.delete:
                //intent = new Intent(DisplayOutfit.this,OutfitActivity.class);
                //startActivity(intent);
                manager.deleteEntry(outfit);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.addOufitDrawerLayoutId);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showClothingList(View view){

        Intent intent = new Intent(this,ClothingList.class);
        intent.putExtra("name", outfit.getEntryName());
        startActivity(intent);

//       layout = findViewById(R.id.bottom_id_clothing_list);
//       setContentView(R.layout.bottom_sheet_clothing_list);
//       listView = findViewById(R.id.list);
//        bab = BottomSheetBehavior.from(layout);
//        bab.setState(BottomSheetBehavior.STATE_HIDDEN);
//                bab.setState(BottomSheetBehavior.STATE_EXPANDED);
//                switch(view.getId()){
//                    case R.id.clothingListButton:
//                        if(b == false){
//                            bab.setState(BottomSheetBehavior.STATE_EXPANDED);
//                        }else{
//                            bab.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                        }b=!b;
//                        break;
//                    default:
//                        break;
//                }


    }


}
