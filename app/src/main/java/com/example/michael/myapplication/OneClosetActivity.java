package com.example.michael.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



public class OneClosetActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private final static int ROWS_WIDE = 3;

    private Closet closet;
    private CardView outfitCard;
    private Button returnHomeButton;
    private Button addClothingButton;
    private Button deleteClothingButton;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet_recyclerview_layout);
        // Actually important stuff:
        Intent intent = getIntent();
        String closetName = intent.getExtras().getString("Name");
        manager = new DBManager(this, null, null, 0);
        closet = manager.getCloset(closetName);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.allClosetsToobar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(closetName);


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.closet_recyclerview_id);
        OneClosetRecyclerViewAdapter myAdapter = new OneClosetRecyclerViewAdapter(this,closet.contentList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this,ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

        drawerLayout = (DrawerLayout) findViewById(R.id.allClosetsDrawerLayoutId);
        toggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.allClosetsNavViewId);
        navigationView.setNavigationItemSelectedListener(this);

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
                intent = new Intent(OneClosetActivity.this,HomePage.class);
                startActivity(intent);
                break;
            case R.id.add:
                intent = new Intent(OneClosetActivity.this,AddToCloset.class);
                startActivity(intent);
                break;
            case R.id.delete:
                manager.deleteEntry(closet);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.allClosetsDrawerLayoutId);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
