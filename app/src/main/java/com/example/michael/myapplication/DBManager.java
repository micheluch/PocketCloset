package com.example.michael.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private int outfitIDVal;
    private int clothingIDVal;
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pocketcloset.db";
    public static final String TABLE_OUTFIT = "outfits";
    public static final String TABLE_CLOSET = "closets";
    public static final String TABLE_CLOTHING = "clothing";
    public static final String REFERENCE_TABLE_OUTFIT = "reference_outfit";

    public static final String COLUMN_ID = "id";

    //Clothing database information
    public static final String COLUMN_CLOTHING_NAME = "clothing_name";
    public static final String COLUMN_CLOTHING_CONDITION = "clothing_condition";
    public static final String COLUMN_CLOTHING_PICTURE = "picture";
    public static final String COLUMN_CLOTHING_TYPE = "type";
    public static final String COLUMN_CLOTHING_XCOORD = "xcoord";
    public static final String COLUMN_CLOTHING_YCOORD = "ycoord";
    public static final String COLUMN_CLOTHING_COLOR = "color";

    //Closet database information
    public static final String COLUMN_CLOSET_NAME = "closet_name";
    public static final String COLUMN_CLOSET_LOCATION = "closet_location";
    public static final String COLUMN_CLOSET_ITEM_COUNT = "closet_size";
    public static final String COLUMN_THUMBNAIL = "thumbnail";

    //Outfit database information
    public static final String COLUMN_OUTFIT_NAME = "outfit_name";
    public static final String COLUMN_OUTFIT_DESCRIPTION = "outfit_description";
    public static final String COLUMN_OUTFIT_IMAGEPATH = "outfit_image";

    //Outfit reference table
    public static final String COLUMN_REFERENCE_OUTFIT_ID = "outfit_id";
    public static final String COLUMN_REFERENCE_CLOTHING_X = "x_cord"; //x coordinate of item
    public static final String COLUMN_REFERENCE_CLOTHING_Y = "y_cord"; //y coordinate of item
    public static final String COLUMN_REFERNCE_CLOTHING_ID = "clothing_id"; //id for clothing table


    public DBManager(@Nullable Context context, @Nullable String name,
                     @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CLOTHING + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY , " +
                COLUMN_CLOTHING_TYPE + " INTEGER, " +
                COLUMN_CLOTHING_NAME + " TEXT, " +
                COLUMN_CLOTHING_PICTURE + " INTEGER, " +
                COLUMN_CLOTHING_XCOORD + " INTEGER, " +
                COLUMN_CLOTHING_YCOORD + " INTEGER, " +
                COLUMN_CLOTHING_COLOR + " INTEGER, " +
                COLUMN_CLOTHING_CONDITION + " TEXT " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_OUTFIT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY , " +
                COLUMN_OUTFIT_NAME + " TEXT, " +
                COLUMN_OUTFIT_DESCRIPTION + " TEXT," +
                COLUMN_OUTFIT_IMAGEPATH + " TEXT " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + REFERENCE_TABLE_OUTFIT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_REFERENCE_OUTFIT_ID + " INTEGER, " +
                COLUMN_REFERNCE_CLOTHING_ID + " INTEGER " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_CLOSET + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLOSET_NAME + " TEXT, " +
                COLUMN_CLOSET_LOCATION + " TEXT, " +
                COLUMN_CLOSET_ITEM_COUNT + " INTEGER " +
                ");";
        db.execSQL(query);

        outfitIDVal = 1;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOSET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTFIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHING);
        db.execSQL("DROP TABLE IF EXISTS " + REFERENCE_TABLE_OUTFIT);
        onCreate(db);
    }


    //We need to hash out how we are constructing classes. In android
    //adding to database can be done with values as done here
    public void addOutfit(Outfit newOutfit) {
        ContentValues valuesToAdd = new ContentValues();
        valuesToAdd.put(COLUMN_OUTFIT_NAME, newOutfit.getEntryName());

        valuesToAdd.put(COLUMN_OUTFIT_IMAGEPATH, newOutfit.getPath());
        valuesToAdd.put(COLUMN_OUTFIT_DESCRIPTION, newOutfit.getDescription());
        SQLiteDatabase db = getWritableDatabase();

        addClothesToReference(newOutfit.getClothingList(), newOutfit.getEntryId());

        if (entryExists(newOutfit.getEntryName(), TABLE_OUTFIT)) {
            valuesToAdd.put(COLUMN_ID, newOutfit.getEntryId());
            db.update(TABLE_OUTFIT, valuesToAdd, null, null);
        } else {
            valuesToAdd.put(COLUMN_ID, outfitIDVal);
            db.insert(TABLE_OUTFIT, null, valuesToAdd);
            outfitIDVal++;
        }
        //db.close(); //MUST ALWAYS CLOSE
        Outfit testOutfit = getOutfit(newOutfit.getEntryName());
    }

    private void addClothesToReference(List<Clothing> clothingReferenceList, int outfitID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REFERENCE_OUTFIT_ID,outfitID);

        for(Clothing item: clothingReferenceList){
            values.put(COLUMN_REFERNCE_CLOTHING_ID, item.getEntryId());

            db.insert(REFERENCE_TABLE_OUTFIT, null, values);
        }
        //db.close();
    }

    public int getOutfitID() {
        return outfitIDVal;
    }

    public int getClothingIDVal() {
        return clothingIDVal;
    }

    public Outfit getOutfit(String outfitName) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT * FROM " +
                TABLE_OUTFIT +
                " WHERE " +
                COLUMN_OUTFIT_NAME +
                " LIKE '%" + outfitName + "%'";
        //should consider adding a Log
        //    Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        Outfit databaseOutfit = new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_NAME)),cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        databaseOutfit.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_DESCRIPTION)));
        databaseOutfit.setPath(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_IMAGEPATH)));
        databaseOutfit.setOutfitImage(databaseOutfit.retrieveImageFromFolder());
        cursor.close();


        //populate clothing entries.
        selectQuery = "SELECT * FROM " +
                REFERENCE_TABLE_OUTFIT +
                " WHERE " +
                COLUMN_REFERENCE_OUTFIT_ID +
                " = " + databaseOutfit.getEntryId();
        cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Clothing outfitItem = getClothing(cursor.getInt(cursor.getColumnIndex(COLUMN_REFERNCE_CLOTHING_ID)));
                databaseOutfit.addClothingToOutfit(outfitItem);
            }while(cursor.moveToNext());
            cursor.close();
        }

        //db.close();


        return databaseOutfit;

    }

    private void deleteOutfit(String outfitName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " +
                TABLE_OUTFIT +
                " WHERE " +
                COLUMN_OUTFIT_NAME +
                "=\"" +
                outfitName +
                "\";";
        db.execSQL(query);
        db.close();
    }

    private void addCloset(Closet newCloset) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOSET_NAME, newCloset.getClosetName());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CLOSET, null, values);
        //db.close();
    }

    private Outfit getCloset(int closetID) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOSET +
                " WHERE " +
                COLUMN_ID +
                " = " + closetID;
        //should consider adding a Log
        //    Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        return new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_NAME)),-1);

    }

    private void deleteCloset(String closetName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " +
                TABLE_CLOSET +
                " WHERE " +
                COLUMN_CLOSET_NAME +
                "=\"" +
                closetName +
                "\";";
        db.execSQL(query);
        //db.close();
    }

    public void addClothing(Clothing newClothing) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_CLOTHING_NAME, newClothing.getClothingName());
        values.put(COLUMN_CLOTHING_CONDITION, newClothing.getCondition().ordinal());
        values.put(COLUMN_CLOTHING_PICTURE, newClothing.getThumbnail());
        values.put(COLUMN_CLOTHING_TYPE, newClothing.getType());
        values.put(COLUMN_CLOTHING_COLOR, newClothing.getColor().ordinal());
        values.put(COLUMN_CLOTHING_XCOORD,newClothing.getXcoordinate());
        values.put(COLUMN_CLOTHING_YCOORD, newClothing.getYcoordinate());

        if (entryExists(newClothing.name, TABLE_CLOTHING)) {
            values.put(COLUMN_ID, newClothing.getEntryId());
            db.update(TABLE_CLOTHING, values, null, null);
        } else {
            values.put(COLUMN_ID, clothingIDVal);
            db.insert(TABLE_CLOTHING, null, values);
            clothingIDVal++;
        }
        db.close();
    }

    public Clothing getClothing(String clothingName) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOTHING +
                " WHERE " +
                COLUMN_CLOTHING_NAME +
                " LIKE '%" + clothingName + "%'";
        //should consider adding a Log
        //    Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        Clothing searchedClothing = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            searchedClothing = new Clothing(cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_NAME)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_PICTURE)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    Clothing.clothingType.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_TYPE))],
                    Clothing.clothingColor.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_COLOR))],
                    Clothing.clothingCondition.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_CONDITION))],
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_XCOORD)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_YCOORD))
            );
            cursor.close();
        }

        //db.close();
        return searchedClothing;
    }

    public Clothing getClothing(int clothingID) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOTHING +
                " WHERE " +
                COLUMN_ID +
                " = " + clothingID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Clothing searchedClothing = null;
        int testVal = cursor.getCount();
        cursor.moveToFirst();
        if (cursor != null && testVal > 0) {
            searchedClothing = new Clothing(cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_NAME)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_PICTURE)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        Clothing.clothingType.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_TYPE))],
                        Clothing.clothingColor.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_COLOR))],
                        Clothing.clothingCondition.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_CONDITION))],
                        cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_XCOORD)),
                        cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_YCOORD))
                            );
            cursor.close();
        }
        db.close();
        return searchedClothing;
    }

    private void deleteClothing(String clothingName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " +
                TABLE_CLOTHING +
                " WHERE " +
                COLUMN_CLOTHING_NAME +
                " LIKE '%" + clothingName + "%'";
        db.execSQL(query);
    }

    private boolean entryExists(String entryName, String tableName) {
        Entry potentialExistingEntry = null;
        switch (tableName) {
            case TABLE_CLOSET:
                //potentialExistingEntry = getCloset(tableName);
                break;
            case TABLE_CLOTHING:
                potentialExistingEntry = getClothing(entryName);
                break;
            case TABLE_OUTFIT:
                potentialExistingEntry = getOutfit(tableName);
                break;
            default:
                break;

        }
        return potentialExistingEntry != null;
    }
}
