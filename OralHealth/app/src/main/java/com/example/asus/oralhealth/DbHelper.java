package com.example.asus.oralhealth;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "oralHealth_project.db";
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
                TEETH_11 + " TEXT NOT NULL, "+
                TEETH_12 + " TEXT NOT NULL, "+
                TEETH_13 + " TEXT NOT NULL, "+
                TEETH_14 + " TEXT NOT NULL, "+
                TEETH_15 + " TEXT NOT NULL, "+
                TEETH_16 + " TEXT NOT NULL, "+
                TEETH_17 + " TEXT NOT NULL, "+
                TEETH_18 + " TEXT "+
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


}