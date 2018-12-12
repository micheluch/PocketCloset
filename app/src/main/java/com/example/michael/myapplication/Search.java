package com.example.michael.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private List<Entry> searchItems = new ArrayList<>();
    private SearchRecyclerViewAdapter mAdapter;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DBManager mDBManager = new DBManager(this, "", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchItems.addAll(mDBManager.getAllClothes());
        searchItems.addAll(mDBManager.getAllOutfits());
        searchItems.addAll(mDBManager.getAllClosets());


        mRecyclerView = findViewById(R.id.search_recyclerview_id);
        mAdapter = new SearchRecyclerViewAdapter(this,searchItems);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        toolbar = (Toolbar)findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_items,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        List<Entry> newList = new ArrayList<>();


        for(Entry item : searchItems)
        {
            if(item.getEntryName().toLowerCase().contains(userInput)){
                newList.add(item);
            }
        }

        mAdapter.updateList(newList);
        return true;
    }
}
