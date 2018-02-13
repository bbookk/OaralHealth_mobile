package com.example.asus.oralhealth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 13/2/2561.
 */

public class DetailAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public DetailAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Detail object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {

        View row;
        row = convertView;
        DetailHolder detailHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            detailHolder = new DetailHolder();
            detailHolder.txt_id = (TextView) row.findViewById(R.id.txt_id);
            detailHolder.txt_schoolName = (TextView) row.findViewById(R.id.txt_schoolName);
            row.setTag(detailHolder);
        }else{
            detailHolder = (DetailHolder) row.getTag();
        }

        Detail detail = (Detail) this.getItem(position);
        detailHolder.txt_id.setText(detail.getId());
        detailHolder.txt_schoolName.setText(detail.getSchoolName());

        return row;
    }

    static class DetailHolder{
        TextView txt_id, txt_schoolName;
    }
}
