package com.example.habitize;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class ViewHabitActivity extends Activity {
    private Habit passedHabit;
    private TextView habitName;
    private TextView habitDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);
        habitName = findViewById(R.id.habitTitle);
        habitDescription = findViewById(R.id.habitDescription);
        passedHabit = (Habit)getIntent().getExtras().getSerializable("habit");
        habitName.setText(passedHabit.getName());
        habitDescription.setText(passedHabit.getDescription());




    }
}
