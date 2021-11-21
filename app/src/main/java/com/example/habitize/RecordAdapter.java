package com.example.habitize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecordAdapter extends ArrayAdapter<Record> {
    private ArrayList<Record> records;
    private ViewRecordsFragment context;
    private int resource;
    private recordViewer viewer;

    public RecordAdapter(ViewRecordsFragment context, int resource, ArrayList<Record> recordList){
        super(context.getContext(),resource,recordList);
        this.context = context;
        this.records = recordList;
        this.resource = resource;
        viewer = context;
    }

    public interface recordViewer{
        void viewRecord(int position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context.getContext()).inflate(resource,null);
        }
        // retrieve habit
        Record record = records.get(position);
        TextView dateField = view.findViewById(R.id.recordDate);
        ImageView recordImage = view.findViewById(R.id.recordImage);




        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewer.viewRecord(position);
            }
        });

        // Setting our custom list items
        dateField.setText(record.getDate());
        DatabaseManager.getAndSetImage(record.getRecordIdentifier(),recordImage);



        return view;


    }

}
