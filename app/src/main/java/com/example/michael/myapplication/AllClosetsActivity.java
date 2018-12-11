package com.example.michael.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
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
import android.widget.TextView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AllClosetsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Serializable {

    List<Closet> closetList;
    private final static int ROWS_WIDE = 3;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Dialog dialog;
    private File imageFile;
    private DBManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet_recyclerview_layout);

        closetList = new ArrayList<>();
        manager = new DBManager(this, null, null, 1);


        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.closet_recyclerview_id);
        AllClosetsRecyclerViewAdapter myAdapter = new AllClosetsRecyclerViewAdapter(this, closetList);
        my_recycler_view.setLayoutManager(new GridLayoutManager(this, ROWS_WIDE));
        my_recycler_view.setAdapter(myAdapter);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.allClosetsToobar_id);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle(name);

        drawerLayout = (DrawerLayout) findViewById(R.id.allClosetsDrawerLayoutId);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.allClosetsNavViewId);
        navigationView.setNavigationItemSelectedListener(this);

        dialog = new Dialog(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        Intent intent;
        switch (id) {
            case R.id.home:
                intent = new Intent(AllClosetsActivity.this, HomePage.class);
                startActivity(intent);
                break;
            case R.id.add:
                View view = menuItem.getActionView();
                showAddClosetPopup(view);
                break;
            case R.id.delete:
                intent = new Intent(AllClosetsActivity.this, OutfitActivity.class);
                startActivity(intent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.allClosetsDrawerLayoutId);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showAddClosetPopup(View v) {

        TextView txtclose;
        CardView camera;
        Button btnAdd;
        dialog.setContentView(R.layout.add_closet_popup);
        final TextInputLayout textInputClosetName;
        final TextInputLayout textInputDescription;
        txtclose = (TextView) dialog.findViewById(R.id.txtClosetclose);
        btnAdd = (Button) dialog.findViewById(R.id.addClosetButton);
        textInputClosetName = dialog.findViewById(R.id.text_input_ClosetName);
        textInputDescription = dialog.findViewById(R.id.text_input_description);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName(textInputClosetName)) {

                    return;
                }

                Closet closet = new Closet(textInputClosetName.getEditText().getText().toString().trim(), "",
                        Entry.pocketClassType.CLOSET_TYPE, "");

                SQLiteDatabase db = manager.getWritableDatabase();
                manager.addCloset(closet);


                String query = "SELECT * FROM " + DBManager.TABLE_CLOSET;

                Cursor cursor = db.rawQuery(query, null);
                int numberOfTableElements = cursor.getCount();
                cursor.close();
                dialog.cancel();
            }

        });

        manager.close();

        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        camera = (CardView) dialog.findViewById(R.id.cameraViewCloset);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Camera.class);
                startActivityForResult(i, 1);
            }
        });
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                imageFile = (File) data.getSerializableExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //No picture was taken
            }
        }
    }


    private boolean validateName(TextInputLayout inputname) {

        String name = inputname.getEditText().getText().toString().trim();

        if (name.isEmpty()) {
            inputname.setError("Enter a name.");
            return false;
        } else {

            inputname.setError(null);
            return true;
        }
    }

    private boolean validateDescription(TextInputLayout inputdescription) {

        String description = inputdescription.getEditText().getText().toString().trim();

        if (description.isEmpty()) {
            inputdescription.setError("Enter a name.");
            return false;
        } else {

            inputdescription.setError(null);
            return true;
        }
    }
}
