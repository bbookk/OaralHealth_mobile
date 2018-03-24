package com.example.asus.oralhealth;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "oralHealth_mobile.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "student";
    public static final String STD_ID = "studentID";
    public static final String NAME = "studentName";
    public static final String TABLE_NAME_RESULT = "result";
    public static final String TEETH_11 = "teeth_11";
    public static final String TEETH_12 = "teeth_12";
    public static final String TEETH_13 = "teeth_13";
    public static final String TEETH_14 = "teeth_14";
    public static final String TEETH_15 = "teeth_15";
    public static final String TEETH_16 = "teeth_16";
    public static final String TEETH_17 = "teeth_17";
    public static final String TEETH_18 = "teeth_18";
    public static final String TEETH_21 = "teeth_21";
    public static final String TEETH_22 = "teeth_22";
    public static final String TEETH_23 = "teeth_23";
    public static final String TEETH_24 = "teeth_24";
    public static final String TEETH_25 = "teeth_25";
    public static final String TEETH_26 = "teeth_26";
    public static final String TEETH_27 = "teeth_27";
    public static final String TEETH_28 = "teeth_28";
    public static final String TEETH_31 = "teeth_31";
    public static final String TEETH_32 = "teeth_32";
    public static final String TEETH_33 = "teeth_33";
    public static final String TEETH_34 = "teeth_34";
    public static final String TEETH_35 = "teeth_35";
    public static final String TEETH_36 = "teeth_36";
    public static final String TEETH_37 = "teeth_37";
    public static final String TEETH_38 = "teeth_38";
    public static final String TEETH_41 = "teeth_41";
    public static final String TEETH_42 = "teeth_42";
    public static final String TEETH_43 = "teeth_43";
    public static final String TEETH_44 = "teeth_44";
    public static final String TEETH_45 = "teeth_45";
    public static final String TEETH_46 = "teeth_46";
    public static final String TEETH_47 = "teeth_47";
    public static final String TEETH_48 = "teeth_48";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCmd ="CREATE TABLE " + TABLE_NAME+ " ("+
                STD_ID + " INTEGER PRIMARY KEY, "+
                NAME + " TEXT NOT NULL );";
        db.execSQL(sqlCmd);

        String resultTable ="CREATE TABLE " + TABLE_NAME_RESULT+ " ("+
                STD_ID + " INTEGER PRIMARY KEY, "+
                NAME + " TEXT NOT NULL, " +
                TEETH_11 + " TEXT , "+
                TEETH_12 + " TEXT , "+
                TEETH_13 + " TEXT , "+
                TEETH_14 + " TEXT , "+
                TEETH_15 + " TEXT , "+
                TEETH_16 + " TEXT , "+
                TEETH_17 + " TEXT , "+
                TEETH_18 + " TEXT , "+
                TEETH_21 + " TEXT , "+
                TEETH_22 + " TEXT , "+
                TEETH_23 + " TEXT , "+
                TEETH_24 + " TEXT , "+
                TEETH_25 + " TEXT , "+
                TEETH_26 + " TEXT , "+
                TEETH_27 + " TEXT , "+
                TEETH_28 + " TEXT , "+
                TEETH_31 + " TEXT , "+
                TEETH_32 + " TEXT , "+
                TEETH_33 + " TEXT , "+
                TEETH_34 + " TEXT , "+
                TEETH_35 + " TEXT , "+
                TEETH_36 + " TEXT , "+
                TEETH_37 + " TEXT , "+
                TEETH_38 + " TEXT , "+
                TEETH_41 + " TEXT , "+
                TEETH_42 + " TEXT , "+
                TEETH_43 + " TEXT , "+
                TEETH_44 + " TEXT , "+
                TEETH_45 + " TEXT , "+
                TEETH_46 + " TEXT , "+
                TEETH_47 + " TEXT , "+
                TEETH_48 + " TEXT "+
                ");";
        db.execSQL(resultTable);


        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RESULT);
        // Create tables again
        onCreate(db);
    }

    public List<String> getAllUsers()
    {
        List<String> userlist=new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT "+STD_ID+", "+NAME+" FROM "+TABLE_NAME,null);
        if(cursor.moveToFirst())
        {
            do {
                userlist.add(cursor.getString(0));
                userlist.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        //close the cursor
        cursor.close();
        //close the database
        db.close();
        return userlist;
    }

    public void addName(String id, String str) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STD_ID, id);
        values.put(NAME, str);
        this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + STD_ID + " ='" + id + "'", null);
        if (c.moveToFirst()) {
//            Toast.makeText(MainActivity.this, "Error record exist.", Toast.LENGTH_SHORT).show();
            db.update(TABLE_NAME, values,  STD_ID  + " = " + id, null);
            db.close();
        } else {
            // Inserting record
            db.insertOrThrow(TABLE_NAME, null, values);
            db.close();
        }

    }

    public void addResult(String id, String name,
                          String status1, String status2, String status3, String status4,
                          String status5, String status6, String status7, String status8,
                          String status9, String status10, String status11, String status12,
                          String status13, String status14, String status15, String status16,
                          String status17, String status18, String status19, String status20,
                          String status21, String status22, String status23, String status24,
                          String status25, String status26, String status27, String status28,
                          String status29, String status30, String status31, String status32) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STD_ID, id);
        values.put(NAME, name);
        values.put(TEETH_11, status1);
        values.put(TEETH_12, status2);
        values.put(TEETH_13, status3);
        values.put(TEETH_14, status4);
        values.put(TEETH_15, status5);
        values.put(TEETH_16, status6);
        values.put(TEETH_17, status7);
        values.put(TEETH_18, status8);
        values.put(TEETH_21, status9);
        values.put(TEETH_22, status10);
        values.put(TEETH_23, status11);
        values.put(TEETH_24, status12);
        values.put(TEETH_25, status13);
        values.put(TEETH_26, status14);
        values.put(TEETH_27, status15);
        values.put(TEETH_28, status16);
        values.put(TEETH_31, status17);
        values.put(TEETH_32, status18);
        values.put(TEETH_33, status19);
        values.put(TEETH_34, status20);
        values.put(TEETH_35, status21);
        values.put(TEETH_36, status22);
        values.put(TEETH_37, status23);
        values.put(TEETH_38, status24);
        values.put(TEETH_41, status25);
        values.put(TEETH_42, status26);
        values.put(TEETH_43, status27);
        values.put(TEETH_44, status28);
        values.put(TEETH_45, status29);
        values.put(TEETH_46, status30);
        values.put(TEETH_47, status31);
        values.put(TEETH_48, status32);

        this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME_RESULT + " WHERE " + STD_ID + " ='" + id + "'", null);
        if (c.moveToFirst()) {
//            Toast.makeText(MainActivity.this, "Error record exist.", Toast.LENGTH_SHORT).show();
            db.update(TABLE_NAME_RESULT, values, STD_ID + " = " + id, null);
            db.close();
        } else {
            // Inserting record
            db.insertOrThrow(TABLE_NAME_RESULT, null, values);
            db.close();
        }
    }


    /**
     * Compose JSON out of SQLite records
     * @return
     */
    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM "+TABLE_NAME_RESULT;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }


}