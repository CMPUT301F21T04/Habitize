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
        /**
         * knows which habit was pressed on in the listview
         * @param position gets position in list
         */
        public void viewHabitPressed(int position);

    }


    public interface habitCheckListener{
        /**
         * to view a record that was pressed on
         * @param position gets position in list
         */
        public void recordEvent(int position);
    }

    //variables to work with
    private habitViewListener viewListener;
    private habitCheckListener checkListener;
    private List<Habit> habits;
    private Context context;
    private Button UP;
    private Button DOWN;
    /**
     * constructor to initialize CustomAdapter
     * @param context context for customAdapter
     * @param habits where all habits go in a list
     */
    public CustomAdapter(Context context, List<Habit> habits){
        super(context,0,habits);
        this.habits = habits;
        this.context = context;
        this.viewListener = (habitViewListener) context;
        this.checkListener = (habitCheckListener) context;
    }



    /**
     * gets View of list
     * @param position position in list
     * @param convertView
     * @param parent
     * @return returns the custom list item
     */
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


        UP = view.findViewById(R.id.HabitUp);
        DOWN = view.findViewById(R.id.HabitDown);

        //when click view button
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view the habit at the position
                viewListener.viewHabitPressed(position);
            }
        });

        //when click circular floating action button
        FloatingActionButton completeButton = view.findViewById(R.id.completeHabit);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //brings you to the record for that list item
                checkListener.recordEvent(position);
            }
        });

        return view;

    }


    /*
    public void visabliity(boolean checked){
        if (checked) {
            DOWN.setVisibility(View.VISIBLE);
            UP.setVisibility(View.VISIBLE);
        } else {
            DOWN.setVisibility(View.INVISIBLE);
            UP.setVisibility(View.INVISIBLE);
        }

    }*/
}
