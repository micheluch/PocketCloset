package com.example.michael.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ClothingList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    List<RowItem> rowItems;
    DBManager manager;
    Outfit outfit;
    List<Clothing> clothingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing_list);

        manager = new DBManager(this, null, null, 1);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");

        outfit = manager.getOutfit(name);

        clothingList = outfit.getClothingList();

//        rowItems = new ArrayList<RowItem>();
//        for(int i = 0; i < clothingList.size(); i++){
//            RowItem item = new RowItem(clothingList.get(i).getClothingName(), clothingList.get(i).getPath());
//            rowItems.add(item);
//        }

        listView = (ListView)findViewById(R.id.list);
        //String[] names = new String[clothingList.size()];
        String[] names = new String[1];

 //       for(int i = 0; i < clothingList.size(); i++){
 //           names[i] = clothingList.get(i).getEntryName();
 //       }

        names[0] = "asdsa";
        //ClothingListViewAdapter clva = new ClothingListViewAdapter(this, R.layout.clothing_item_list, rowItems);
        //listView.setAdapter(clva);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.clothing_item_list, R.id.clothing_name, names));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, ViewClothing.class);
        intent.putExtra("name", listView.getItemAtPosition(position).toString());
        startActivity(intent);

    }
}
