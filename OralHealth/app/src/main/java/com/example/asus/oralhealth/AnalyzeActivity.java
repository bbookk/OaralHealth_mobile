package com.example.asus.oralhealth;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.extras.Base64;

public class AnalyzeActivity extends AppCompatActivity {

    DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
    Calendar myCalendar = Calendar.getInstance();
    String dateValue, roomValue, schoolValue;
    JSONObject jsonObj;
    private ArrayList<String> classroomList, schoolList;
    TextView json;
    JSONArray jsonArr;
    private DbHelper helper;
    String json_string;
    Spinner room, school;
    private String JSON_STRING;
    public String dent_name;
    String[] studentId, schoolName, date, dentName, dmft, classroom, studentName, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        helper = new DbHelper(this);
        dent_name = getIntent().getStringExtra("dentist_name");
        room = (Spinner) findViewById(R.id.room);
        school = (Spinner) findViewById(R.id.school);
        classroomList = new ArrayList<String>();
        schoolList = new ArrayList<String>();
//        Toast.makeText(AnalyzeActivity.this, dent_name, Toast.LENGTH_SHORT).show();

        if (isNetworkAvailable() == true) {
//            Toast.makeText(AnalyzeActivity.this, "Connection", Toast.LENGTH_SHORT).show();
            new AnalyzeActivity.BackgroundTask().execute();
        } else {
//            Toast.makeText(AnalyzeActivity.this, "Connection failed.", Toast.LENGTH_SHORT).show();
        }

        try {
            Cursor cursor = getAllNotes();
            addToSpinner(cursor);
//            showNotes(cursor);
        } finally {
            helper.close();
        }

        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnalyzeActivity.this, Analyze2Activity.class);
                startActivity(i);
            }
        });

        TextView home = (TextView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnalyzeActivity.this, DetectActivity.class);
                i.putExtra("den_username", dent_name);
                startActivity(i);
            }
        });
        Button analyze = (Button) findViewById(R.id.analyzeBtn);
        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AnalyzeActivity.this, Analyze2Activity.class);

                TextView date = (TextView)findViewById(R.id.showdate);
                Spinner room = (Spinner) findViewById(R.id.room);
                Spinner school = (Spinner) findViewById(R.id.school);
                dateValue = date.getText().toString();
                roomValue = room.getSelectedItem().toString();
                schoolValue = school.getSelectedItem().toString();
                i.putExtra("date", dateValue);
                i.putExtra("room", roomValue);
                i.putExtra("school", schoolValue);
                i.putExtra("den_username", dent_name);
                startActivity(i);
            }
        });

        TextView date = (TextView) findViewById(R.id.btnChangeDate);
        date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new DatePickerDialog(AnalyzeActivity.this, d,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        updateLabel();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        Locale locale = new Locale("th", "TH");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", locale);
        TextView showDate = (TextView) findViewById(R.id.showdate);
        String date = df.format(myCalendar.getTime());
        showDate.setText(date);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private static String[] COLUMNS = {DbHelper.RECORD_DATE, DbHelper.SCHOOL_NAME, DbHelper.CLASSROOM, DbHelper.STD_ID,
            DbHelper.DENTIST_NAME, DbHelper.DMFT, DbHelper.NAME, DbHelper.GENDER};
    private static String ORDER_BY = DbHelper.CLASSROOM + " DESC";

    private Cursor getAllNotes() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_NAME_ANALYZE, COLUMNS, null, null, null, null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;
    }

    private void showNotes(Cursor cursor) {
        StringBuilder builder = new StringBuilder("ข้อความที่บันทึกไว้:\n\n");

        while (cursor.moveToNext()) {
            String content = cursor.getString(1);

//            builder.append("ลำดับ ").append(id).append(": ");
//            builder.append("\t").append(content).append("\n");
        }

//        TextView tv = (TextView) findViewById(R.id.testView);
//        tv.setText(builder);
    }

    private void addToSpinner(Cursor cursor) {
//        Cursor cursor = getAllNotes();
        while (cursor.moveToNext()) {
            String schoolName = cursor.getString(1);
            String room = cursor.getString(2);
            if(classroomList.contains(room) == false) {
                classroomList.add(room);
            }
            if(schoolList.contains(schoolName) == false) {
                schoolList.add(schoolName);
            }
        }
        room.setAdapter(new ArrayAdapter<String>(AnalyzeActivity.this,
                R.layout.spinner_style, classroomList));

        school.setAdapter(new ArrayAdapter<String>(AnalyzeActivity.this,
                R.layout.spinner_style, schoolList));
//
//        TextView tv = (TextView) findViewById(R.id.testView);
//        tv.setText(classroomList.toString());
    }

    private class BackgroundTask extends AsyncTask<Void, Void, String> {

        String JSON_URL;
        @Override
        protected void onPreExecute() {
            JSON_URL = "https://oralhealthstatuscheck.com/getData_analyze.php";
//            Toast.makeText(AnalyzeActivity.this, "background task", Toast.LENGTH_SHORT).show();
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
            json = (TextView) findViewById(R.id.testView);

            json_string = result;
            if (json_string == null) {
                Toast.makeText(AnalyzeActivity.this, "Get Json Before.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    jsonObj = new JSONObject(json_string);
                    int count = 0;
                    jsonArr = jsonObj.getJSONArray("analysis_result");
                    studentId = new String[jsonArr.length()];
                    date = new String[jsonArr.length()];
                    schoolName = new String[jsonArr.length()];
                    classroom = new String[jsonArr.length()];
                    dentName = new String[jsonArr.length()];
                    dmft = new String[jsonArr.length()];
                    studentName = new String[jsonArr.length()];
                    gender = new String[jsonArr.length()];
                    while (count < jsonArr.length()) {
                        JSONObject jo = jsonArr.getJSONObject(count);

                        date[count] = jo.getString("date");
                        schoolName[count] = jo.getString("schoolName");
                        classroom[count] = jo.getString("classroom");
                        studentId[count] = jo.getString("studentID");
                        dentName[count] = jo.getString("dentName");
                        dmft[count] = jo.getString("dmft");
                        studentName [count] = jo.getString("studentName");
                        gender[count] = jo.getString("gender");
                        try {
                            helper.addAnalyzeResult(date[count], schoolName[count], classroom[count],
                                    studentId[count], dentName[count], dmft[count], studentName[count], gender[count]);
                            Cursor cursor = getAllNotes();
//                            showNotes(cursor);
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
}
