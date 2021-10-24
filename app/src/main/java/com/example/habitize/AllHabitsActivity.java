package com.example.habitize;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AllHabitsActivity extends AppCompatActivity {
    private HabitList habitList;
    private ArrayList<Habit> dataList;
    private CustomAdapter habitAdapter;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);

    }


    }
