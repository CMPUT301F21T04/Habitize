package com.example.habitize;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;



import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;

import com.google.firebase.firestore.FirebaseFirestoreException;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //edit habits init
    private Button editHabit;
    private EditText title;
    private EditText description;
    private EditText startDate;

    private EditText Title;
    private EditText Description;
    private EditText StartDate;

    private Button Monday;
    private Button Tuesday;
    private Button Wednesday;
    private Button Thursday;
    private Button Friday;
    private Button Saturday;
    private Button Sunday;
    private Button createHabit;

    private Switch geolocation;
    private Switch Geolocation;

    private Button imageBtn;
    private Button locationBtn;

    //private FirebaseFirestore db;
    private CollectionReference userCol;
    //private DocumentReference docRef;
    private String passedEmail;
    //private List<Habit> passedHabits;

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
        passedEmail = (String)getIntent().getExtras().getSerializable("User"); // retrieving passed user

        imageBtn = findViewById(R.id.addImage);
        locationBtn = findViewById(R.id.addLocation);

        createHabit = findViewById(R.id.create_habit);
        title = findViewById(R.id.habitTitle);
        description = findViewById(R.id.habitDescription);
        startDate = findViewById(R.id.startDate);
        Monday = findViewById(R.id.monday);
        Tuesday = findViewById(R.id.tuesday);
        Wednesday = findViewById(R.id.wednesday);
        Thursday = findViewById(R.id.thursday);
        Friday = findViewById(R.id.friday);
        Saturday = findViewById(R.id.saturday);
        Sunday = findViewById(R.id.sunday);

        Title = findViewById((R.id.habitTitle));
        Description = findViewById((R.id.habitDescription));
        db = FirebaseFirestore.getInstance();
        userCol = db.collection("userHabits");
        docRef = userCol.document(passedEmail);


        //Edit habit //
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                passedHabits = new ArrayList<>(); // reset the list
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                for(int i = 0; i < mappedList.size() ; i++){ // get each item one by one
                    Map<String,String> habitFields = (Map<String, String>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = habitFields.get("name");
                    String description = habitFields.get("description");
                    Habit newHabit = new Habit(name,description); // create a new habit out of this information
                    passedHabits.add(newHabit); // add it to the habitList

                }
            }
        });

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
        //Edit habits button
        editHabit = findViewById(R.id.editHabit);
        editHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get input of all EditTexts and remove whitespace to validate input
                String inputTitle = title.getText().toString().trim();
                String inputDescription = description.getText().toString().trim();
                String inputDate = startDate.getText().toString().trim();

                //check if empty and user left fields blank
                if(TextUtils.isEmpty(inputTitle)){
                    title.setError("Enter a habit title!");
                }

                if(TextUtils.isEmpty(inputDescription)){
                    description.setError("Enter a habit description!");
                }

                if(TextUtils.isEmpty(inputDate)){
                    startDate.setError("Enter a habit start date!");
                }

                //make sure title is up to 20 chars
                if (inputTitle.length() > 20){
                    title.setError("Habit title should be no more than 20 characters!");
                    return;
                }

                //make sure habit description is up to 30 chars
                if (inputDescription.length() > 30){
                    description.setError("Habit description should be no more than 30 characters!");
                    return;
                }

                /**
                 * creates the habit and stores in database only if validation above is correct
                 */
                if ((!TextUtils.isEmpty(inputTitle)) && (!TextUtils.isEmpty(inputDescription)) && (!TextUtils.isEmpty(inputDate))) {
                    // Create the habit
                    Habit newHabit = new Habit(Title.getText().toString(), Description.getText().toString());
                    // add it to the user list
                    passedHabits.add(newHabit);
                    // Hash it for transportation to database
                    HashMap<String,Object> listMap = new HashMap<>();
                    listMap.put("habits",passedHabits);
                    // send to database and close
                    docRef.set(listMap);
                    finish();
                }

            }

            });





    }
}
