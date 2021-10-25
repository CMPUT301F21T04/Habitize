package com.example.habitize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button addHabit;
    private Button allHabits;
    private Button todaysHabits;
    private Button followReq;
    private ProgressBar progressBar;

    private int progressTrack = 0; //starting at 0 for progress (max 100)
    //private Handler progHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance(); // initialize database. We don't really need to do this here.
        // we can have the database instantiated in only the activities where it is needed to pull data
        addHabit = findViewById(R.id.addHabit); // our 4 buttons
        allHabits = findViewById(R.id.allHabits);
        todaysHabits = findViewById(R.id.todaysHabits);
        followReq = findViewById(R.id.followReq);
        progressBar = findViewById(R.id.progressBar);// progress bar

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

            }
        });
        followReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Test for progress bar
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(progressTrack < 100){
//                    android.os.SystemClock.sleep(50);
//                    progHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressBar.setProgress(progressTrack);
//                        }
//                    });
//                }
//            }
//        }).start();

    }
}