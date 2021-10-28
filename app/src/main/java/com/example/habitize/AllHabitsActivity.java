package com.example.habitize;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AllHabitsActivity extends AppCompatActivity {
    private List<Habit> dataList;
    private CustomAdapter habitAdapter;
    private User currentUser;
    private ListView list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        // retrieving passsed user
        currentUser = (User)getIntent().getExtras().getSerializable("User");
        list = findViewById(R.id.habit_list);


        dataList = currentUser.getUserHabits();


        habitAdapter = new CustomAdapter(this,dataList);
        list.setAdapter(habitAdapter);

        // testing here


    }


    }
