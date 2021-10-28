package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class AddHabitActivity extends AppCompatActivity {
    private EditText title;
    private EditText description;
    private EditText startDate;
    private Button Monday;
    private Button Tuesday;
    private Button Wednesday;
    private Button Thursday;
    private Button Friday;
    private Button Saturday;
    private Button Sunday;
    private Button createHabit;
    private Switch geolocation;
    private Button imageBtn;
    private Button locationBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_info);

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

        /**
         * create button that handles potential user error with input after clicking button.
         * Button will then add the new habit
         */
        createHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get input of all EditTexts and remove whitespace to validate input
                String inputTitle = title.getText().toString().trim();
                String inputDescription = description.getText().toString().trim();
                String inputDate = startDate.getText().toString().trim();

                //check if empty and user left fields blank
                if(TextUtils.isEmpty(inputTitle)){
                    title.setError("Enter a habit title!");
                    return;
                }

                if(TextUtils.isEmpty(inputDescription)){
                    description.setError("Enter a habit description!");
                    return;
                }

                if(TextUtils.isEmpty(inputDate)){
                    startDate.setError("Enter a habit start date!");
                    return;
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


            }
        });



    }

}

