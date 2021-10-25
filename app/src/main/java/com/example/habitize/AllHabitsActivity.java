package com.example.habitize;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AllHabitsActivity extends AppCompatActivity {
    private ArrayList<Habit> dataList;
    private CustomAdapter habitAdapter;

    private ListView list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        // INTENT TESTS NOT WORKING. THIS IS USED INSTEAD
        Habit testHabit = new Habit("quit smoking","now");
        Habit testHabit1 = new Habit("quit smoking","now");
        Habit testHabit2 = new Habit("quit smoking","now");
        Habit testHabit3 = new Habit("quit smoking","now");
        // END TEST
        list = findViewById(R.id.habit_list);
        dataList = new ArrayList<>();
        dataList.add(testHabit);
        dataList.add(testHabit1);
        dataList.add(testHabit2);
        dataList.add(testHabit3);

        habitAdapter = new CustomAdapter(this,dataList);
        list.setAdapter(habitAdapter);

        // testing here


    }


    }
