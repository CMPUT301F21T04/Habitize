package com.example.habitize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Habit> {

    private ArrayList<Habit> habits;
    private Context context;

    // TODO: Add more fields here. Image..etc

    public CustomAdapter(Context context, ArrayList<Habit> habits){
        super(context,0,habits);
        this.habits = habits;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_content,parent,false);
        }

        Habit habit = habits.get(position);
        TextView nameField = view.findViewById(R.id.habitName);
        nameField.setText(habit.getName());


        return view;


    }

}
