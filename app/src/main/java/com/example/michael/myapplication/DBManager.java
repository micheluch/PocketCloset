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
    public static final String DATABASE_NAME = "pocketcloset.db";
    public static final String TABLE_OUTFIT = "outfits";
    public static final String TABLE_CLOSET = "closets";
    public static final String TABLE_CLOTHING = "clothing";

    public static final String COLUMN_ID = "id";

    //Clothing database information
    public static final String COLUMN_CLOTHING_NAME = "clothing_name";
    public static final String COLUMN_CLOTHING_CONDITION = "clothing_condition";
    public static final String COLUMN_CLOTHING_PICTURE = "picture";
    public static final String COLUMN_CLOTHING_TYPE = "type";

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
                COLUMN_CLOTHING_TYPE + " INTEGER, " +
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
    private void addOutfit(Outfit newOutfit){
        ContentValues valuesToAdd = new ContentValues();
        valuesToAdd.put(COLUMN_OUTFIT_NAME, newOutfit.getOutfitName());
        //valuesToAdd.put(COLUMN_OUTFIT_ACCESSORIES, newOutfit.getAccessories());
        valuesToAdd.put(COLUMN_OUTFIT_BOTTOM, newOutfit.getBottom().getClothingName());
        valuesToAdd.put(COLUMN_OUTFIT_HEAD, newOutfit.getHat().getClothingName());
        valuesToAdd.put(COLUMN_OUTFIT_OUTERWEAR, newOutfit.getOuterwear().getClothingName());
        valuesToAdd.put(COLUMN_OUTFIT_SHOES, newOutfit.getShoes().getClothingName());
        valuesToAdd.put(COLUMN_OUTFIT_TOP, newOutfit.getTop().getClothingName());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_OUTFIT, null, valuesToAdd);
        db.close(); //MUST ALWAYS CLOSE

    }

    private Outfit getOutfit(int outfitID){
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT * FROM " +
                TABLE_OUTFIT +
                " WHERE " +
                COLUMN_ID +
                " = " + outfitID;
        //should consider adding a Log
        //    Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor != null)
            cursor.moveToFirst();
        return new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_NAME)));

    }

    private void deleteOutfit(String outfitName){
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

    private void addCloset(Closet newCloset){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOSET_NAME, newCloset.getClosetName());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CLOSET, null, values);
        db.close();
    }

    private Outfit getCloset(int closetID){
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
        return new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_NAME)));

    }

    private void deleteCloset(String closetName){
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
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOTHING_NAME, newClothing.getClothingName());
        values.put(COLUMN_CLOTHING_CONDITION, newClothing.getClothingCondition());
        values.put(COLUMN_CLOTHING_PICTURE, newClothing.getThumbnail());
        values.put(COLUMN_CLOTHING_TYPE, newClothing.getType());

        if(entryExists(newClothing.name, TABLE_CLOTHING)){
            db.update(TABLE_CLOTHING,values, null,null);
        }
        else{
            db.insert(TABLE_CLOTHING, null, values);
        }
        db.close();
    }

    public Clothing getClothing(String clothingName){
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOTHING +
                " WHERE " +
                COLUMN_CLOTHING_NAME +
                " LIKE '%" +clothingName + "%'";
        //should consider adding a Log
        //    Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery,null);
        Clothing searchedClothing = null;
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            searchedClothing = new Clothing(cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_NAME)),R.drawable.taco_socks);
        }
        cursor.close();
        return searchedClothing;

    }

    private void deleteClothing(String clothingName){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " +
                TABLE_CLOTHING +
                " WHERE " +
                COLUMN_CLOTHING_NAME +
                " LIKE '%" +clothingName + "%'";
        db.execSQL(query);
    }

    private boolean entryExists(String entryName, String tableName){
        Entry potentialExistingEntry = null;
        switch(tableName){
            case TABLE_CLOSET:
                //potentialExistingEntry = getCloset(tableName);
                break;
            case TABLE_CLOTHING:
                potentialExistingEntry = getClothing(entryName);
                break;
            case TABLE_OUTFIT:
                //potentialExistingEntry = getOutfit(tableName);
                break;
            default:
                break;

        }
        return potentialExistingEntry != null;
    }
}
