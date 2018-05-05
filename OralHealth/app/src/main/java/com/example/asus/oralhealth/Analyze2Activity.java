package com.example.asus.oralhealth;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Analyze2Activity extends AppCompatActivity {

    DbHelper helper;
    String dateValue, roomValue , schoolNameValue;
    JSONArray resultSet;
    JSONObject jsonObj;
    public String dent_name;
    TableLayout tl;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze2);
        dent_name = getIntent().getStringExtra("den_username");
//        Toast.makeText(Analyze2Activity.this, dent_name, Toast.LENGTH_SHORT).show();

        helper = new DbHelper(this);
        TextView room = (TextView) findViewById(R.id.room_label);
        TextView date = (TextView) findViewById(R.id.date_label);
        TextView school = (TextView) findViewById(R.id.school_label);

        dateValue = getIntent().getStringExtra("date");
        roomValue = getIntent().getStringExtra("room");
        schoolNameValue = getIntent().getStringExtra("school");
        String dateShow = " Date : "+dateValue;
        date.setText(dateShow);

        String roomShow = " Room : "+roomValue;
        room.setText(roomShow);

        String schoolShow = " School : "+schoolNameValue;
        school.setText(schoolShow);

        addRow();

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
                Intent i = new Intent(Analyze2Activity.this, DetectActivity.class);
                i.putExtra("den_username", dent_name);
                startActivity(i);
            }
        });
        TextView home = (TextView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Analyze2Activity.this, DetectActivity.class);
                i.putExtra("den_username", dent_name);
                startActivity(i);
            }
        });


    }

    @SuppressLint("ResourceType")
    public void addRow(){
        tl = (TableLayout) findViewById(R.id.main_table);
        int count=0;
        String myPath = this.getDatabasePath("oralHealth_mobiles.db").toString();// Set path to your database

        String myTable = DbHelper.TABLE_NAME_ANALYZE;//Set name of your table

        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        String searchQuery = "SELECT * FROM " + myTable
                + " WHERE " + DbHelper.RECORD_DATE + " ='" + dateValue + "' AND "
                +DbHelper.CLASSROOM + " ='" + roomValue + "' AND "+DbHelper.SCHOOL_NAME + " ='" + schoolNameValue + "';";
        Cursor cursor = myDataBase.rawQuery(searchQuery, null);
        while (cursor.moveToNext()) {
//            String date = cursor.getString(0);
//            String schoolName = cursor.getString(1);
//            String classroom = cursor.getString(2);
            String studentId = cursor.getString(3);
            String dentName = cursor.getString(4);
            String dmft = cursor.getString(5);
            String studentName = cursor.getString(6);
            String gender = cursor.getString(7);

// Create the table row
            TableRow tr = new TableRow(this);
            tr.setBackgroundColor(Color.LTGRAY);
//            if(count%2!=0) tr.setBackgroundColor(R.color.label);
            tr.setId(100+count);
            tr.setPadding(20, 40, 20, 40);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            if(studentId == "" && dentName == "" && dmft == "" && studentName == "" && gender == ""){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons

                builder.setMessage("Data Not Found");
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
            }

            TextView labelStudentId = new TextView(this);
            labelStudentId.setId(200+count);
            labelStudentId.setText(studentId);
            labelStudentId.setGravity(Gravity.CENTER);
            labelStudentId.setTextColor(Color.BLACK);
            labelStudentId.setPadding(20,0,20,0);

            tr.addView(labelStudentId);

            TextView labelGender = new TextView(this);
            labelGender.setId(200+count);
            labelGender.setText(gender);
            labelGender.setGravity(Gravity.CENTER);
            labelGender.setPadding(20,0,20,0);
            labelGender.setTextColor(Color.BLACK);
            tr.addView(labelGender);

            TextView labelStudentName = new TextView(this);
            labelStudentName.setId(200+count);
            labelStudentName.setText(studentName);
            labelStudentName.setGravity(Gravity.CENTER);
            labelStudentName.setPadding(20,0,20,0);
            labelStudentName.setTextColor(Color.BLACK);
            tr.addView(labelStudentName);

            TextView labelDentName = new TextView(this);
            labelDentName.setId(200+count);
            labelDentName.setText(dentName);
            labelDentName.setGravity(Gravity.CENTER);
            labelDentName.setPadding(20,0,20,0);
            labelDentName.setTextColor(Color.BLACK);
            tr.addView(labelDentName);

            Float dmftValue = Float.parseFloat(dmft);

            ProgressBar progressBar = new ProgressBar(this,null,
                    android.R.attr.progressBarStyleHorizontal);
            progressBar.setPadding(0,0,0,0);
            int statusProgress = Integer.parseInt(String.valueOf(dmft))*10;
            progressBar.setIndeterminate(false);
//            progressBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            progressBar.setMax(100);
            progressBar.setProgress(statusProgress);
            if(dmftValue <= 1.1){
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(0, 204, 0)));
            }
            if(dmftValue >= 1.2 || dmftValue >= 2.6){
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
            }
            if(dmftValue >= 2.7 || dmftValue >= 4.4){
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(255, 102, 0)));
            }
            if(dmftValue >= 4.5 || dmftValue > 6.5){
                progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
            }
            progressBar.setScaleY(4f);
            progressBar.setScaleX(0.5f);
            tr.addView(progressBar);

            TextView labelDMFT = new TextView(this);
            labelDMFT.setId(200+count);
            labelDMFT.setText(dmft);
            labelDMFT.setGravity(Gravity.CENTER);
            labelDMFT.setPadding(0,0,0,0);
            labelDMFT.setTextColor(Color.BLACK);
            tr.addView(labelDMFT);

// finally add this to the table row
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            count++;
        }
    }

}
