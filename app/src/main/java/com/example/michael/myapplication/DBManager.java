package com.example.michael.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "pocketcloset.db";
    public static final String TABLE_OUTFIT = "outfits";
    public static final String TABLE_CLOSET = "closets";
    public static final String TABLE_CLOTHING = "clothing";
    public static final String REFERENCE_TABLE_OUTFIT = "reference_outfit";
    public static final String REFERENCE_TABLE_CLOSET = "reference_closet";

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
    public static final String COLUMN_REFERENCE_CLOTHING_ID = "clothing_id"; //id for clothing table

    //Closet reference table
    public static final String COLUMN_REFERENCE_CLOSET_ID = "closet_id";
    public static final String COLUMN_REFERENCE_ENTRY_TYPE = "entry_type";
    public static final String COLUMN_REFERENCE_ENTRY_ID = "entry_id";

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
                COLUMN_CLOTHING_XCOORD + " INTEGER, " +
                COLUMN_CLOTHING_YCOORD + " INTEGER, " +
                COLUMN_CLOTHING_COLOR + " INTEGER, " +
                COLUMN_CLOTHING_CONDITION + " TEXT " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_OUTFIT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OUTFIT_NAME + " TEXT, " +
                COLUMN_OUTFIT_DESCRIPTION + " TEXT," +
                COLUMN_OUTFIT_IMAGEPATH + " INTEGER " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + REFERENCE_TABLE_OUTFIT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_REFERENCE_OUTFIT_ID + " TEXT, " +
                COLUMN_REFERENCE_CLOTHING_ID + " INTEGER " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_CLOSET + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLOSET_NAME + " TEXT, " +
                COLUMN_CLOSET_LOCATION + " TEXT, " +
                COLUMN_CLOSET_ITEM_COUNT + " INTEGER " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + REFERENCE_TABLE_CLOSET + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_REFERENCE_CLOSET_ID + " INTEGER, " +
                COLUMN_REFERENCE_ENTRY_TYPE + " INTEGER, " +
                COLUMN_REFERENCE_ENTRY_ID + " INTEGER " +
                ");";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOSET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTFIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHING);
        db.execSQL("DROP TABLE IF EXISTS " + REFERENCE_TABLE_OUTFIT);
        onCreate(db);
    }

    //TODO DEFINE HOW TO ADD TO DATABASE
    //We need to hash out how we are constructing classes. In android
    //adding to database can be done with values as done here
    private void addOutfit(Outfit newOutfit) {
        // TODO: rework this method to take a Closet argument and add the outfit to a closet
        ContentValues valuesToAdd = new ContentValues();
        valuesToAdd.put(COLUMN_OUTFIT_NAME, newOutfit.getEntryName());
        valuesToAdd.put(COLUMN_ID, newOutfit.getEntryId());
        valuesToAdd.put(COLUMN_OUTFIT_IMAGEPATH, newOutfit.getThumbnail());
        valuesToAdd.put(COLUMN_OUTFIT_DESCRIPTION, newOutfit.getDescription());
        SQLiteDatabase db = getWritableDatabase();

        addClothesToReference(newOutfit.getClothingList(), newOutfit.getEntryName());

        if (entryExists(newOutfit.getEntryName(), TABLE_OUTFIT)) {
            db.update(TABLE_OUTFIT, valuesToAdd, null, null);
        } else {
            db.insert(TABLE_OUTFIT, null, valuesToAdd);
        }

        db.close(); //MUST ALWAYS CLOSE
    }

    private void addClothesToReference(List<Clothing> clothingReferenceList, String outfitID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REFERENCE_OUTFIT_ID, outfitID);

        for (Clothing item : clothingReferenceList) {
            values.put(COLUMN_REFERENCE_CLOTHING_ID, item.getEntryId());

            db.insert(REFERENCE_TABLE_OUTFIT, null, values);
        }
    }

    private Outfit getOutfit(String outfitName) {
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

        Outfit databaseOutfit = new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_NAME)));
        databaseOutfit.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_DESCRIPTION)));
        databaseOutfit.setEntryId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        databaseOutfit.setThumbnail(-999999);
        cursor.close();

        //populate clothing entries.
        selectQuery = "SELECT * FROM " +
                REFERENCE_TABLE_OUTFIT +
                " WHERE " +
                COLUMN_REFERENCE_OUTFIT_ID +
                " LIKE '%" + outfitName + "%'";
        cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Clothing outfitItem = getClothing(cursor.getString(cursor.getColumnIndex(COLUMN_REFERENCE_CLOTHING_ID)));
            databaseOutfit.addClothingToOutfit(outfitItem);
        }


        return databaseOutfit;

    }

    private Outfit getOutfit(int outfitID) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT * FROM " +
                TABLE_OUTFIT +
                " WHERE " +
                COLUMN_ID +
                " = " + outfitID;
        //should consider adding a Log
        //    Log.e(LOG, selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();

        Outfit databaseOutfit = new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_NAME)));
        databaseOutfit.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_DESCRIPTION)));
        databaseOutfit.setEntryId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        databaseOutfit.setThumbnail(-999999);
        cursor.close();

        //populate clothing entries.
        selectQuery = "SELECT * FROM " +
                REFERENCE_TABLE_OUTFIT +
                " WHERE " +
                COLUMN_REFERENCE_OUTFIT_ID +
                " = " + outfitID;
        cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Clothing outfitItem = getClothing(cursor.getString(cursor.getColumnIndex(COLUMN_REFERENCE_CLOTHING_ID)));
            databaseOutfit.addClothingToOutfit(outfitItem);
        }


        return databaseOutfit;

    }

    private void deleteOutfit(String outfitName) {
        // TODO: rework this method to take a Closet argument and remove the outfit from its closet
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

    private void addCloset(Closet newCloset) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOSET_NAME, newCloset.getClosetName());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CLOSET, null, values);
        db.close();
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
        return new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_NAME)));

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
    }

    /**
     * addEntryToCloset
     * Adds a single entry object to the proper closet reference table.
     */
    public void addEntryToCloset(Entry entry, int closetID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REFERENCE_CLOSET_ID, closetID);
        values.put(COLUMN_REFERENCE_ENTRY_TYPE, entry.type.ordinal());
        values.put(COLUMN_REFERENCE_CLOTHING_ID, entry.getEntryId());

        db.insert(REFERENCE_TABLE_CLOSET, null, values);

    }

    /**
     * addEntriesToCloset
     * Adds a list of entries to the relevant closet.
     */
    public void addEntriesToCloset(List<Entry> entryReferenceList, int closetID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REFERENCE_CLOSET_ID, closetID);

        for (Entry entry : entryReferenceList) {
            values.put(COLUMN_REFERENCE_ENTRY_TYPE, entry.type.ordinal());
            values.put(COLUMN_REFERENCE_CLOTHING_ID, entry.getEntryId());

            db.insert(REFERENCE_TABLE_CLOSET, null, values);
        }
    }

    /**
     * getEntriesFromCloset
     * Returns a list of entries with the given type.
     *
     * @param closetID The ID of the Closet to get entries from.
     * @param type     The Entry type of the items wanted.
     */
    public List<Entry> getEntriesFromCloset(int closetID, Entry.entryType type) {
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT * FROM " +
                REFERENCE_TABLE_CLOSET +
                " WHERE " +
                COLUMN_REFERENCE_CLOSET_ID +
                " = " + closetID +
                " AND " +
                COLUMN_REFERENCE_ENTRY_TYPE +
                " = " + type.ordinal();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Entry> results = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            switch(type) {
                case outfitType:
                    results.add(getOutfit(cursor.getInt(cursor.getColumnIndex(COLUMN_REFERENCE_ENTRY_ID))));
                    break;
                case clothingType:
                    results.add(getClothing(cursor.getInt(cursor.getColumnIndex(COLUMN_REFERENCE_ENTRY_ID))));
            }
        }


    }

    public void addClothing(Clothing newClothing) {
        // TODO: rework this method to take a Closet argument and add the clothing to a closet
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOTHING_NAME, newClothing.getClothingName());
        values.put(COLUMN_CLOTHING_CONDITION, newClothing.getCondition().ordinal());
        values.put(COLUMN_CLOTHING_PICTURE, newClothing.getThumbnail());
        values.put(COLUMN_CLOTHING_TYPE, newClothing.getType());

        if (entryExists(newClothing.name, TABLE_CLOTHING)) {
            db.update(TABLE_CLOTHING, values, null, null);
        } else {
            db.insert(TABLE_CLOTHING, null, values);
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
        if (cursor.getCount() > 0) {
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
        }
        cursor.close();
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
        if (cursor.getCount() > 0) {
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
        }
        cursor.close();
        return searchedClothing;
    }

    private void deleteClothing(String clothingName) {
        // TODO: rework this method to take a Closet argument and remove the clothing from its closet
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
                //potentialExistingEntry = getOutfit(tableName);
                break;
            default:
                break;

        }
        return potentialExistingEntry != null;
    }
}
