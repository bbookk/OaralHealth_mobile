package com.example.asus.oralhealth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class testActivity extends AppCompatActivity {
    private static final String TAG = DbHelper.class.getSimpleName();
    private String JSON_STRING;
    private DbHelper helper;
    String json_string;
    JSONObject jsonObj;
    JSONArray jsonArr;
    TextView json;
    List<String> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        helper = new DbHelper(this);
        //read all data form DB to show
        try {
            Cursor cursor = getAllNotes();
            showNotes(cursor);
        } finally { //close connection with DB
            helper.close();
        }

        if(isNetworkAvailable() == true){
            Toast.makeText(testActivity.this, "Connection", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(testActivity.this, "Connection failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private class BackgroundTask extends AsyncTask<Void, Void, String> {
        String JSON_URL;

        @Override
        protected void onPreExecute() {
            JSON_URL = "http://192.168.1.2/OralHealth_project/getData.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                StringBuilder JSON_DATA = new StringBuilder();
                URL url = new URL(JSON_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((JSON_STRING = reader.readLine()) != null) {
                    JSON_DATA.append(JSON_STRING).append("\n");
                }
                return JSON_DATA.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json = (TextView) findViewById(R.id.txtView);
            json.setText(result);
            json_string = result;
        }
    }

    public void getJson(View view) {
        new BackgroundTask().execute();
    }

    public void parseJson(View view) {

        if (json_string == null) {
            Toast.makeText(testActivity.this, "Get Json Before.", Toast.LENGTH_SHORT).show();
        } else {
            try {
                jsonObj = new JSONObject(json_string);
                jsonArr = jsonObj.getJSONArray("result");
                int count = 0;
                int[] id = new int[jsonArr.length()];
                String[] studentName = new String[jsonArr.length()];

                while (count < jsonArr.length()) {
                    JSONObject jo = jsonArr.getJSONObject(count);
                    id[count] = jo.getInt("ID");
                    studentName[count] = jo.getString("studentName");
                    try {
                        addNote(id[count], studentName[count]);
                        Cursor cursor = getAllNotes();
                        showNotes(cursor);
                    } finally {
                        helper.close();
                    }
//                    LinearLayout linearLayout = new LinearLayout(this);
//                    setContentView(linearLayout);
//                    linearLayout.setOrientation(LinearLayout.VERTICAL);
////                    for (int i = 0; i < jsonArr.length(); i++) {
//                        TextView textView = new TextView(this);
//                        textView.setText(id[count]);
//                        linearLayout.addView(textView);
////                    }
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void addNote(int id, String str) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.STD_ID, id);
        values.put(DbHelper.NAME, str);
        db.insertOrThrow(DbHelper.TABLE_NAME, null, values);
    }

    private static String[] COLUMNS = {DbHelper.STD_ID, DbHelper.NAME};
    private static String ORDER_BY = DbHelper.STD_ID + " DESC";

    private Cursor getAllNotes() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_NAME, COLUMNS, null, null, null, null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;
    }

    private void showNotes(Cursor cursor) {
        StringBuilder builder = new StringBuilder("ข้อความที่บันทึกไว้:\n\n");

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0); //read column 0 _ID
            String content = cursor.getString(1); // Read Colum 2 CONTENT

            builder.append("ลำดับ ").append(id).append(": ");
            builder.append("\t").append(content).append("\n");
        }

        TextView tv = (TextView) findViewById(R.id.txtView);
        tv.setText(builder);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}