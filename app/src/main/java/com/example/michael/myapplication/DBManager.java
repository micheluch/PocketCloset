package com.example.michael.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.support.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pocketcloset.db";
    public static final String TABLE_OUTFIT = "outfits";
    public static final String TABLE_CLOSET = "closets";
    public static final String TABLE_CLOTHING = "clothing";

    public static final String COLUMN_ID = "id";

    //Clothing database information
    public static final String COLUMN_CLOTHING_NAME = "clothing_name";
    public static final String COLUMN_CLOTHING_CONDITION = "clothing_condition";
    public static final String COLUMN_CLOTHING_PICTURE = "picture";

    //Closet database information
    public static final String COLUMN_CLOSET_NAME = "closet_name";
    public static final String COLUMN_CLOSET_LOCATION = "closet_location";
    public static final String COLUMN_CLOSET_ITEM_COUNT = "closet_size";
    public static final String COLUMN_THUMBNAIL = "thumbnail";

    //Outfit database information
    public static final String COLUMN_OUTFIT_NAME = "outfit_name";
    public static final String COLUMN_OUTFIT_HEAD = "outfit_headwear";
    public static final String COLUMN_OUTFIT_TOP = "outfit_top";
    public static final String COLUMN_OUTFIT_BOTTOM = "outfit_bottom";
    public static final String COLUMN_OUTFIT_SHOES = "outfit_shoes";
    public static final String COLUMN_OUTFIT_ACCESSORIES= "outfit_accessories";
    public static final String COLUMN_OUTFIT_OUTERWEAR = "outfit_outerwear";



    public DBManager(@Nullable Context context, @Nullable String name,
                     @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CLOTHING + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLOTHING_NAME + " TEXT, " +
                COLUMN_CLOTHING_PICTURE + " INTEGER, " +
                COLUMN_CLOTHING_CONDITION + " TEXT " +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
