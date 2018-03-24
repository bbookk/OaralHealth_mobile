package com.example.asus.oralhealth;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = DbHelper.class.getSimpleName();
    private String JSON_STRING;
    private DbHelper helper;
    String json_string;
    JSONObject jsonObj;
    JSONArray jsonArr;
    TextView json;
    RecordActivity recordActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DbHelper(this);
        recordActivity = new RecordActivity();
        new CountDownTimer(2000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                DetectActivity();
            }
        }.start();

        try {
            Cursor cursor = getAllNotes();
            showNotes(cursor);
        } finally { //close connection with DB
            helper.close();
        }

        if (isNetworkAvailable() == true) {
//            Toast.makeText(MainActivity.this, "Connection", Toast.LENGTH_SHORT).show();
            new MainActivity.BackgroundTask().execute();
        } else {
//            Toast.makeText(MainActivity.this, "Connection failed.", Toast.LENGTH_SHORT).show();
        }


    }

    private void DetectActivity() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class BackgroundTask extends AsyncTask<Void, Void, String> {
        String JSON_URL;

        @Override
        protected void onPreExecute() {
            JSON_URL = "http://192.168.1.3/OralHealth_project/getData.php";
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
            if (json_string == null) {
                Toast.makeText(MainActivity.this, "Get Json Before.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    jsonObj = new JSONObject(json_string);
                    jsonArr = jsonObj.getJSONArray("result");
                    int count = 0;
                    String[] id = new String[jsonArr.length()];
                    String[] studentName = new String[jsonArr.length()];

                    while (count < jsonArr.length()) {
                        JSONObject jo = jsonArr.getJSONObject(count);
                        id[count] = jo.getString("ID");
                        studentName[count] = jo.getString("studentName");

                        try {
                            helper.addName(id[count], studentName[count]);
                            Cursor cursor = getAllNotes();
                            showNotes(cursor);
                        } finally {
                            helper.close();
                        }
                        count++;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
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
}
