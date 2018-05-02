package com.example.asus.oralhealth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class RecordActivity extends AppCompatActivity implements RecognitionListener {
    private static final String KWS_SEARCH = "wakeup";
    private static final String KEYPHRASE = "start";
    private static final String COMMAND_SEARCH = "commands";
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    String ServerURL = "https://oralhealthstatuscheck.com/insert_result.php";
    private SpeechRecognizer recognizer;
    private HashMap<String, Integer> captions;
    private Button myButton[] = new Button[32];
    TextView showPtid, showPtName;
    MediaPlayer player;
    int index = 0;
    int playlistIndex = 8;
    boolean check = true;
    LinearLayout row1, row2;
    DbHelper helper;
    public String std_id;
    public String name;
    public String today_dt;
    public String dent_name;
    String[] result;
    Button okBtn;
    JSONObject jsonObj;
    JSONArray resultSet;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new DbHelper(this);
        captions = new HashMap<String, Integer>();
        captions.put(KWS_SEARCH, R.string.kws_caption);
        captions.put(COMMAND_SEARCH, R.string.digits_caption);
        setContentView(R.layout.activity_record);

        today_dt = getCurrentDate();
        dent_name = getIntent().getStringExtra("dentist_name");
        Toast.makeText(RecordActivity.this, dent_name, Toast.LENGTH_SHORT).show();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (isNetworkAvailable() == true) {
//            Toast.makeText(MainActivity.this, "Connection", Toast.LENGTH_SHORT).show();
            saveData();
        } else {
            Toast.makeText(RecordActivity.this, "Connection failed.", Toast.LENGTH_SHORT).show();
        }


        TextView back = (TextView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent i = new Intent(RecordActivity.this, DetectActivity.class);
//                i.putExtra("den_username", dent_name);
//                startActivity(i);
            }
        });


        TextView home = (TextView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(RecordActivity.this, DetectActivity.class);
                i.putExtra("den_username", dent_name);
                startActivity(i);
            }
        });

        try {
            Cursor cursor = getAllNotes();
//            showNotes(cursor);
        } finally { //close connection with DB
            helper.close();
        }

        showPtid = (TextView) findViewById(R.id.showpatientId);
        showPtName = (TextView) findViewById(R.id.showpatientName);
        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, R.layout.select_dialog, helper.getAllUsers());
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLUE);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {

                String selectedItem = (String) parent.getItemAtPosition(position);
                try {
                    int num = Integer.parseInt(selectedItem);
                    getData(selectedItem);
//                    Toast.makeText(RecordActivity.this, "number", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
//                    Toast.makeText(RecordActivity.this, "not number", Toast.LENGTH_SHORT).show();
                    getIDfromName(selectedItem);
                }

//                getData(selectedItem);
            }
        });
        createButton();
        okBtn = (Button) findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okBtn.setEnabled(false);
                int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(RecordActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
                    return;
                }
                runRecognizerSetup();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void getData(String id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME + " WHERE " + DbHelper.STD_ID + " ='" + id + "'", null);
        while (c.moveToNext()) {
            std_id = c.getString(0);
            name = c.getString(1);
            showPtid.setText(std_id);
            showPtName.setText(name);
        }
        //close the cursor
        c.close();
        //close the database
        db.close();
    }

    private void getIDfromName(String stdName) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DbHelper.TABLE_NAME + " WHERE " + DbHelper.NAME + " ='" + stdName + "'", null);
        while (c.moveToNext()) {
            std_id = c.getString(0);
            name = c.getString(1);
            showPtid.setText(std_id);
            showPtName.setText(name);
        }
        //close the cursor
        c.close();
        //close the database
        db.close();
    }

    public void createButton() {
        row1 = (LinearLayout) findViewById(R.id.row1);
        row2 = (LinearLayout) findViewById(R.id.row2);
        for (int y = 0; y < 8; y++) {
            myButton[y] = new Button(this);
            myButton[y].setText("" + 1 + (8 - y));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            lp.width = 0;

            row1.addView(myButton[y], lp);
        }
        for (int y = 0; y < 8; y++) {
            myButton[y + 8] = new Button(this);
            myButton[y + 8].setText("" + 2 + (y + 1));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            lp.width = 0;

            row1.addView(myButton[y + 8], lp);
        }
        for (int y = 0; y < 8; y++) {
            myButton[y + 16] = new Button(this);
            myButton[y + 16].setText("" + 4 + (8 - y));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            lp.width = 0;

            row2.addView(myButton[y + 16], lp);
        }
        for (int y = 0; y < 8; y++) {
            myButton[y + 24] = new Button(this);
            myButton[y + 24].setText("" + 3 + (y + 1));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            lp.width = 0;

            row2.addView(myButton[y + 24], lp);
        }
        for (int i = 0; i < 32; i++) {
            final int count = i;
            myButton[count].setBackgroundResource(R.color.white);
            myButton[count].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ColorDrawable buttonColor = (ColorDrawable) myButton[count].getBackground();
                    if (buttonColor.getColor() == Color.WHITE) {
                        myButton[count].setBackgroundResource(R.color.green);
                        myButton[count].setText("0");
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("0")) {
                        myButton[count].setText("A");
                        myButton[count].setBackgroundResource(R.color.green);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("A")) {
                        myButton[count].setText("1");
                        myButton[count].setBackgroundResource(R.color.red);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("1")) {
                        myButton[count].setText("2");
                        myButton[count].setBackgroundResource(R.color.red);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("2")) {
                        myButton[count].setText("B");
                        myButton[count].setBackgroundResource(R.color.red);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("B")) {
                        myButton[count].setText("C");
                        myButton[count].setBackgroundResource(R.color.red);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("C")) {
                        myButton[count].setText("3");
                        myButton[count].setBackgroundResource(R.color.yellow);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("3")) {
                        myButton[count].setText("D");
                        myButton[count].setBackgroundResource(R.color.yellow);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("D")) {
                        myButton[count].setText("G");
                        myButton[count].setBackgroundResource(R.color.yellow);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("G")) {
                        myButton[count].setText("4");
                        myButton[count].setBackgroundResource(R.color.orange);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("4")) {
                        myButton[count].setText("E");
                        myButton[count].setBackgroundResource(R.color.orange);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("E")) {
                        myButton[count].setText("8");
                        myButton[count].setBackgroundResource(R.color.bg);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("8")) {
                        myButton[count].setText("9");
                        myButton[count].setBackgroundResource(R.color.bg);
                    } else if (myButton[count].getText().toString().equalsIgnoreCase("9")) {
                        myButton[count].setText("0");
                        myButton[count].setBackgroundResource(R.color.green);
                    }
                }
            });
        }
    }

    public void playVoice() {
        if (playlistIndex == 0) {
            check = false;
        }
        if (check) {
            player = new MediaPlayer();
            String fileName = "sound1" + playlistIndex;
            int id = getResources().getIdentifier(fileName, "raw", getPackageName());
            player = MediaPlayer.create(this, id);
            player.setLooping(false);
            player.start();
            playlistIndex--;
        }
    }

    private void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(RecordActivity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    ((TextView) findViewById(R.id.textview1))
                            .setText("Failed to init recognizer " + result);
                } else {
                    switchSearch(COMMAND_SEARCH);
                    TextView text2 = (TextView) findViewById(R.id.textview2);
                    text2.setText("Reach onPostExecute");
                }
            }
        }.execute();
    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(KWS_SEARCH))
            switchSearch(COMMAND_SEARCH);
        TextView text2 = (TextView) findViewById(R.id.textview2);
        text2.setText("Reach onEnd");
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();
        if (text.equals(COMMAND_SEARCH)) {
            TextView text2 = (TextView) findViewById(R.id.textview2);
            text2.setText("Reach onPartial");
            switchSearch(COMMAND_SEARCH);
        } else
            ((TextView) findViewById(R.id.textview1)).setText(text);
    }

    @Override
    public void onResult(Hypothesis hypothesis) {

        result = new String[32];
        if (hypothesis != null && index < 32) {
            String text = hypothesis.getHypstr();
            String teeth_no = myButton[index].getText().toString();
            ((TextView) findViewById(R.id.textview1)).setText("Result");
            switch (text) {
                case "ศูนย์":
                    result[index] = "0";
                    myButton[index].setBackgroundResource(R.color.green);
                    break;
                case "หนึ่ง":
                    result[index] = "1";
                    myButton[index].setBackgroundResource(R.color.red);
                    break;
                case "อัลฟ่า":
                    result[index] = "A";
                    myButton[index].setBackgroundResource(R.color.green);
                    break;
                case "สอง":
                    result[index] = "2";
                    myButton[index].setBackgroundResource(R.color.red);
                    break;
                case "บราโว":
                    result[index] = "B";
                    myButton[index].setBackgroundResource(R.color.red);
                    break;
                case "สาม":
                    result[index] = "3";
                    myButton[index].setBackgroundResource(R.color.yellow);
                    break;
                case "ชาร์ลี":
                    result[index] = "C";
                    myButton[index].setBackgroundResource(R.color.red);
                    break;
                case "สี่":
                    result[index] = "4";
                    myButton[index].setBackgroundResource(R.color.orange);
                    break;
                case "เดลต้า":
                    result[index] = "D";
                    myButton[index].setBackgroundResource(R.color.yellow);
                    break;
                case "ห้า":
                    result[index] = "5";
                    break;
                case "หก":
                    result[index] = "6";
                    break;
                case "เจ็ด":
                    result[index] = "7";
                    break;
                case "แปด":
                    result[index] = "8";
                    myButton[index].setBackgroundResource(R.color.bg);
                    break;
                case "เอคโค":
                    result[index] = "E";
                    myButton[index].setBackgroundResource(R.color.orange);
                    break;
                case "เก้า":
                    result[index] = "9";
                    myButton[index].setBackgroundResource(R.color.bg);
                    break;
                case "ฟอกซ์ทรอต":
                    result[index] = "F";
                    break;
                case "กอล์ฟ":
                    result[index] = "G";
                    myButton[index].setBackgroundResource(R.color.yellow);
                    break;
            }
//            Toast.makeText(RecordActivity.this, "index : " + index + " " + teeth_no + " : " + result[index], Toast.LENGTH_SHORT).show();
            saveResult(result[index]);
            index++;
            while (index > 32) {
                Toast.makeText(RecordActivity.this, "index over 32", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    int i;
    public List<String> myList = new ArrayList<String>();
    String status[];

    private void saveResult(String result) {
        i = index;
        status = new String[32];
        status[i] = result;
        myList.add(status[i]);
        myButton[index].setText(myList.get(i));

        if (myList.size() == 32) {
            addToSQLite();

            TextView next = (TextView) findViewById(R.id.next);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToSQLite();
//                finish();
                    Intent i = new Intent(RecordActivity.this, SuccessActivity.class);
                    startActivity(i);
                }
            });
        }

        Toast.makeText(RecordActivity.this, i + " " + index + " " + myList.get(i), Toast.LENGTH_SHORT).show();
    }

    private void addToSQLite() {
        helper.addResult(std_id, name,
                myButton[0].getText().toString(), myButton[1].getText().toString(), myButton[2].getText().toString(), myButton[3].getText().toString(),
                myButton[4].getText().toString(), myButton[5].getText().toString(), myButton[6].getText().toString(), myButton[7].getText().toString(),
                myButton[8].getText().toString(), myButton[9].getText().toString(), myButton[10].getText().toString(), myButton[11].getText().toString(),
                myButton[12].getText().toString(), myButton[13].getText().toString(), myButton[14].getText().toString(), myButton[15].getText().toString(),
                myButton[16].getText().toString(), myButton[17].getText().toString(), myButton[18].getText().toString(), myButton[19].getText().toString(),
                myButton[20].getText().toString(), myButton[21].getText().toString(), myButton[22].getText().toString(), myButton[23].getText().toString(),
                myButton[24].getText().toString(), myButton[25].getText().toString(), myButton[26].getText().toString(), myButton[27].getText().toString(),
                myButton[28].getText().toString(), myButton[29].getText().toString(), myButton[30].getText().toString(), myButton[31].getText().toString(),today_dt,dent_name);
    }

    private static String[] COLUMNS = {DbHelper.STD_ID, DbHelper.NAME,
            DbHelper.TEETH_11, DbHelper.TEETH_12, DbHelper.TEETH_13, DbHelper.TEETH_14,
            DbHelper.TEETH_15, DbHelper.TEETH_16, DbHelper.TEETH_17, DbHelper.TEETH_18,
            DbHelper.TEETH_21, DbHelper.TEETH_22, DbHelper.TEETH_23, DbHelper.TEETH_24,
            DbHelper.TEETH_25, DbHelper.TEETH_26, DbHelper.TEETH_27, DbHelper.TEETH_28,
            DbHelper.TEETH_31, DbHelper.TEETH_32, DbHelper.TEETH_33, DbHelper.TEETH_34,
            DbHelper.TEETH_35, DbHelper.TEETH_36, DbHelper.TEETH_37, DbHelper.TEETH_38,
            DbHelper.TEETH_41, DbHelper.TEETH_42, DbHelper.TEETH_43, DbHelper.TEETH_44,
            DbHelper.TEETH_45, DbHelper.TEETH_46, DbHelper.TEETH_47, DbHelper.TEETH_48, DbHelper.RECORD_DATE, DbHelper.DENTIST_NAME};

    private static String ORDER_BY = DbHelper.STD_ID + " DESC";

    private Cursor getAllNotes() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(DbHelper.TABLE_NAME_RESULT, COLUMNS, null, null, null, null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;
    }

    private JSONArray getResults() {
        String myPath = this.getDatabasePath("oralHealth_app.db").toString();// Set path to your database

        String myTable = DbHelper.TABLE_NAME_RESULT;//Set name of your table

        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        String searchQuery = "SELECT  * FROM " + myTable;
        Cursor cursor = myDataBase.rawQuery(searchQuery, null);

        resultSet = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        if (cursor.getString(i) != null) {

                            Log.d("TAG_NAME", cursor.getString(i));

                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        } else {
                            rowObject.put(cursor.getColumnName(i), "");
                        }
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);

            cursor.moveToNext();
        }
        cursor.close();
        try {
            jsonObj = new JSONObject();
            jsonObj.put("Students", resultSet);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    @Override
    public void onError(Exception e) {
    }

    private void switchSearch(String searchName) {

        recognizer.stop();

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.equals(COMMAND_SEARCH)) {
            TextView text2 = (TextView) findViewById(R.id.textview2);
            text2.setText("Reach switch");
            playVoice();
            recognizer.startListening(searchName);
        }
        String caption = getResources().getString(captions.get(searchName));
        ((TextView) findViewById(R.id.textview1)).setText(caption);

    }

    @Override
    public void onTimeout() {
        switchSearch(COMMAND_SEARCH);
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them
        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "command"))
                .setDictionary(new File(assetsDir, "database.dic"))

                .setRawLogDir(assetsDir)

                .getRecognizer();
        recognizer.addListener(this);

//            recognizer.addKeyphraseSearch(KWS_SEARCH,KEYPHRASE);

        File digitsGrammar = new File(assetsDir, "database.gram");
        recognizer.addGrammarSearch(COMMAND_SEARCH, digitsGrammar);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                runRecognizerSetup();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void saveData() {

        JSONArray result = getResults();

        String id[] = new String[result.length()];
        String name[] = new String[result.length()];
        String teeth_11[] = new String[result.length()];
        String teeth_12[] = new String[result.length()];
        String teeth_13[] = new String[result.length()];
        String teeth_14[] = new String[result.length()];
        String teeth_15[] = new String[result.length()];
        String teeth_16[] = new String[result.length()];
        String teeth_17[] = new String[result.length()];
        String teeth_18[] = new String[result.length()];
        String teeth_21[] = new String[result.length()];
        String teeth_22[] = new String[result.length()];
        String teeth_23[] = new String[result.length()];
        String teeth_24[] = new String[result.length()];
        String teeth_25[] = new String[result.length()];
        String teeth_26[] = new String[result.length()];
        String teeth_27[] = new String[result.length()];
        String teeth_28[] = new String[result.length()];
        String teeth_31[] = new String[result.length()];
        String teeth_32[] = new String[result.length()];
        String teeth_33[] = new String[result.length()];
        String teeth_34[] = new String[result.length()];
        String teeth_35[] = new String[result.length()];
        String teeth_36[] = new String[result.length()];
        String teeth_37[] = new String[result.length()];
        String teeth_38[] = new String[result.length()];
        String teeth_41[] = new String[result.length()];
        String teeth_42[] = new String[result.length()];
        String teeth_43[] = new String[result.length()];
        String teeth_44[] = new String[result.length()];
        String teeth_45[] = new String[result.length()];
        String teeth_46[] = new String[result.length()];
        String teeth_47[] = new String[result.length()];
        String teeth_48[] = new String[result.length()];
        String record_dt[] = new String[result.length()];
        String dent_name[] = new String[result.length()];

        try {
            for (int i = 0; i < result.length(); i++) {
                id[i] = result.getJSONObject(i).getString("studentID");
                name[i] = result.getJSONObject(i).getString("studentName");
                teeth_11[i] = result.getJSONObject(i).getString("teeth_11");
                teeth_12[i] = result.getJSONObject(i).getString("teeth_12");
                teeth_13[i] = result.getJSONObject(i).getString("teeth_13");
                teeth_14[i] = result.getJSONObject(i).getString("teeth_14");
                teeth_15[i] = result.getJSONObject(i).getString("teeth_15");
                teeth_16[i] = result.getJSONObject(i).getString("teeth_16");
                teeth_17[i] = result.getJSONObject(i).getString("teeth_17");
                teeth_18[i] = result.getJSONObject(i).getString("teeth_18");
                teeth_21[i] = result.getJSONObject(i).getString("teeth_21");
                teeth_22[i] = result.getJSONObject(i).getString("teeth_22");
                teeth_23[i] = result.getJSONObject(i).getString("teeth_23");
                teeth_24[i] = result.getJSONObject(i).getString("teeth_24");
                teeth_25[i] = result.getJSONObject(i).getString("teeth_25");
                teeth_26[i] = result.getJSONObject(i).getString("teeth_26");
                teeth_27[i] = result.getJSONObject(i).getString("teeth_27");
                teeth_28[i] = result.getJSONObject(i).getString("teeth_28");
                teeth_31[i] = result.getJSONObject(i).getString("teeth_31");
                teeth_32[i] = result.getJSONObject(i).getString("teeth_32");
                teeth_33[i] = result.getJSONObject(i).getString("teeth_33");
                teeth_34[i] = result.getJSONObject(i).getString("teeth_34");
                teeth_35[i] = result.getJSONObject(i).getString("teeth_35");
                teeth_36[i] = result.getJSONObject(i).getString("teeth_36");
                teeth_37[i] = result.getJSONObject(i).getString("teeth_37");
                teeth_38[i] = result.getJSONObject(i).getString("teeth_38");
                teeth_41[i] = result.getJSONObject(i).getString("teeth_41");
                teeth_42[i] = result.getJSONObject(i).getString("teeth_42");
                teeth_43[i] = result.getJSONObject(i).getString("teeth_43");
                teeth_44[i] = result.getJSONObject(i).getString("teeth_44");
                teeth_45[i] = result.getJSONObject(i).getString("teeth_45");
                teeth_46[i] = result.getJSONObject(i).getString("teeth_46");
                teeth_47[i] = result.getJSONObject(i).getString("teeth_47");
                teeth_48[i] = result.getJSONObject(i).getString("teeth_48");
                record_dt[i] = result.getJSONObject(i).getString("record_date");
                dent_name[i] = result.getJSONObject(i).getString("dentist_name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < result.length(); i++) {
            InsertData(id[i], name[i], teeth_11[i], teeth_12[i], teeth_13[i], teeth_14[i],
                    teeth_15[i], teeth_16[i], teeth_17[i], teeth_18[i],
                    teeth_21[i], teeth_22[i], teeth_23[i], teeth_24[i],
                    teeth_25[i], teeth_26[i], teeth_27[i], teeth_28[i],
                    teeth_31[i], teeth_32[i], teeth_33[i], teeth_34[i],
                    teeth_35[i], teeth_36[i], teeth_37[i], teeth_38[i],
                    teeth_41[i], teeth_42[i], teeth_43[i], teeth_44[i],
                    teeth_45[i], teeth_46[i], teeth_47[i], teeth_48[i], record_dt[i], dent_name[i]);
        }
    }

    public void InsertData(final String id, final String name,
                           final String teeth_11, final String teeth_12, final String teeth_13, final String teeth_14,
                           final String teeth_15, final String teeth_16, final String teeth_17, final String teeth_18,
                           final String teeth_21, final String teeth_22, final String teeth_23, final String teeth_24,
                           final String teeth_25, final String teeth_26, final String teeth_27, final String teeth_28,
                           final String teeth_31, final String teeth_32, final String teeth_33, final String teeth_34,
                           final String teeth_35, final String teeth_36, final String teeth_37, final String teeth_38,
                           final String teeth_41, final String teeth_42, final String teeth_43, final String teeth_44,
                           final String teeth_45, final String teeth_46, final String teeth_47, final String teeth_48, final String record_dt, final String dent_name) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String idHolder = id;
                String NameHolder = name;
                String teeth_11Holder = teeth_11;
                String teeth_12Holder = teeth_12;
                String teeth_13Holder = teeth_13;
                String teeth_14Holder = teeth_14;
                String teeth_15Holder = teeth_15;
                String teeth_16Holder = teeth_16;
                String teeth_17Holder = teeth_17;
                String teeth_18Holder = teeth_18;
                String teeth_21Holder = teeth_21;
                String teeth_22Holder = teeth_22;
                String teeth_23Holder = teeth_23;
                String teeth_24Holder = teeth_24;
                String teeth_25Holder = teeth_25;
                String teeth_26Holder = teeth_26;
                String teeth_27Holder = teeth_27;
                String teeth_28Holder = teeth_28;
                String teeth_31Holder = teeth_31;
                String teeth_32Holder = teeth_32;
                String teeth_33Holder = teeth_33;
                String teeth_34Holder = teeth_34;
                String teeth_35Holder = teeth_35;
                String teeth_36Holder = teeth_36;
                String teeth_37Holder = teeth_37;
                String teeth_38Holder = teeth_38;
                String teeth_41Holder = teeth_41;
                String teeth_42Holder = teeth_42;
                String teeth_43Holder = teeth_43;
                String teeth_44Holder = teeth_44;
                String teeth_45Holder = teeth_45;
                String teeth_46Holder = teeth_46;
                String teeth_47Holder = teeth_47;
                String teeth_48Holder = teeth_48;
                String recordDt_Holder = record_dt;
                String dentName_Holder = dent_name;


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("std_id", idHolder));
                nameValuePairs.add(new BasicNameValuePair("studentName", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("teeth_11", teeth_11Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_12", teeth_12Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_13", teeth_13Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_14", teeth_14Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_15", teeth_15Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_16", teeth_16Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_17", teeth_17Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_18", teeth_18Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_21", teeth_21Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_22", teeth_22Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_23", teeth_23Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_24", teeth_24Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_25", teeth_25Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_26", teeth_26Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_27", teeth_27Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_28", teeth_28Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_31", teeth_31Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_32", teeth_32Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_33", teeth_33Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_34", teeth_34Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_35", teeth_35Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_36", teeth_36Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_37", teeth_37Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_38", teeth_38Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_41", teeth_41Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_42", teeth_42Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_43", teeth_43Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_44", teeth_44Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_45", teeth_45Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_46", teeth_46Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_47", teeth_47Holder));
                nameValuePairs.add(new BasicNameValuePair("teeth_48", teeth_48Holder));
                nameValuePairs.add(new BasicNameValuePair("record_date", recordDt_Holder));
                nameValuePairs.add(new BasicNameValuePair("dentist_name", dentName_Holder));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

                    TextView tv = (TextView) findViewById(R.id.testView);
                    tv.setText(nameValuePairs.toString());
                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();

                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(RecordActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(id, name, teeth_11);

    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-YYYY");
        String strDate = mdformat.format(calendar.getTime());
        return strDate;
    }


}