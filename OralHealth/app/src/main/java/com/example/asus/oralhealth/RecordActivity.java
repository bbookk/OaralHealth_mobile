package com.example.asus.oralhealth;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;

public class RecordActivity extends AppCompatActivity implements RecognitionListener {
    private static final String KWS_SEARCH = "wakeup";
    private static final String KEYPHRASE = "start";
    private static final String DIGITS_SEARCH = "digits";
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private SpeechRecognizer recognizer;
    private HashMap<String, Integer> captions;
    private Button myButton[] = new Button[32];
    Spinner go;
    TextView showPtid,showPtName;
    MediaPlayer player;
    int index = 0;
    int playlistIndex=8;
    boolean check=true;
    LinearLayout row1, row2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        index = 0;
//        check = true;
//        playlistIndex = 8;
        super.onCreate(savedInstanceState);
        captions = new HashMap<String, Integer>();
        captions.put(KWS_SEARCH, R.string.kws_caption);
        captions.put(DIGITS_SEARCH, R.string.digits_caption);
        setContentView(R.layout.activity_record);
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }
        runRecognizerSetup();

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
                finish();
                Intent i = new Intent(RecordActivity.this, SuccessActivity.class);
                startActivity(i);
            }
        });

        TextView home = (TextView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(RecordActivity.this, DetectActivity.class);
                startActivity(i);
            }
        });
        Button good = (Button) findViewById(R.id.allBtn);
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 32; i++) {
                    myButton[i].setBackgroundResource(R.color.green);
                }
            }
        });
        go = (Spinner) findViewById(R.id.go);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.testPTid, R.layout.spinner_style);
        adapter.setDropDownViewResource(R.layout.dropdown_style);
        go.setAdapter(adapter);

        showPtid = (TextView)findViewById(R.id.showpatientId);
        showPtName = (TextView)findViewById(R.id.showpatientName);
        showPtName.setText("กชกร นาระหัด");
        ImageButton goBtn = (ImageButton)findViewById(R.id.goBtn);
        showPtid.setText(go.getSelectedItem().toString());
        goBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPtid.setText(go.getSelectedItem().toString());
            }
        });
        createButton();
    }

    public void createButton(){
      row1 = (LinearLayout) findViewById(R.id.row1);
        row2 = (LinearLayout) findViewById(R.id.row2);
        for (int y = 0; y < 8; y++) {
            myButton[y] = new Button(this);
//            myButton[y].setBackgroundResource(R.color.white);
            myButton[y].setText("" + 1 + (8 - y));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            lp.width = 0;

            row1.addView(myButton[y], lp);
        }
        for (int y = 0; y < 8; y++) {
            myButton[y + 8] = new Button(this);
//            myButton[y+8].setBackgroundResource(R.color.white);
            myButton[y + 8].setText("" + 2 + (y + 1));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            lp.width = 0;

            row1.addView(myButton[y + 8], lp);
        }
        for (int y = 0; y < 8; y++) {
            myButton[y + 16] = new Button(this);
//            myButton[y+16].setBackgroundResource(R.color.white);
            myButton[y + 16].setText("" + 4 + (8 - y));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1;
            lp.width = 0;

            row2.addView(myButton[y + 16], lp);
        }
        for (int y = 0; y < 8; y++) {
            myButton[y + 24] = new Button(this);
//            myButton[y+24].setBackgroundResource(R.color.white);
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
                    } else if (buttonColor.getColor() == Color.rgb(102, 255, 51)) {
                        myButton[count].setBackgroundResource(R.color.red);
                    } else if (buttonColor.getColor() == Color.RED) {
                        myButton[count].setBackgroundResource(R.color.yellow);
                    }else if (buttonColor.getColor() == Color.rgb(255, 255, 0)) {
                        myButton[count].setBackgroundResource(R.color.orange);
                    }
                    else if (buttonColor.getColor() == Color.rgb(255, 153, 0)) {
                        myButton[count].setBackgroundResource(R.color.bg);
                    }else if (buttonColor.getColor() == Color.rgb(255, 255, 230)) {
                        myButton[count].setBackgroundResource(R.color.white);
                    }
                }
            });
        }
    }

    public void playVoice(){
        if(playlistIndex == 0){
            check = false;
        }
        if(check){
            player = new MediaPlayer();
            String fileName = "sound1"+ playlistIndex;
            int id = getResources().getIdentifier(fileName,"raw",getPackageName());
            player = MediaPlayer.create(this,id);
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
                    switchSearch(DIGITS_SEARCH);
                    TextView text2 = (TextView)findViewById(R.id.textview2);
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
            switchSearch(DIGITS_SEARCH);
        TextView text2 = (TextView)findViewById(R.id.textview2);
        text2.setText("Reach onEnd");
    }

    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();
        if (text.equals(DIGITS_SEARCH)){
            TextView text2 = (TextView)findViewById(R.id.textview2);
            text2.setText("Reach onPartial");
            switchSearch(DIGITS_SEARCH);
        }
        else
            ((TextView) findViewById(R.id.textview1)).setText(text);
    }

    @Override
    public void onResult(Hypothesis hypothesis) {
        if(hypothesis != null){
            String text = hypothesis.getHypstr();
            ((TextView) findViewById(R.id.textview1)).setText("Result");
            if(text.equals("zero")){
                myButton[index].setBackgroundResource(R.color.green);
                index++;
            }else if(text.equals("one") || text.equals("a")){
                myButton[index].setBackgroundResource(R.color.red);
                index++;
            }else if(text.equals("two")|| text.equals("b")){
                myButton[index].setBackgroundResource(R.color.red);
                index++;
            }else if(text.equals("three") || text.equals("c")){
                myButton[index].setBackgroundResource(R.color.yellow);
                index++;
            }else if(text.equals("four") || text.equals("d")){
                myButton[index].setBackgroundResource(R.color.orange);
                index++;
            }else if(text.equals("five")){
                index++;
            }else if(text.equals("six")){
                index++;
            }else if(text.equals("seven")){
                index++;
            }else if(text.equals("eight") || text.equals("e")){
                myButton[index].setBackgroundResource(R.color.bg);
                index++;
            }else if(text.equals("nine") || text.equals("g")){
                myButton[index].setBackgroundResource(R.color.bg);
                index++;
            }
        }
    }
    @Override
    public void onError(Exception e) {
    }

    private void switchSearch(String searchName) {

        recognizer.stop();

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.equals(DIGITS_SEARCH)){
            TextView text2 = (TextView)findViewById(R.id.textview2);
            text2.setText("Reach switch");
            playVoice();
            recognizer.startListening(searchName);
        }
        String caption = getResources().getString(captions.get(searchName));
        ((TextView) findViewById(R.id.textview1)).setText(caption);

    }

    @Override
    public void onTimeout() {
        switchSearch(DIGITS_SEARCH);
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them
        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                .setRawLogDir(assetsDir)

                .getRecognizer();
        recognizer.addListener(this);

//            recognizer.addKeyphraseSearch(KWS_SEARCH,KEYPHRASE);

        File digitsGrammar = new File(assetsDir, "digits.gram");
        recognizer.addGrammarSearch(DIGITS_SEARCH, digitsGrammar);

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
}