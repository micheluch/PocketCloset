package com.example.michael.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.support.annotation.Nullable;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pocketcloset.db";
    private static final String TABLE_OUTFIT = "outfits";
    private static final String TABLE_CLOSET = "closets";
    private static final String TABLE_CLOTHING = "clothing";

    private static final String COLUMN_ID = "id";

    //Clothing database information
    private static final String COLUMN_CLOTHING_NAME = "clothing_name";
    private static final String COLUMN_CLOTHING_CONDITION = "clothing_condition";
    private static final String COLUMN_CLOTHING_PICTURE = "picture";

    //Closet database information
    private static final String COLUMN_CLOSET_NAME = "closet_name";
    private static final String COLUMN_CLOSET_LOCATION = "closet_location";
    private static final String COLUMN_CLOSET_ITEM_COUNT = "closet_size";
    private static final String COLUMN_THUMBNAIL = "thumbnail";

    //Outfit database information
    private static final String COLUMN_OUTFIT_NAME = "outfit_name";
    private static final String COLUMN_OUTFIT_HEAD = "outfit_headwear";
    private static final String COLUMN_OUTFIT_TOP = "outfit_top";
    private static final String COLUMN_OUTFIT_BOTTOM = "outfit_bottom";
    private static final String COLUMN_OUTFIT_SHOES = "outfit_shoes";
    private static final String COLUMN_OUTFIT_ACCESSORIES= "outfit_accessories";
    private static final String COLUMN_OUTFIT_OUTERWEAR = "outfit_outerwear";



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

        query = "CREATE TABLE " + TABLE_OUTFIT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OUTFIT_NAME + " TEXT, " +
                COLUMN_OUTFIT_HEAD + " TEXT, " +
                COLUMN_OUTFIT_TOP + " TEXT, " +
                COLUMN_OUTFIT_BOTTOM + " TEXT, " +
                COLUMN_OUTFIT_SHOES + " TEXT, " +
                COLUMN_OUTFIT_OUTERWEAR + " TEXT, " +
                COLUMN_OUTFIT_ACCESSORIES + " BLOB " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_CLOSET + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLOSET_NAME + " TEXT, " +
                COLUMN_CLOSET_LOCATION + " TEXT, " +
                COLUMN_CLOSET_ITEM_COUNT + " INTEGER " +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOSET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTFIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHING);
        onCreate(db);
    }

    //TODO DEFINE HOW TO ADD TO DATABASE
    //We need to hash out how we are constructing classes. In android
    //adding to database can be done with values as done here
    public void addOutfit(Outfit newOutfit){
        ContentValues valuesToAdd = new ContentValues();
        valuesToAdd.put(COLUMN_OUTFIT_NAME, newOutfit.getOutfitName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_OUTFIT, null, valuesToAdd);
        db.close(); //MUST ALWAYS CLOSE

    }

    public Outfit getOutfit(int outfitID){
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_OUTFIT +
                " WHERE " +
                COLUMN_ID +
                " = " + outfitID;
        //should consider adding a Log
        //    Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor != null)
            cursor.moveToFirst();
        return new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_NAME)),0);

    }

    public void deleteOutfit(String outfitName){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " +
                TABLE_OUTFIT +
                " WHERE " +
                COLUMN_OUTFIT_NAME +
                "=\"" +
                outfitName +
                "\";";
        db.execSQL(query);
    }

    public void addCloset(Closet newCloset){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOSET_NAME, newCloset.getClosetName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CLOSET, null, values);
        db.close();
    }

    public Outfit getCloset(int closetID){
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOSET +
                " WHERE " +
                COLUMN_ID +
                " = " + closetID;
        //should consider adding a Log
        //    Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor != null)
            cursor.moveToFirst();
        return new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_NAME)),0);

    }

    public void deleteCloset(String closetName){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " +
                TABLE_CLOSET +
                " WHERE " +
                COLUMN_CLOSET_NAME +
                "=\"" +
                closetName +
                "\";";
        db.execSQL(query);
    }

    public void addClothing(Clothing newClothing){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOTHING_NAME, newClothing.getClothingName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CLOTHING, null, values);
        db.close();
    }

    public Outfit getClothing(int clothingID){
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOTHING +
                " WHERE " +
                COLUMN_ID +
                " = " + clothingID;
        //should consider adding a Log
        //    Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor != null)
            cursor.moveToFirst();
        return new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_NAME)),0);

    }

    public void deleteClothing(String clothingName){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " +
                TABLE_CLOTHING +
                " WHERE " +
                COLUMN_CLOTHING_NAME +
                "=\"" +
                clothingName +
                "\";";
        db.execSQL(query);
    }
}
