package com.example.michael.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;



public class OutfitActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    List<Outfit> outfitList; //switch to wearable later for clothing and outfits

    private final static int ROWS_WIDE = 3;

    private Button returnHomeButton;
    private Button addClothingButton;
    private Button deleteClothingButton;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private DBManager manager;
    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outfit_recyclerview);

        outfitList = new ArrayList<>();
        manager = new DBManager(this, null, null, 1);
        String query = "SELECT * FROM " + DBManager.TABLE_OUTFIT;
        SQLiteDatabase db = manager.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int numberOfTableElements = cursor.getCount();
        if(numberOfTableElements > 0){
            cursor.moveToFirst();
            do{
                int dummyInt = cursor.getColumnIndex(DBManager.COLUMN_OUTFIT_NAME);
                String outfitName = cursor.getString(dummyInt);
                outfitList.add(manager.getOutfit(outfitName));
            }while (cursor.moveToNext());
            cursor.close();
        }

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.outfit_recyclerview_id);
        OutfitRecyclerViewAdapter myAdapter = new OutfitRecyclerViewAdapter(this,outfitList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.outfitstoolbar_id);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(name);

        drawerLayout = (DrawerLayout) findViewById(R.id.outfitsDrawerLayoutId);
        toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.outfitsNavViewId);
        navigationView.setNavigationItemSelectedListener(this);
        dialog = new Dialog(this);
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
                intent = new Intent(OutfitActivity.this,HomePage.class);
                startActivity(intent);
                break;
            case R.id.add:
                intent = new Intent(OutfitActivity.this,CreateOutfit.class);
                startActivity(intent);
                break;
            case R.id.delete:
                intent = new Intent(OutfitActivity.this,OutfitActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.outfitsDrawerLayoutId);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
