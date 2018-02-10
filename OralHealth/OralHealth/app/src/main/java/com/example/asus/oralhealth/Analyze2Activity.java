package com.example.asus.oralhealth;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Analyze2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze2);

        TextView room = (TextView) findViewById(R.id.room_label);
        TextView date = (TextView) findViewById(R.id.date_label);
        TextView school = (TextView) findViewById(R.id.school_label);

        String dateShow = " Date : "+getIntent().getStringExtra("date");
        date.setText(dateShow);

        String roomShow = " Room : "+getIntent().getStringExtra("room");
        room.setText(roomShow);


        String schoolShow = " School : "+getIntent().getStringExtra("school");
        school.setText(schoolShow);


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
                startActivity(i);
            }
        });
        TextView home = (TextView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Analyze2Activity.this, DetectActivity.class);
                startActivity(i);
            }
        });
    }
}
