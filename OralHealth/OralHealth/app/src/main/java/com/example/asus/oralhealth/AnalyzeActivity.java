package com.example.asus.oralhealth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AnalyzeActivity extends AppCompatActivity {

    DateFormat fmtDateAndTime = DateFormat.getDateTimeInstance();
    Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);

        Spinner room = (Spinner) findViewById(R.id.room);
        ArrayAdapter<CharSequence> adapter2 =
                ArrayAdapter.createFromResource(this,
                        R.array.room, R.layout.spinner_style);
        adapter2.setDropDownViewResource(R.layout.dropdown_style);
        room.setAdapter(adapter2);

        Spinner school = (Spinner) findViewById(R.id.school);
        ArrayAdapter<CharSequence> adapter3 =
                ArrayAdapter.createFromResource(this,
                        R.array.school, R.layout.spinner_style);
        adapter3.setDropDownViewResource(R.layout.dropdown_style);
        school.setAdapter(adapter3);

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
                String dateValue = date.getText().toString();
                String roomValue = room.getSelectedItem().toString();
                String schoolValue = school.getSelectedItem().toString();
                i.putExtra("date", dateValue);
                i.putExtra("room", roomValue);
                i.putExtra("school", schoolValue);
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
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", locale);
        TextView showDate = (TextView) findViewById(R.id.showdate);
        showDate.setText(df.format(myCalendar.getTime()));
    }
}
