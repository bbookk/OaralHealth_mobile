package com.example.asus.oralhealth;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText Username, Password;
    Button LogIn;
    String Password_Holder, Username_Holder;
    String finalResult ;
    String HttpURL = "https://oralhealthstatuscheck.com/loginMobile.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    private String JSON_STRING;
    private DbHelper helper;
    String json_string;
    JSONObject jsonObj;
    JSONArray jsonArr;
    TextView json;
    Bcrypt bcrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bcrypt = new Bcrypt();
        helper = new DbHelper(this);
        Username = (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.password);
        LogIn = (Button)findViewById(R.id.signinBtn);
//        checkData();
        if (isNetworkAvailable() == true) {
//            Toast.makeText(MainActivity.this, "Connection", Toast.LENGTH_SHORT).show();
            new LoginActivity.getLoginData().execute();
        } else {

//            Toast.makeText(MainActivity.this, "Connection failed.", Toast.LENGTH_SHORT).show();
        }

        try {
            Cursor cursor = getAllNotes();
//            showNotes(cursor);
        } finally { //close connection with DB
            helper.close();
        }

        TextView signUp = (TextView)findViewById(R.id.signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();


                if(CheckEditText){
//                    if (isNetworkAvailable() == true) {
                        UserLoginFunction(Username_Holder, Password_Holder);
//                    }
//                    else{
                        checkData();
//                    }

                }
                else {

                    Toast.makeText(LoginActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    public void CheckEditTextIsEmptyOrNot(){

        Username_Holder = Username.getText().toString();
        Password_Holder = Password.getText().toString();

        if(TextUtils.isEmpty(Username_Holder) || TextUtils.isEmpty(Password_Holder))
        {
            CheckEditText = false;
        }
        else {

            CheckEditText = true ;
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void UserLoginFunction(final String username, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(LoginActivity.this,"Please Wait...","Loading Data",true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase(username)){
                    finish();
                    Intent intent = new Intent(LoginActivity.this, DetectActivity.class);
                    intent.putExtra("den_username",username);
                    startActivity(intent);

                }
                else{
//                    Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("username",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(username,password);
    }

    private class getLoginData extends AsyncTask<Void, Void, String> {
        String JSON_URL;

        @Override
        protected void onPreExecute() {
            JSON_URL = "https://oralhealthstatuscheck.com/getDataLogin.php";
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
            json_string = result;
            if (json_string == null) {
                Toast.makeText(LoginActivity.this, "Get Json Before.", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(LoginActivity.this, "Get Json Success", Toast.LENGTH_SHORT).show();
                try {
                    jsonObj = new JSONObject(json_string);
                    jsonArr = jsonObj.getJSONArray("result");
                    int count = 0;
                    String[] id = new String[jsonArr.length()];
                    String[] username = new String[jsonArr.length()];
                    String[] password = new String[jsonArr.length()];
                    String[] firstname = new String[jsonArr.length()];
                    String[] lastname = new String[jsonArr.length()];
                    String[] email = new String[jsonArr.length()];
                    String[] type = new String[jsonArr.length()];

                    while (count < jsonArr.length()) {
                        JSONObject jo = jsonArr.getJSONObject(count);
                        id[count] = jo.getString("id");
                        username[count] = jo.getString("username");
                        password[count] = jo.getString("password");
                        firstname[count] = jo.getString("firstName");
                        lastname[count] = jo.getString("lastname");
                        email[count] = jo.getString("email");
                        type[count] = jo.getString("type");


                        try {
                            helper.addLoginData(id[count], username[count], password[count],
                                    firstname[count], lastname[count], email[count], type[count]);
                            Cursor cursor = getAllNotes();
//                            showNotes(cursor);
                        } finally { //close connection with DB
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

    public boolean checkPassword(String pass , String hash){
        TextView test = (TextView) findViewById(R.id.testView);
        boolean status = false;
        if (bcrypt.checkpw(pass, hash)){
            status = true;
            Toast.makeText(LoginActivity.this,"Login success",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(LoginActivity.this,"Username or Password are in correct",Toast.LENGTH_SHORT).show();
            status = false;
        }
        return status;
    }

    @SuppressLint("ResourceType")
    public void checkData() {
        int count = 0;
        String myPath = this.getDatabasePath("OralHealth_project_cstu29.db").toString();// Set path to your database

        String myTable = DbHelper.TABLE_NAME_DENTIST;//Set name of your table

        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        String searchQuery = "SELECT * FROM " + myTable
                + " WHERE " + DbHelper.USERNAME + " ='" + Username_Holder + "';";
        Cursor cursor = myDataBase.rawQuery(searchQuery, null);

        if (cursor.getCount() == 0) {
            TextView notFound = (TextView) findViewById(R.id.testView);
            notFound.setText(" DATA NOT FOUND !!!!!");
        }
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String firstname = cursor.getString(3);
            String lastName = cursor.getString(4);
            String email = cursor.getString(5);
            String type = cursor.getString(6);

            if(checkPassword(Password_Holder , password) == true){
                if(Integer.parseInt(type)  == 1 || Integer.parseInt(type)  == 2 ){
                    Intent intent = new Intent(LoginActivity.this, DetectActivity.class);
                    intent.putExtra("den_username",username);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"Your Type is incorrect.",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(LoginActivity.this,"Your Passworde is incorrect.",Toast.LENGTH_LONG).show();
            }

            count++;
        }

    }

    private static String[] COLUMNS = {DbHelper.DENTIST_ID, DbHelper.USERNAME, DbHelper.PASSWORD, DbHelper.FIRSTNAME
    , DbHelper.LASTNAME, DbHelper.EMAIL, DbHelper.TYPE};
    private static String ORDER_BY = DbHelper.USERNAME + " DESC";

    private Cursor getAllNotes() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_NAME_DENTIST, COLUMNS, null, null, null, null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;
    }

    private void showNotes(Cursor cursor) {
        StringBuilder builder = new StringBuilder("ข้อความที่บันทึกไว้:\n\n");

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0); //read column 0 _ID
            String content = cursor.getString(1); // Read Colum 2 CONTENT

            builder.append("ลำดับ ").append(id).append(": ");
            builder.append("\t").append(content).append("\n");
        }

        TextView tv = (TextView) findViewById(R.id.testView);
        tv.setText(builder);
    }
}