package com.example.habitize;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Habit>{

    public interface habitViewListener{
        public void viewHabitPressed(int position);

    }
    public interface habitCheckListener{
        public void recordEvent(int position);
    }
    private habitViewListener viewListener;
    private habitCheckListener checkListener;
    private List<Habit> habits;
    private Context context;

    //
    public CustomAdapter(Context context, List<Habit> habits){
        super(context,0,habits);
        this.habits = habits;
        this.context = context;
        this.viewListener = (habitViewListener) context;
        this.checkListener = (habitCheckListener) context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_content,parent,false);
        }
        // retrieve habit
        Habit habit = habits.get(position);
        TextView nameField = view.findViewById(R.id.habitName);
        // Setting our custom list items
        nameField.setText(habit.getName());
        Button viewButton = view.findViewById(R.id.viewHabit);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // we view the habit at the position
                viewListener.viewHabitPressed(position);
            }
        });

        FloatingActionButton completeButton = view.findViewById(R.id.completeHabit);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListener.recordEvent(position);
            }
        });

        return view;


    }

}
