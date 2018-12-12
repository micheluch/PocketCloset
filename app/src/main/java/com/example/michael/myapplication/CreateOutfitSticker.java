package com.example.michael.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CreateOutfitSticker extends AppCompatActivity {

    public static final String EXTRA_STICKER_ID = "extra_sticker_id";
    private List<Entry> entryList;
    private List<Bitmap> entryBitmapList;
    private DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_sticker_recycler_view);

        entryList = new ArrayList<>();
        entryBitmapList = new ArrayList<>();
        manager = new DBManager(this, null, null, 1);
        SQLiteDatabase db = manager.getWritableDatabase();
        String query = "SELECT * FROM " + DBManager.TABLE_CLOTHING;
        Cursor cursor = db.rawQuery(query, null);
        int numberOfTableElements = cursor.getCount();
        if(numberOfTableElements > 0){
            cursor.moveToFirst();
            do{
                int dummyInt = cursor.getColumnIndex(DBManager.COLUMN_CLOTHING_NAME);
                String clothingName = cursor.getString(dummyInt);
                Clothing currentClothing = manager.getClothing(clothingName);
                currentClothing.setImage();
                entryList.add(currentClothing);
                entryBitmapList.add(currentClothing.getImage());
            } while (cursor.moveToNext());
            cursor.close();
        }

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.stickers_recycler_view);
        GridLayoutManager glm = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(glm);

//        List<Integer> stickers = new ArrayList<>(stickerIds.length);
//        for (Integer id : stickerIds) {
//            stickers.add(id);
//        }

        recyclerView.setAdapter(new StickersAdapter(entryBitmapList, this));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onStickerSelected(int position) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_STICKER_ID, entryList.get(position).getEntryName());
        setResult(RESULT_OK, intent);
        finish();
    }

    class StickersAdapter extends RecyclerView.Adapter<StickersAdapter.StickerViewHolder> {

        private final List<Bitmap> stickerIds;
        private final Context context;
        private final LayoutInflater layoutInflater;

        StickersAdapter(@NonNull List<Bitmap> stickerIds, @NonNull Context context) {
            this.stickerIds = stickerIds;
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public StickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StickerViewHolder(layoutInflater.inflate(R.layout.activity_create_outfit_sticker, parent, false));
        }

        @Override
        public void onBindViewHolder(StickerViewHolder holder, int position) {
            //Bitmap original = getItem(position);
            //ByteArrayOutputStream out = new ByteArrayOutputStream();
            //original.compress(Bitmap.CompressFormat.PNG, 0, out);
            //Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));


            //holder.image.setImageBitmap(decoded);
            holder.image.setImageBitmap(getItem(position));

        }

        @Override
        public int getItemCount() {
            return stickerIds.size();
        }

        private Bitmap getItem(int position) {
            return stickerIds.get(position);
        }

        class StickerViewHolder extends RecyclerView.ViewHolder {

            ImageView image;

            StickerViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.sticker_image);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = getAdapterPosition();
                        if (pos >= 0) { // might be NO_POSITION
                            onStickerSelected(pos);
                        }
                    }
                });
            }
        }
    }
}
