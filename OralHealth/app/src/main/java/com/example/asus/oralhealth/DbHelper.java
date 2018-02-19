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
    private static final String DATABASE_NAME = "oralHealth.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "student";
    public static final String STD_ID = "studentID";
    public static final String NAME = "studentName";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCmd ="CREATE TABLE " + TABLE_NAME+ " ("+
                STD_ID + " INTEGER PRIMARY KEY, "+
                NAME + " TEXT NOT NULL );";
        db.execSQL(sqlCmd);

        Log.d(TAG, "Database tables created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

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

    // Getting All Contacts
    public List<String> getDataFromSearch(String id, String name)
    {
        List<String> list=new ArrayList<>();
        //get readable database
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME + " WHERE " + DbHelper.STD_ID + " ='" + id + "'"+", "+ DbHelper.NAME + " ='" + name + "'", null);
        if(c.moveToFirst())
        {
            do {
                list.add(c.getString(0));
                list.add(c.getString(1));
            }while (c.moveToNext());
        }
        //close the cursor
        c.close();
        //close the database
        db.close();
        return list;
    }

}