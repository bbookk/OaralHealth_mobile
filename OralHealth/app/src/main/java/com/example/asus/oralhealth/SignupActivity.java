package com.example.asus.oralhealth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
//
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.common.api.CommonStatusCodes;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.safetynet.SafetyNet;
//import com.google.android.gms.safetynet.SafetyNetApi;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class SignupActivity extends AppCompatActivity {
//
//    private static final String TAG = MainActivity.class.getSimpleName();
//    private final String url = "https://oralhealthstatuscheck.com/registerMobile.php";
//    final String SiteKey = "6LdRakEUAAAAAG_-Up5-TVKAHyRnI017Ve03UoEE";
//    final String SecretKey = "6LdRakEUAAAAAD0hP_jO6g05pNhjmsSIOt2twCPm";
//    private GoogleApiClient mGoogleApiClient;
//    BufferedWriter bfwriter;
//    OutputStream outputStream;
//    BufferedReader bfReader;
//    String finalResult ;
//    ProgressDialog progressDialog;
//    StringBuilder stringBuilder = new StringBuilder();
//    HashMap<String,String> hashMap = new HashMap<>();
//    EditText usernameField,passwordField,confirmPasswordField,firstnameField,lastnameField,emailField;
//    String username,password,confirmPassword,firstname,lastname,email;
//    String sqlStatement;
//    String response = "";
//    HttpParse httpParse = new HttpParse();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//        Button signupBtn = (Button) findViewById(R.id.signupBtn);
//        usernameField = (EditText)findViewById(R.id.username);
//        passwordField = (EditText)findViewById(R.id.password);
//        confirmPasswordField = (EditText)findViewById(R.id.confirmpassword);
//        firstnameField = (EditText)findViewById(R.id.firstname);
//        lastnameField = (EditText)findViewById(R.id.lastname);
//        emailField = (EditText)findViewById(R.id.email);
//        signupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                username = usernameField.getText().toString();
//                password = passwordField.getText().toString();
//                confirmPassword = confirmPasswordField.getText().toString();
//                firstname = firstnameField.getText().toString();
//                lastname = lastnameField.getText().toString();
//                email = emailField.getText().toString();
//                if(password.equals(confirmPassword)){
//                    insertData(username,password,firstname,lastname,email);
//                }
//                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
//                startActivity(i);
//            }
//        });
//
////       SafetyNet.getClient(this).verifyWithRecaptcha("6LdRakEUAAAAAG_-Up5-TVKAHyRnI017Ve03UoEE")
////                .addOnSuccessListener(this, new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
////                    @Override
////                    public void onSuccess(SafetyNetApi.RecaptchaTokenResponse response) {
////                        if (!response.getTokenResult().isEmpty()) {
////                            //handleSiteVerify(response.getTokenResult());
////                            Toast.makeText(SignupActivity.this, "Success",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                })
////                .addOnFailureListener(this, new OnFailureListener() {
////                    @Override
////                    public void onFailure(@NonNull Exception e) {
////                        if (e instanceof ApiException) {
////                            ApiException apiException = (ApiException) e;
////                            Toast.makeText(SignupActivity.this, "Error message: " +
////                                            CommonStatusCodes.getStatusCodeString(apiException.getStatusCode()),
////                                    Toast.LENGTH_SHORT).show();
////                        } else {
////                            Toast.makeText(SignupActivity.this, "Unknown type of error: " + e.getMessage(),
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                });
//    }
//
//    public void insertData(final String username,final String password,final String firstname,final String lastname,final String email){
//        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//
//                progressDialog = ProgressDialog.show(SignupActivity.this,"Loading Data",null,true,true);
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                hashMap.put("username",params[0]);
//                hashMap.put("password",params[1]);
//                hashMap.put("firstname",params[2]);
//                hashMap.put("lastname",params[3]);
//                hashMap.put("email",params[4]);
//                finalResult = httpParse.postRequest(hashMap, url);
//
//                return finalResult;
//            }
//            @Override
//            protected void onPostExecute(String httpResponseMsg) {
//
//                super.onPostExecute(httpResponseMsg);
//                progressDialog.dismiss();
//
//                Toast.makeText(SignupActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();
//
//            }
//        }
//        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
//
//        sendPostReqAsyncTask.execute(username,password,firstname,lastname,email);
//    }
//}
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    Button signUp;
    EditText User_Name,First_Name, Last_Name, Email, Password,Confirm_Password ;
    String Username_Holder, Firstname_Holder,Lastname_Holder, Email_Holder, Password_Holder,Confirm_Password_Holder;
    String finalResult ;
    String HttpURL = "https://oralhealthstatuscheck.com/registerMobile.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        pattern = Pattern.compile(EMAIL_PATTERN);
        //Assign Id'S
        First_Name = (EditText)findViewById(R.id.firstname);
        User_Name = (EditText)findViewById(R.id.username);
        Last_Name = (EditText)findViewById(R.id.lastname);
        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        Confirm_Password = (EditText)findViewById(R.id.confirmpassword);
        signUp = (Button)findViewById(R.id.signupBtn);

        //Adding Click Listener on button.
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute.
                    if(Password_Holder.length() < 8 && Confirm_Password_Holder.length() < 8){
                        Toast.makeText(SignupActivity.this, "Please use at least 8 characters for password", Toast.LENGTH_LONG).show();
                    }
                    else if(!Password_Holder.equals(Confirm_Password_Holder)){
                        Toast.makeText(SignupActivity.this, "Password does not match", Toast.LENGTH_LONG).show();
                    }
                    else if(!validateEmail(Email_Holder)){
                        Toast.makeText(SignupActivity.this, "Please use a valid email name", Toast.LENGTH_LONG).show();
                    }
                    else{
                        UserRegisterFunction(Username_Holder, Password_Holder,Firstname_Holder,Lastname_Holder,Email_Holder);
                        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }

                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(SignupActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){
        Username_Holder = User_Name.getText().toString();
        Firstname_Holder = First_Name.getText().toString();
        Lastname_Holder = Last_Name.getText().toString();
        Email_Holder = Email.getText().toString();
        Password_Holder = Password.getText().toString();
        Confirm_Password_Holder = Confirm_Password.getText().toString();
        if(TextUtils.isEmpty(Firstname_Holder) || TextUtils.isEmpty(Lastname_Holder) || TextUtils.isEmpty(Email_Holder) || TextUtils.isEmpty(Password_Holder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void UserRegisterFunction(final String username, final String password, final String firstname, final String lastname, final String email){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(SignupActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(SignupActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("username",params[0]);

                hashMap.put("password",params[1]);

                hashMap.put("firstname",params[2]);

                hashMap.put("lastname",params[3]);

                hashMap.put("email",params[4]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(username,password,firstname,lastname,email);
    }
    public boolean validateEmail(final String email){
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

