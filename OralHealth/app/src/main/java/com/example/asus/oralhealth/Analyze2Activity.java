package com.example.asus.oralhealth;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
//                Intent i = new Intent(Analyze2Activity.this, DetectActivity.class);
//                i.putExtra("den_username", dent_name);
//                startActivity(i);
            }
        });

        TextView next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Analyze2Activity.this, DetectActivity.class);
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

//    private JSONArray getAnalyzeData() {
//        String myPath = this.getDatabasePath("oralHealth_app.db").toString();// Set path to your database
//
//        String myTable = DbHelper.TABLE_NAME_ANALYZE;//Set name of your table
//
//        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//        String searchQuery = "SELECT * FROM " + myTable
//                + " WHERE " + DbHelper.RECORD_DATE + " ='" + dateValue + "' AND "
//                +DbHelper.CLASSROOM + " ='" + roomValue + "' AND "+DbHelper.SCHOOL_NAME + " ='" + schoolNameValue + "';";
//        Cursor cursor = myDataBase.rawQuery(searchQuery, null);
//
//        resultSet = new JSONArray();
//
//        cursor.moveToFirst();
//        while (cursor.isAfterLast() == false) {
//
//            int totalColumn = cursor.getColumnCount();
//            JSONObject rowObject = new JSONObject();
//
//            for (int i = 0; i < totalColumn; i++) {
//                if (cursor.getColumnName(i) != null) {
//                    try {
//                        if (cursor.getString(i) != null) {
//
//                            Log.d("TAG_NAME", cursor.getString(i));
//
//                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
//                        } else {
//                            rowObject.put(cursor.getColumnName(i), "");
//                        }
//                    } catch (Exception e) {
//                        Log.d("TAG_NAME", e.getMessage());
//                    }
//                }
//            }
//            resultSet.put(rowObject);
//
//            cursor.moveToNext();
//        }
//        cursor.close();
//
//        try {
//            jsonObj = new JSONObject();
//            jsonObj.put("Analyze_data", resultSet);
////            TextView test = (TextView) findViewById(R.id.testView);
////            test.setText(resultSet.toString());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return resultSet;
//    }

    @SuppressLint("ResourceType")
    public void addRow(){
        tl = (TableLayout) findViewById(R.id.main_table);

        TableRow tr_head = new TableRow(this);
        tr_head.setId(10);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView label_date = new TextView(this);
        label_date.setId(20);
        label_date.setText("DATE");
        label_date.setTextColor(Color.WHITE);
        label_date.setPadding(5, 5, 5, 5);
        tr_head.addView(label_date);// add the column to the table row here

        TextView label_weight_kg = new TextView(this);
        label_weight_kg.setId(21);// define id that must be unique
        label_weight_kg.setText("Wt(Kg.)"); // set the text for the header
        label_weight_kg.setTextColor(Color.WHITE); // set the color
        label_weight_kg.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_weight_kg); // add the column to the table row here

        Integer count=0;
        String myPath = this.getDatabasePath("oralHealth_app.db").toString();// Set path to your database

        String myTable = DbHelper.TABLE_NAME_ANALYZE;//Set name of your table

        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        String searchQuery = "SELECT * FROM " + myTable
                + " WHERE " + DbHelper.RECORD_DATE + " ='" + dateValue + "' AND "
                +DbHelper.CLASSROOM + " ='" + roomValue + "' AND "+DbHelper.SCHOOL_NAME + " ='" + schoolNameValue + "';";
        Cursor cursor = myDataBase.rawQuery(searchQuery, null);
        StringBuilder builder = new StringBuilder("ข้อความที่บันทึกไว้:\n\n");
        while (cursor.moveToNext()) {
//            String date = cursor.getString(0);
//            String schoolName = cursor.getString(1);
//            String classroom = cursor.getString(2);
            String studentId = cursor.getString(3);
            String dentName = cursor.getString(4);
            String dmft = cursor.getString(5);
            String studentName = cursor.getString(6);
            String gender = cursor.getString(7);

            builder.append("ลำดับ ").append(studentId).append(": ");
            builder.append("\t").append(studentName).append("\n");

                    TextView tv = (TextView) findViewById(R.id.testView);
        tv.setText(builder);

// Create the table row
            TableRow tr = new TableRow(this);
            if(count%2!=0) tr.setBackgroundColor(Color.GRAY);
            tr.setId(100+count);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

//Create two columns to add as table data
            // Create a TextView to add date
            TextView labelStudentId = new TextView(this);
            labelStudentId.setId(200+count);
            labelStudentId.setText(studentId);
            labelStudentId.setPadding(2, 0, 5, 0);
            labelStudentId.setTextColor(Color.BLACK);
            tr.addView(labelStudentId);

            TextView labelGender = new TextView(this);
            labelGender.setId(200+count);
            labelGender.setText(gender);
            labelGender.setTextColor(Color.BLACK);
            tr.addView(labelGender);

            TextView labelStudentName = new TextView(this);
            labelStudentName.setId(200+count);
            labelStudentName.setText(studentName);
            labelStudentName.setTextColor(Color.BLACK);
            tr.addView(labelStudentName);

            TextView labelDentName = new TextView(this);
            labelDentName.setId(200+count);
            labelDentName.setText(dentName);
            labelDentName.setTextColor(Color.BLACK);
            tr.addView(labelDentName);

            TextView labelDMFT = new TextView(this);
            labelDMFT.setId(200+count);
            labelDMFT.setText(dmft);
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
