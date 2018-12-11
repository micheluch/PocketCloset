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
import java.util.Set;
import java.util.TreeSet;

public class DBManager extends SQLiteOpenHelper {

    private static int outfitID;
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
    public static final String COLUMN_CLOTHING_PICTURE_PATH = "picture_path";
    public static final String COLUMN_CLOTHING_TYPE = "type";
    public static final String COLUMN_CLOTHING_XCOORD = "xcoord";
    public static final String COLUMN_CLOTHING_YCOORD = "ycoord";
    public static final String COLUMN_CLOTHING_COLOR = "color";

    //Closet database information
    public static final String COLUMN_CLOSET_NAME = "closet_name";
    public static final String COLUMN_CLOSET_LOCATION = "closet_location";
    public static final String COLUMN_CLOSET_ITEM_COUNT = "closet_size";
    public static final String COLUMN_CLOSET_DESCRIPTION = "closet_description";
    public static final String COLUMN_CLOSET_IMAGEPATH = "closet_column_imagepath";

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
                COLUMN_CLOTHING_PICTURE_PATH + " TEXT, " +
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
                COLUMN_OUTFIT_IMAGEPATH + " TEXT " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + REFERENCE_TABLE_OUTFIT + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_REFERENCE_OUTFIT_ID + " INTEGER, " +
                COLUMN_REFERENCE_CLOTHING_ID + " INTEGER " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_CLOSET + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CLOSET_NAME + " TEXT, " +
                COLUMN_CLOSET_DESCRIPTION + " TEXT, " +
                COLUMN_CLOSET_IMAGEPATH + " TEXT " +
                ");";
        db.execSQL(query);

        query = "CREATE TABLE " + REFERENCE_TABLE_CLOSET + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_REFERENCE_CLOSET_ID + " INTEGER, " +
                COLUMN_REFERENCE_ENTRY_TYPE + " INTEGER, " +
                COLUMN_REFERENCE_ENTRY_ID + " INTEGER " +
                ");";
        db.execSQL(query);
        outfitID = 1;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOSET);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTFIT);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHING);
//        db.execSQL("DROP TABLE IF EXISTS " + REFERENCE_TABLE_OUTFIT);
//        db.execSQL("DROP TABLE IF EXISTS " + REFERENCE_TABLE_CLOSET);
//        onCreate(db);
    }


    //We need to hash out how we are constructing classes. In android
    //adding to database can be done with values as done here
    public void addOutfit(Outfit newOutfit) {
        // TODO: rework this method to take a Closet argument and add the outfit to a closet
        ContentValues valuesToAdd = new ContentValues();
        valuesToAdd.put(COLUMN_OUTFIT_NAME, newOutfit.getEntryName());
        valuesToAdd.put(COLUMN_ID, newOutfit.getEntryId());
        valuesToAdd.put(COLUMN_OUTFIT_IMAGEPATH, newOutfit.getPath());
        valuesToAdd.put(COLUMN_OUTFIT_DESCRIPTION, newOutfit.getDescription());
        SQLiteDatabase db = getWritableDatabase();

        addClothesToReference(newOutfit.getClothingList(), newOutfit.getEntryId());

        if (entryExists(newOutfit.getEntryName(), TABLE_OUTFIT)) {
            db.update(TABLE_OUTFIT, valuesToAdd, null, null);
        } else {
            db.insert(TABLE_OUTFIT, null, valuesToAdd);
            outfitID++;
        }
        //db.close(); //MUST ALWAYS Closet
    }

    private void addClothesToReference(List<Clothing> clothingReferenceList, int outfitID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REFERENCE_OUTFIT_ID, outfitID);

        for (Clothing item : clothingReferenceList) {
            values.put(COLUMN_REFERENCE_CLOTHING_ID, item.getEntryId());

            db.insert(REFERENCE_TABLE_OUTFIT, null, values);
        }
        //db.close();
    }

    public int getOutfitID() {
        return outfitID;
    }

    public Outfit getOutfit(String outfitName) {
        SQLiteDatabase db = getWritableDatabase();
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
        if (cursor == null || cursor.getCount() == 0)
            return null;
        Outfit databaseOutfit = new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_NAME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_IMAGEPATH)));
        databaseOutfit.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_DESCRIPTION)));
        databaseOutfit.setImage();
        cursor.close();


        //populate clothing entries.
        List<Clothing> associatedClothing = getClothingForOutfit(databaseOutfit.getEntryId());
        if (associatedClothing != null) {
            for (Clothing currentItem : associatedClothing) {
                databaseOutfit.addClothingToOutfit(currentItem);
            }
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

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0)
            cursor.moveToFirst();
        else
            return null;

        Outfit databaseOutfit = new Outfit(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_NAME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_IMAGEPATH)));

        databaseOutfit.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_OUTFIT_DESCRIPTION)));
        databaseOutfit.setEntryId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        cursor.close();

        //populate clothing entries.
        List<Clothing> associatedClothing = getClothingForOutfit(databaseOutfit.getEntryId());
        if (associatedClothing != null) {
            for (Clothing currentItem : associatedClothing) {
                databaseOutfit.addClothingToOutfit(currentItem);
            }
        }

        return databaseOutfit;
    }

    private List<Clothing> getClothingForOutfit(int outfitReferenceID) {

        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT * FROM " +
                REFERENCE_TABLE_OUTFIT +
                " WHERE " +
                COLUMN_REFERENCE_OUTFIT_ID +
                " = " + outfitReferenceID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            int columnInd = cursor.getColumnIndex(COLUMN_REFERENCE_CLOTHING_ID);
            List<Clothing> clothingInOutfit = new ArrayList<>(cursor.getCount());
            cursor.moveToFirst();
            do {
                Clothing outfitItem = getClothing(cursor.getString(columnInd));
                clothingInOutfit.add(outfitItem);
            } while (cursor.moveToNext());
            return clothingInOutfit;
        }
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
        db.close();
    }

    public void addCloset(Closet newCloset) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOSET_NAME, newCloset.getEntryName());
        values.put(COLUMN_CLOSET_DESCRIPTION, newCloset.getDescription());
        values.put(COLUMN_CLOSET_IMAGEPATH, newCloset.getPath());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CLOSET, null, values);
        //db.close();
    }

    public Closet getCloset(int closetID) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOSET +
                " WHERE " +
                COLUMN_ID +
                " = " + closetID;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        Closet result = new Closet(cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_DESCRIPTION)),
                Entry.pocketClassType.CLOSET_TYPE,
                cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_IMAGEPATH)));
        result.contentList = getEntriesFromCloset(result.getEntryId());
        return result;
    }

    public Closet getCloset(String closetName) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOSET +
                " WHERE " +
                COLUMN_CLOSET_NAME +
                " LIKE '%" + closetName + "%'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor == null || cursor.getCount() == 0)
            return null;
        cursor.moveToFirst();
        Closet databaseCloset = new Closet(cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_NAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_DESCRIPTION)),
                Entry.pocketClassType.CLOSET_TYPE,
                cursor.getString(cursor.getColumnIndex(COLUMN_CLOSET_IMAGEPATH)));
        int closetReferenceID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        databaseCloset.setEntryId(closetReferenceID);
        List<Entry> closetItems = getEntriesForCloset(closetReferenceID);
        if (closetItems != null) {
            for (Entry closetItem : closetItems)
                databaseCloset.addEntryToCloset(closetItem);
        }
        return databaseCloset;
    }

    private List<Entry> getEntriesForCloset(int closetReferenceID) {
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT * FROM " +
                REFERENCE_TABLE_CLOSET +
                " WHERE " +
                COLUMN_REFERENCE_CLOSET_ID +
                " = " + closetReferenceID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            int columnInd = cursor.getColumnIndex(COLUMN_REFERENCE_ENTRY_ID);
            List<Entry> clothingInCloset = new ArrayList<>(cursor.getCount());
            Entry closetItem = null;
            cursor.moveToFirst();
            do {
                int type = cursor.getInt(cursor.getColumnIndex(COLUMN_REFERENCE_ENTRY_TYPE));
                int referenceID = cursor.getInt(columnInd);
                if (type == Entry.pocketClassType.OUTFIT_TYPE.ordinal()) {
                    closetItem = getOutfit(referenceID);
                } else if (type == Entry.pocketClassType.CLOTHING_TYPE.ordinal()) {
                    closetItem = getClothing(referenceID);
                }
                if (closetItem != null) clothingInCloset.add(closetItem);
            } while (cursor.moveToNext());
            return clothingInCloset;
        }
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
            values.put(COLUMN_REFERENCE_ENTRY_TYPE, entry.getEntryType().ordinal());
            values.put(COLUMN_REFERENCE_CLOTHING_ID, entry.getEntryId());

            db.insert(REFERENCE_TABLE_CLOSET, null, values);
        }
    }


    /**
     * getEntriesFromCloset
     * Returns a list of entries with the given type.
     *
     * @param closetID The ID of the Closet to get entries from.
     */
    public List<Entry> getEntriesFromCloset(int closetID) {
        SQLiteDatabase db = getWritableDatabase();
        String selectQuery = "SELECT * FROM " +
                REFERENCE_TABLE_CLOSET +
                " WHERE " +
                COLUMN_REFERENCE_CLOSET_ID +
                " = " + closetID;
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Entry> results = new ArrayList<>();
        Outfit dummyEntry = new Outfit("", 1, ""); //= new Entry("","",0,Entry.pocketClassType.OUTFIT_TYPE);
        int type;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                type = cursor.getInt(cursor.getColumnIndex(COLUMN_REFERENCE_ENTRY_TYPE));
                switch (type) {
                    case 3:
                        results.add(getOutfit(cursor.getInt(cursor.getColumnIndex(COLUMN_REFERENCE_ENTRY_ID))));
                        break;
                    case 2:
                        results.add(getClothing(cursor.getInt(cursor.getColumnIndex(COLUMN_REFERENCE_ENTRY_ID))));
                        break;
                    default:
                        break;
                }
            } while (cursor.moveToNext());

        }

        return results;
    }

    public void addClothing(Clothing newClothing) {
        // TODO: rework this method to take a Closet argument and add the clothing to a closet
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CLOTHING_NAME, newClothing.getClothingName());
        values.put(COLUMN_CLOTHING_CONDITION, newClothing.getCondition().ordinal());
        values.put(COLUMN_CLOTHING_PICTURE_PATH, newClothing.getPath());
        values.put(COLUMN_CLOTHING_TYPE, newClothing.getType());
        values.put(COLUMN_CLOTHING_COLOR, newClothing.getColor().ordinal());
        values.put(COLUMN_CLOTHING_XCOORD, newClothing.getXcoordinate());
        values.put(COLUMN_CLOTHING_YCOORD, newClothing.getYcoordinate());

        if (entryExists(newClothing.name, TABLE_CLOTHING)) {
            db.update(TABLE_CLOTHING, values, null, null);
        } else {
            db.insert(TABLE_CLOTHING, null, values);
        }
        //db.close();
    }

    public Clothing getClothing(String clothingName) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOTHING +
                " WHERE " +
                COLUMN_CLOTHING_NAME +
                " LIKE '%" + clothingName + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        Clothing searchedClothing = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            searchedClothing = new Clothing(cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_PICTURE_PATH)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    Clothing.clothingType.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_TYPE))],
                    Clothing.clothingColor.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_COLOR))],
                    Clothing.clothingCondition.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_CONDITION))],
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_XCOORD)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_YCOORD))
            );
            searchedClothing.setImage();
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
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            searchedClothing = new Clothing(cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_PICTURE_PATH)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    Clothing.clothingType.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_TYPE))],
                    Clothing.clothingColor.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_COLOR))],
                   Clothing.clothingCondition.values()[cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_CONDITION))],
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_XCOORD)),
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CLOTHING_YCOORD))
            );
            cursor.close();
        }
       // db.close();
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
                potentialExistingEntry = getCloset(tableName);
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

    //can remove this final int by restructuring the database to have all second columns be the name
    private List<Entry> searchDatabaseByName(String searchTerm) {
        Set<Entry> searchedItems = new TreeSet<>();
        searchedItems.addAll(getAllClothesByName(searchTerm));
        searchedItems.addAll(getAllOutfitsByName(searchTerm));
        //searchedItems.addAll(getAllClosetsByName(searchTerm));
        List<Entry> returnList = new ArrayList<Entry>();
        returnList.addAll(searchedItems);
        return returnList;
    }

    private List<Entry> getAllClothesByName(String clothingName) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOTHING +
                " WHERE " +
                COLUMN_CLOTHING_NAME +
                " LIKE '%" + clothingName + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Entry> searchedItems = new ArrayList<>();
        if (cursor == null || cursor.getCount() <= 0) {
            return searchedItems;
        } else
            cursor.moveToFirst();
        do {
            String searchName = cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_NAME));
            Clothing searchedClothing = getClothing(searchName);
            if (searchedClothing != null)
                searchedItems.add(searchedClothing);
        } while (cursor.moveToNext());
        return searchedItems;
    }

    private List<Entry> getAllOutfitsByName(String outfitName) {
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_OUTFIT +
                " WHERE " +
                COLUMN_OUTFIT_NAME +
                " LIKE '%" + outfitName + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Entry> searchedItems = new ArrayList<>();
        if (cursor == null || cursor.getCount() <= 0) {
            return searchedItems;
        } else
            cursor.moveToFirst();
        do {
            String searchName = cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_NAME));
            Outfit searchedOutfit = getOutfit(searchName);
            if (searchedOutfit != null)
                searchedItems.add(searchedOutfit);
        } while (cursor.moveToNext());
        return searchedItems;
    }

    //@TODO Make Closet an extension of an entry
    private List<Entry> getAllClosetsByName(String closetName){
        SQLiteDatabase db = getWritableDatabase(); //check formatting on selectquery. Potentially spacing issues
        String selectQuery = "SELECT  * FROM " +
                TABLE_CLOSET +
                " WHERE " +
                COLUMN_CLOSET_NAME +
                " LIKE '%" + closetName + "%'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<Entry> searchedItems = new ArrayList<>();
        if (cursor == null || cursor.getCount() <= 0){
            return searchedItems;
        }
        else
            cursor.moveToFirst();
        do{
            String searchName = cursor.getString(cursor.getColumnIndex(COLUMN_CLOTHING_NAME));
            Closet searchedCloset = getCloset(searchName);
            if(searchedCloset != null)
                searchedItems.add(searchedCloset);
        }while(cursor.moveToNext());
        return searchedItems;
    }

    public List<Clothing> getAllClothes(){
        SQLiteDatabase db = getWritableDatabase();
        List<Clothing> clothingList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_CLOTHING;
        Cursor cursor = db.rawQuery(query, null);
        int numberOfTableElements = cursor.getCount();
        if(numberOfTableElements > 0){
            cursor.moveToFirst();
            do{
                int dummyInt = cursor.getColumnIndex(COLUMN_CLOTHING_NAME);
                String clothingName = cursor.getString(dummyInt);
                clothingList.add(getClothing(clothingName));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return clothingList;
    }

    public List<Outfit> getAllOutfits(){
        SQLiteDatabase db = getWritableDatabase();
        List<Outfit> outfitList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_OUTFIT;
        Cursor cursor = db.rawQuery(query, null);
        int numberOfTableElements = cursor.getCount();
        if(numberOfTableElements > 0){
            cursor.moveToFirst();
            do{
                int dummyInt = cursor.getColumnIndex(COLUMN_OUTFIT_NAME);
                String outfitName = cursor.getString(dummyInt);
                outfitList.add(getOutfit(outfitName));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return outfitList;
    }
}
