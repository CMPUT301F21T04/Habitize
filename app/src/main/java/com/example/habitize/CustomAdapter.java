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

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Habit>{

    public interface habitViewListener{
        public void viewHabitPressed();

    }
    private habitViewListener listener;
    private List<Habit> habits;
    private Context context;

    // TODO: Add more fields here. Image..etc

    public CustomAdapter(Context context, List<Habit> habits){
        super(context,0,habits);
        this.habits = habits;
        this.context = context;
        this.listener = (habitViewListener) context;
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
        Button viewButton = view.findViewById(R.id.viewHabit);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.viewHabitPressed();
            }
        });



        return view;


    }

}
