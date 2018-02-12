package com.example.asus.oralhealth;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class testActivity extends AppCompatActivity {
    private static final String TAG = DbHelper.class.getSimpleName();
    private String JSON_STRING;
    private DbHelper db;
    String json_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        TextView txtView2 = (TextView) findViewById(R.id.txtView2);

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
            TextView json = (TextView) findViewById(R.id.txtView);
            json.setText(result);
            json_string = result;
        }
    }

    public void getJson(View view) {
        new BackgroundTask().execute();
    }

    public void parseJson(View view){

        if (json_string == null){
            Toast.makeText(testActivity.this, "Get Json Before.",Toast.LENGTH_SHORT).show();
        }else{
            Intent i = new Intent(testActivity.this , ListViewActivity.class);
            i.putExtra("json_data",json_string);
            startActivity(i);
        }
    }
}