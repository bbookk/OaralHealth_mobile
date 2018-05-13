package com.example.asus.oralhealth;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SuccessActivity extends AppCompatActivity {
    public String dent_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        dent_name = getIntent().getStringExtra("den_username");
        new CountDownTimer(1500, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                DetectActivity();
            }
        }.start();
    }

    private void DetectActivity() {
        Intent i = new Intent(SuccessActivity.this, DetectActivity.class);
        i.putExtra("den_username", dent_name);
        startActivity(i);
        finish();
    }
}
