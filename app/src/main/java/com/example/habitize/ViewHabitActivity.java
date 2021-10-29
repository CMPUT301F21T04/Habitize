package com.example.habitize;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ViewHabitActivity extends Activity {
    private Habit passedHabit;
    private ArrayList<Habit> passedHabits;
    private int passedIndex;
    private String passedUser;
    private TextView habitName;
    private TextView habitDescription;
    private Button deleteButton;
    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);
        passedHabit = (Habit)getIntent().getExtras().getSerializable("habit");
        passedIndex = (int)getIntent().getExtras().getSerializable("index");
        passedHabits = (ArrayList<Habit>)getIntent().getExtras().getSerializable("habits");
        passedUser  = (String)getIntent().getExtras().getSerializable("user");
        db = FirebaseFirestore.getInstance();
        // initializing database
        colRef = db.collection("userHabits");
        docRef = colRef.document(passedUser);
        habitName = findViewById(R.id.habitTitle);
        habitDescription = findViewById(R.id.habitDescription);
        deleteButton = findViewById(R.id.deleteButton);

        // setting texts
        habitName.setText(passedHabit.getName());
        habitDescription.setText(passedHabit.getDescription());

        // setting listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // We have clicked the deleteButton, modify the current habit list
                passedHabits.remove(passedIndex);
                // hash the list and replace the one at the database
                HashMap<String,Object> dataMap = new HashMap<String,Object>();
                dataMap.put("habits",passedHabits);
                docRef.set(dataMap);
                finish();
            }
        });

    }
}
