package com.example.asus.oralhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    final String SiteKey = "6LdRakEUAAAAAG_-Up5-TVKAHyRnI017Ve03UoEE";
    final String SecretKey = "6LdRakEUAAAAAD0hP_jO6g05pNhjmsSIOt2twCPm";
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button signupBtn = (Button) findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                Toast.makeText(SignupActivity.this, "Sign up success", Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

       SafetyNet.getClient(this).verifyWithRecaptcha("6LdRakEUAAAAAG_-Up5-TVKAHyRnI017Ve03UoEE")
                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                    @Override
                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
                        if (!response.getTokenResult().isEmpty()) {
                            //handleSiteVerify(response.getTokenResult());
                            Toast.makeText(SignupActivity.this, "Success",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            ApiException apiException = (ApiException) e;
                            Toast.makeText(SignupActivity.this, "Error message: " +
                                            CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "Unknown type of error: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


