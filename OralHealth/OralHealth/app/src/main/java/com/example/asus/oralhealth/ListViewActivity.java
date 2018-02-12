package com.example.asus.oralhealth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListViewActivity extends AppCompatActivity {

    String json_string;

    JSONObject jsonObj;
    JSONArray jsonArr;
    DetailAdapter detailAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = (ListView) findViewById(R.id.listview);

        detailAdapter = new DetailAdapter(this, R.layout.row_layout);
        listView.setAdapter(detailAdapter);
        json_string = getIntent().getExtras().getString("json_data");

        try {
            jsonObj = new JSONObject(json_string);
            jsonArr = jsonObj.getJSONArray("result");
            int count = 0;
            String id, schoolName;


            while(count < jsonArr.length()){
                JSONObject jo = jsonArr.getJSONObject(count);
                id = jo.getString("ID");
                schoolName = jo.getString("schoolName");
                Detail detail = new Detail(id,schoolName);
                detailAdapter.add(detail);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
