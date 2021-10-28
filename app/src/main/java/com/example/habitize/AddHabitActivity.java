package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseError;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class AddHabitActivity extends AppCompatActivity {
    private EditText title;
    private EditText description;
    private EditText startDate;
    private Button monday;
    private Button tuesday;
    private Button wednesday;
    private Button thursday;
    private Button friday;
    private Button saturday;
    private Button sunday;
    private Switch geolocation;
    private Button imageBtn;
    private Button locationBtn;
    private Button createHabit;
    private User currentUser;
    private FirebaseFirestore db;
    private CollectionReference users;
    private DocumentReference docRef;
    private String passedEmail;
    private List<Habit> passedHabits;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_info);
        passedEmail = (String)getIntent().getExtras().getSerializable("User"); // retrieving passed user
        passedHabits = (List<Habit>)getIntent().getExtras().getSerializable("list");

        imageBtn = findViewById(R.id.addImage);
        locationBtn = findViewById(R.id.addLocation);
        createHabit = findViewById(R.id.createHabit);
        title = findViewById((R.id.habitTitle));
        description = findViewById((R.id.habitDescription));
        db = FirebaseFirestore.getInstance();
        users = db.collection("userHabits");
        docRef = users.document(passedEmail);
        //TO DO: make a header w/ title - NEW HABIT - perhaps textview trick

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddHabitImage.class));             // redo intent handling
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddHabitLocation.class));          // redo intent handling
            }
        });


        createHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // edit our user profile and send it to the database again
                // creating new habit
                Habit newHabit = new Habit(title.getText().toString(), description.getText().toString());

                // adding habit to user profile
                passedHabits.add(newHabit);
                HashMap<String,Object> listMap = new HashMap<>();
                listMap.put("habits",passedHabits);
                docRef.set(listMap); // we send the modified list
                finish();




            }
        });


    }
}
