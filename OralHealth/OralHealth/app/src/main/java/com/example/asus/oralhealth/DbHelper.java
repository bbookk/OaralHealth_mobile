package com.example.asus.oralhealth;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();


    private Resources mResources;
    private static final String DATABASE_NAME = "oralhealth.db";
    private static final int DATABASE_VERSION = 2;
    //Constants for Database name, table name, and column names
    public static final String TABLE_NAME = "student";
    public static final String COLUMN_ID = "studentID";
    public static final String COLUMN_NAME = "schoolName";


    Context context;
    SQLiteDatabase db;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();

        db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_BUGS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT NOT NULL " + " );";

        db.execSQL(SQL_CREATE_BUGS_TABLE);
        Log.d(TAG, "Database Created Successfully" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertData(String id,String name)

    {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues values=new ContentValues();

        values.put("ID",id);

        values.put("schoolName",name);


        sqLiteDatabase.insert(TABLE_NAME,null,values);

    }

    public ArrayList fetchData()

    {

        ArrayList<String> stringArrayList=new ArrayList<String>();

        String fetchdata="SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);

        if(cursor.moveToFirst()){

            do

            {

                stringArrayList.add(cursor.getString(0));

                stringArrayList.add(cursor.getString(1));

                stringArrayList.add(cursor.getString(2));

            } while (cursor.moveToNext());

        }

        return stringArrayList;

    }

}