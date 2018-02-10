package com.example.asus.oralhealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ExportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        Spinner from = (Spinner) findViewById(R.id.from);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.testPTid, R.layout.spinner_style);
        adapter.setDropDownViewResource(R.layout.dropdown_style);
        from.setAdapter(adapter);

        Spinner to = (Spinner) findViewById(R.id.to);
        ArrayAdapter<CharSequence> adapter2 =
                ArrayAdapter.createFromResource(this,
                        R.array.testPTid, R.layout.spinner_style);
        adapter2.setDropDownViewResource(R.layout.dropdown_style);
        to.setAdapter(adapter);

        Button excel = (Button) findViewById(R.id.exportBtn);
        excel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ExportActivity.this, SuccessActivity.class);
                startActivity(i);
            }
        });

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
                Intent i = new Intent(ExportActivity.this, SuccessActivity.class);
                startActivity(i);
            }
        });
        TextView home = (TextView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ExportActivity.this, DetectActivity.class);
                startActivity(i);
            }
        });
    }
}
