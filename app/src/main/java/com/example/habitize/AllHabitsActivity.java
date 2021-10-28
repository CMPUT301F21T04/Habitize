package com.example.habitize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AllHabitsActivity extends AppCompatActivity implements CustomAdapter.habitViewListener{
    private List<Habit> dataList;
    private CustomAdapter habitAdapter;
    private ListView list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        // retrieving passsed list to populate listview
        dataList = (ArrayList<Habit>)getIntent().getExtras().getSerializable("list");
        list = findViewById(R.id.habit_list);




        habitAdapter = new CustomAdapter(this,dataList);
        list.setAdapter(habitAdapter);



        // testing here


    }


    @Override
    public void viewHabitPressed() {
        Intent intent = new Intent(AllHabitsActivity.this,ViewHabitActivity.class);
        startActivity(intent);
    }
}
