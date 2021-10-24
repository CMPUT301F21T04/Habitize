package com.example.habitize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button addHabit;
    private Button allHabits;
    private Button todaysHabits;
    private Button followReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);


        db = FirebaseFirestore.getInstance(); // initialize database. We don't really need to do this here.
        // we can have the database instantiated in only the activities where it is needed to pull data
        addHabit = findViewById(R.id.addHabit); // our 4 buttons
        allHabits = findViewById(R.id.allHabits);
        todaysHabits = findViewById(R.id.todaysHabits);
        followReq = findViewById(R.id.followReq);


        // branch to new activities here
        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        allHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        todaysHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllHabitsActivity.class);
                startActivity(intent);

            }
        });
        followReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        super.onCreate(savedInstanceState);
    }
}