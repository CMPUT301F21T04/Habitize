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
    private Switch publicHabit;
    private boolean habitToPublic;
    private Button imageBtn;


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
     * @param inflater inflates the views in the fragment
     * @param container for parent view that the fragment's UI should be attached to
     * @param savedInstanceState to be used for Bundle where fragment is re-constructed from
     *                             a previous saved state
     * @return  returns the View of the create habit screen fragment
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

        // Inflate the layout for this fragment
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
        publicHabit = (Switch) root.findViewById(R.id.publicHabit);


        /*
         * Listener for choosing a date to start a habit. Will use a datePicker to do this below
         */
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //get the current time zone and locale of the user's system
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                //make dialog box to pick date
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

                //updates the text view
                startDate.setText(date);
            }
        };


        /*
         * Monday's checkbox listener
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

        /*
         * Tuesday's checkbox listener
         */
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

        /*
         * Wednesday's checkbox listener
         */
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

        //if switch is on, the habit will become public to all users. If not, remains private.
        publicHabit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    habitToPublic = true;
                } else {
                    habitToPublic = false;
                }
            }
        });




        // adding listeners

        
        return root;
    }


    public String getTitle(){
        return title.getText().toString();
    }
    public String getDescription(){
        return description.getText().toString();
    }
    public String getDate(){ return startDate.getText().toString();}
    public boolean getMon(){
        return MonRecurrence;
    }
    public boolean getTue(){
        return TueRecurrence;
    }
    public boolean getWed(){
        return WedRecurrence;
    }
    public boolean getThur(){
        return ThurRecurrence;
    }
    public boolean getFri(){
        return FriRecurrence;
    }
    public boolean getSat(){
        return SatRecurrence;
    }
    public boolean getSun(){
        return SunRecurrence;
    }

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
