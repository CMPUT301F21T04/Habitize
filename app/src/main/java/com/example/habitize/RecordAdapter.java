package com.example.habitize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecordAdapter extends ArrayAdapter<Record> {
    private ArrayList<Record> records;
    private Context context;
    private int resource;

    public RecordAdapter(Context context,int resource, ArrayList<Record> recordList){
        super(context,resource,recordList);
        this.context = context;
        this.records = recordList;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(resource,null);
        }
        // retrieve habit
        Record record = records.get(position);
        TextView dateField = view.findViewById(R.id.recordDate);
        // Setting our custom list items
        dateField.setText(record.getDate());



        return view;


    }

}