package com.example.asus.oralhealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetectActivity extends AppCompatActivity {
    String dent_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);
        TextView back = (TextView) findViewById(R.id.back);
        back.setText("Back To Login Again.");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetectActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        TextView txt = (TextView) findViewById(R.id.txtView);
        String username = "Hello "+getIntent().getStringExtra("den_username");
        txt.setText(username);

        dent_name = getIntent().getStringExtra("den_username");

        Button record = (Button) findViewById(R.id.record);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetectActivity.this, RecordActivity.class);
                i.putExtra("dentist_name", dent_name);
                startActivity(i);
            }
        });

        Button view = (Button) findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetectActivity.this, AnalyzeActivity.class);
                i.putExtra("dentist_name", dent_name);
                startActivity(i);
            }
        });
    }
}
