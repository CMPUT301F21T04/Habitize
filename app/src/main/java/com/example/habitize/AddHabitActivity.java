package com.example.habitize;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddHabitActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private EditText Title;
    private EditText Description;
    private TextView startDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CheckBox Monday;
    private CheckBox Tuesday;
    private CheckBox Wednesday;
    private CheckBox Thursday;
    private CheckBox Friday;
    private CheckBox Saturday;
    private CheckBox Sunday;
    private Button createHabit;
    private boolean MonRecurrence;
    private boolean TueRecurrence;
    private boolean WedRecurrence;
    private boolean ThurRecurrence;
    private boolean FriRecurrence;
    private boolean SatRecurrence;
    private boolean SunRecurrence;
    private Switch geolocation;
    private Switch Geolocation;
    private Button imageBtn;
    private Button locationBtn;
    private FirebaseFirestore db;
    private CollectionReference userCol;
    private DocumentReference docRef;
    private String passedUser;
    private List<Habit> passedHabits;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_info);


        passedUser = (String)getIntent().getExtras().getSerializable("User"); // retrieving passed user


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
        userCol = db.collection("Users");
        docRef = userCol.document(passedUser);

        /*
         * TO DO: public or private, test cases
         */

        //We pull the current habit list, modify it, and send it back (only if we create the habit)
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                passedHabits = new ArrayList<>(); // reset the list
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                for(int i = 0; i < mappedList.size() ; i++){ // get each item one by one
                    Map<String,Object> habitFields = (Map<String, Object>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = (String) habitFields.get("name");
                    String description = (String) habitFields.get("description");
                    String date = (String) habitFields.get("startDate");
                    boolean mondayRec = (boolean) habitFields.get("mondayR");
                    boolean tuesdayRec = (boolean) habitFields.get("tuesdayR");
                    boolean wednesdayRec = (boolean) habitFields.get("wednesdayR");
                    boolean thursdayRec = (boolean) habitFields.get("thursdayR");
                    boolean fridayRec = (boolean) habitFields.get("fridayR");
                    boolean saturdayRec = (boolean) habitFields.get("saturdayR");
                    boolean sundayRec = (boolean) habitFields.get("sundayR");

                    Habit newHabit = new Habit(name,description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec); // create a new habit out of this information
                    passedHabits.add(newHabit); // add it to the habitList
                }
            }
        });

        //Will pop up a date picker which will only allow today's date + future dates to be inputted
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                    AddHabitActivity.this,
                    android.R.style.Theme_DeviceDefault,
                    mDateSetListener,
                    year, month, day);

                //set a minimum date the user can select (cannot choose past dates to start on)
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                //change color of date picker background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
                dialog.show();
            }
        });

        //format of dialog box for datePicker and will set the textBox to chosen date
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                //must add one more month to be able to get the desired month
                month = month + 1;
                //formats the date according to mm/dd/yyyy
                Log.d(TAG, "onDateSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                //updates the textBox
                startDate.setText(date);
            }
        };

        //create button that handles potential user error with input after clicking button.
        //Button will then add the new habit
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


                //creates the habit and stores in database only if validation above is correct
                if ((!TextUtils.isEmpty(inputTitle)) && (!TextUtils.isEmpty(inputDescription)) &&
                        (!TextUtils.isEmpty(inputDate))) {
                    // Create the habit
                    Habit newHabit = new Habit(Title.getText().toString(), Description.getText().toString(),
                            startDate.getText().toString(), MonRecurrence, TueRecurrence, WedRecurrence,
                            ThurRecurrence, FriRecurrence, SatRecurrence, SunRecurrence);
                    // add it to the user list
                    passedHabits.add(newHabit);
                    // Hash it for transportation to database
                    HashMap<String,Object> listMap = new HashMap<>();
                    listMap.put("habits",passedHabits);
                    // send to database and close
                    docRef.update(listMap);
                    finish();
                }
            }
        });

    }

    //Checkbox Implementation below to set up recurrence days of the week
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        //Check which checkbox was clicked, if clicked, it will set the recurrence to yes. If
        //un-clicked, the recurrence will say no (habit does not occur on day).
        switch (view.getId()){
            case R.id.monday:
                if(checked){
                    MonRecurrence = true;
                }
                else {
                    MonRecurrence = false;
                }
                break;
            case R.id.tuesday:
                if(checked){
                    TueRecurrence = true;
                }
                else {
                    TueRecurrence = false;
                }
                break;
            case R.id.wednesday:
                if(checked){
                    WedRecurrence = true;
                }
                else {
                    WedRecurrence = false;
                }
                break;
            case R.id.thursday:
                if(checked){
                    ThurRecurrence = true;
                }
                else{
                    ThurRecurrence = false;
                }
                break;
            case R.id.friday:
                if(checked){
                    FriRecurrence = true;
                }
                else {
                    FriRecurrence = false;
                }
                break;
            case R.id.saturday:
                if(checked){
                    SatRecurrence = true;
                }
                else {
                    SatRecurrence = false;
                }
                break;
            case R.id.sunday:
                if(checked){
                    SunRecurrence = true;
                }
                else {
                    SunRecurrence = false;
                }
                break;
        }
    }

}

