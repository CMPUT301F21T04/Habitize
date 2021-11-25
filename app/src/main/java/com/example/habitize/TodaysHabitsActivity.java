package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TodaysHabitsActivity extends AppCompatActivity {

    private ArrayList<Habit> dataList;
    private HabitAdapter habitAdapter;
    private ArrayList<Integer> posInFireBase;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView listView;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private FirebaseFirestore db;
    private String userAcct;
    private SimpleDateFormat simpleDateFormat;
    private Switch reorderT;
    private ArrayList<Habit> allHabits;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        reorderT = findViewById(R.id.ReOrderToday);



        dataList = new ArrayList<>();
        posInFireBase = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        allHabits = new ArrayList<>();
        listView = findViewById(R.id.habit_list);
        habitAdapter = new HabitAdapter(dataList,posInFireBase,allHabits,false);
        listView.setAdapter(habitAdapter);
        listView.setLayoutManager(mLayoutManager);
        DatabaseManager.getAllHabits(allHabits);
        DatabaseManager.getTodaysHabitsRecycler(dataList, habitAdapter,posInFireBase);


    }

}
