package com.example.habitize;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class AddHabitBaseFragment extends Fragment {
    //variables to be worked with
    private EditText title;
    private EditText description;
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

    private String passedEmail;
    private List<Habit> passedHabits;
    private static final String TAG = "MainActivity";

    /*
     * Required empty public constructor
     */
    public AddHabitBaseFragment() {

    }

    /**
     * Will instantiate the UI view of the create habit screen
     * @param inflater inflates tge views in the fragment
     * @param container for parent view that the fragment's UI should be attached to
     * @param savedInstanceState to be used for Bundle where fragment is re-constructed from a
     *                              previous state
     * @return returns the View of a the create habit screen fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set all day of the week occurrences to false so we can change later
        MonRecurrence = false;
        TueRecurrence = false;
        WedRecurrence = false;
        ThurRecurrence = false;
        FriRecurrence = false;
        SatRecurrence = false;
        SunRecurrence = false;

        //Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_habit_base, container, false);

        //find the views for each id
        createHabit = root.findViewById(R.id.create_habit_tabs);
        title = root.findViewById(R.id.fragmentHabitTitle);
        description = root.findViewById(R.id.fragmentHabitDescription);
        startDate = root.findViewById(R.id.fragmentStartDate);
        Monday = root.findViewById(R.id.fragmentMonday);
        Tuesday = root.findViewById(R.id.fragmentMonday);
        Wednesday = root.findViewById(R.id.fragmentWednesday);
        Thursday = root.findViewById(R.id.fragmentThursday);
        Friday = root.findViewById(R.id.fragmentFriday);
        Saturday = root.findViewById(R.id.fragmentSaturday);
        Sunday  = root.findViewById(R.id.fragmentSunday);


        /*
         * Listener for choosing a date to start a habit. Will use a datePicker to do this below
         */
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //get instance will get the current time zone and locale of the user's system
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //sets up the dialog box for datePicker
                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
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

        /*
         * This listener will set up the format of the date picker and update the text view
         */
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

        /*
         * All day of the week checkbox listeners below
         */
        Monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!MonRecurrence){
                    MonRecurrence = true;
                }
                else{
                    MonRecurrence = false;
                }
            }
        });
        Tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!TueRecurrence){
                    TueRecurrence = true;
                }
                else{
                    TueRecurrence = false;
                }
            }
        });
        Wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!WedRecurrence){
                    WedRecurrence = true;
                }
                else{
                    WedRecurrence = false;
                }
            }
        });
        Thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!ThurRecurrence){
                    ThurRecurrence = true;
                }
                else{
                    ThurRecurrence = false;
                }
            }
        });
        Friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!FriRecurrence){
                    FriRecurrence = true;
                }
                else{
                    FriRecurrence = false;
                }
            }
        });
        Saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!SatRecurrence){
                    SatRecurrence = true;
                }
                else{
                    SatRecurrence = false;
                }
            }
        });
        Sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!SunRecurrence){
                    SunRecurrence = true;
                }
                else{
                    SunRecurrence = false;
                }
            }
        });

        return root;
    }


    /**
     * getter for habit's title
     * @return habit's title as a string
     */
    public String getTitle(){
        return title.getText().toString();
    }

    /**
     * getter for habit's description
     * @return habit's description as a string
     */
    public String getDescription(){
        return description.getText().toString();
    }

    /**
     * Getter for Habit's start date
     * @return habits start as a string
     */
    public String getDate(){ return startDate.getText().toString();}

    /**
     * Getter for seeing if Monday Recurrence was checked
     * @return Monday's checkbox status as true or false
     */
    public boolean getMon(){
        return MonRecurrence;
    }

    /**
     * Getter for seeing if Tuesday Recurrence was checked
     * @return Tuesday's checkbox status as true or false
     */
    public boolean getTue(){
        return TueRecurrence;
    }

    /**
     * Getter for seeing if Wednesday Recurrence was checked
     * @return Wednesday's checkbox status as true or false
     */
    public boolean getWed(){
        return WedRecurrence;
    }

    /**
     * Getter for seeing if Thursday Recurrence was checked
     * @return Thursday's checkbox status as true or false
     */
    public boolean getThur(){
        return ThurRecurrence;
    }

    /**
     * Getter for seeing if Friday Recurrence was checked
     * @return Friday's checkbox status as true or false
     */
    public boolean getFri(){
        return FriRecurrence;
    }

    /**
     * Getter for seeing if Saturday Recurrence was checked
     * @return Saturday's checkbox status as true or false
     */
    public boolean getSat(){
        return SatRecurrence;
    }

    /**
     * Getter for seeing if Sunday Recurrence was checked
     * @return Sunday's checkbox status as true or false
     */
    public boolean getSun(){
        return SunRecurrence;
    }

    /**
     * Check which checkbox was clicked, if clicked, it will set the recurrence to true. If
     * un-clicked, the recurrence will be false (habit does not occur on day).
     * @param view of checkboxes
     */
    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

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
            case R.id.fragmentFriday:
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


