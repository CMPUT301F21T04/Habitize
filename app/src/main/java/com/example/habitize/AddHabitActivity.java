package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CollectionReference userCol;
    private DocumentReference docRef;
    private String passedEmail;
    private List<Habit> passedHabits;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_info);
        passedEmail = (String)getIntent().getExtras().getSerializable("User"); // retrieving passed user

        imageBtn = findViewById(R.id.addImage);
        locationBtn = findViewById(R.id.addLocation);
        createHabit = findViewById(R.id.editHabit);
        title = findViewById((R.id.habitTitle));
        description = findViewById((R.id.habitDescription));
        db = FirebaseFirestore.getInstance();
        userCol = db.collection("userHabits");
        docRef = userCol.document(passedEmail);
        //TO DO: make a header w/ title - NEW HABIT - perhaps textview trick


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
