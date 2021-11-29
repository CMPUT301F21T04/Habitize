package com.example.habitize.Activities.ViewHabit;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

import androidx.fragment.app.Fragment;

import com.example.habitize.R;
import com.example.habitize.Structural.Habit;

import java.util.Calendar;
import java.util.List;

/**
 * Runs when the user views a habit already created.*/
// this is one of the 3 fragments that gets created when viewing a habit
public class ViewHabitBaseFragment extends Fragment   {

    private EditText title;
    private EditText description;
    private TextView startDate;
    private String titleText;
    private String descText;
    private String startDateText;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private CheckBox Monday;
    private CheckBox Tuesday;
    private CheckBox Wednesday;
    private CheckBox Thursday;
    private CheckBox Friday;
    private CheckBox Saturday;
    private CheckBox Sunday;
    private boolean MonRecurrence;
    private boolean TueRecurrence;
    private boolean WedRecurrence;
    private boolean ThurRecurrence;
    private boolean FriRecurrence;
    private boolean SatRecurrence;
    private boolean SunRecurrence;
    private Switch visible;
    private boolean publicHabit;
    private boolean mViewing;
    private boolean visibility;

    private Switch geolocation;
    private Switch Geolocation;
    private Button imageBtn;
    private Button locationBtn;

    private String passedEmail;
    private List<Habit> passedHabits;
    private static final String TAG = "MainActivity";

    /**
     * */
    public ViewHabitBaseFragment() {
        // Required empty public constructor
    }

    // filling constructor for viewing
    public ViewHabitBaseFragment(String titleText, String descText,String startTextDate, boolean monRecurrence,
                                 boolean tueRecurrence, boolean wedRecurrence, boolean thurRecurrence,
                                 boolean friRecurrence, boolean satRecurrence, boolean sunRecurrence,boolean visible ){
        this.titleText = titleText;
        this.descText = descText;
        this.startDateText = startTextDate;
        this.MonRecurrence = monRecurrence;
        this.TueRecurrence = tueRecurrence;
        this.WedRecurrence = wedRecurrence;
        this.ThurRecurrence = thurRecurrence;
        this.FriRecurrence = friRecurrence;
        this.SatRecurrence = satRecurrence;
        this.SunRecurrence = sunRecurrence;
        this.visibility = visible;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_view_habit_base, container, false);

        // connecting views
        title = root.findViewById(R.id.FragmentViewHabitTitle);
        description = root.findViewById(R.id.FragmentViewHabitDescription);
        startDate = root.findViewById(R.id.FragmentViewHabitStartDate);
        Monday = root.findViewById(R.id.FragmentViewHabitMonday);
        Tuesday = root.findViewById(R.id.FragmentViewHabitTuesday);
        Wednesday = root.findViewById(R.id.FragmentViewHabitWednesday);
        Thursday = root.findViewById(R.id.FragmentViewHabitThursday);
        Friday = root.findViewById(R.id.FragmentViewHabitFriday);
        Saturday = root.findViewById(R.id.FragmentViewHabitSaturday);
        Sunday  = root.findViewById(R.id.FragmentViewHabitSunday);
        visible = root.findViewById(R.id.view_publicHabit);

        title.setEnabled(false);
        description.setEnabled(false);
        startDate.setEnabled(false);
        Monday.setEnabled(false);
        Tuesday.setEnabled(false);
        Wednesday.setEnabled(false);
        Thursday.setEnabled(false);
        Friday.setEnabled(false);
        Saturday.setEnabled(false);
        Sunday.setEnabled(false);
        mViewing = (boolean) getArguments().getSerializable("viewing");


        // setting views to values set in the constructor
        Monday.setChecked(MonRecurrence);
        Tuesday.setChecked(TueRecurrence);
        Wednesday.setChecked(WedRecurrence);
        Thursday.setChecked(ThurRecurrence);
        Friday.setChecked(FriRecurrence);
        Saturday.setChecked(SatRecurrence);
        Sunday.setChecked(SunRecurrence);
        title.setText(titleText);
        description.setText(descText);
        startDate.setText(startDateText);
        visible.setChecked(visibility);





        if(mViewing){
            startDate.setClickable(false);
        }
        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_Holo_Light,
                        mDateSetListener,
                        year, month, day);

                //set a minimum date the user can select (cannot choose past dates to start on)
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                //change color of date picker background
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
            }
        });

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


        // listeners. Rachel had a shortened version of this but idk how to fix it
        // this is so unclean. I hate it.
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

        // if switch for public enable is on, the habit will be public to all users
        visible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    publicHabit = true;
                } else{
                    publicHabit = false;
                }
            }
        });

        return root;
    }


    public void setEditable(){
        Monday.setEnabled(true);
        Tuesday.setEnabled(true);
        Wednesday.setEnabled(true);
        Thursday.setEnabled(true);
        Friday.setEnabled(true);
        Saturday.setEnabled(true);
        Sunday.setEnabled(true);
        visible.setEnabled(true);
        title.setEnabled(true);
        description.setEnabled(true);
        startDate.setEnabled(true);
    }
    public void setNotEditable(){
        Monday.setEnabled(false);
        Tuesday.setEnabled(false);
        Wednesday.setEnabled(false);
        Thursday.setEnabled(false);
        Friday.setEnabled(false);
        Saturday.setEnabled(false);
        Sunday.setEnabled(false);
        visible.setEnabled(false);
        title.setEnabled(false);
        description.setEnabled(false);
        startDate.setEnabled(false);

    }


    public boolean getVisible(){
        return this.visible.isChecked();
    }

    public String getTitle(){
        return title.getText().toString();
    }
    public String getDescription(){
        return description.getText().toString();
    }
    public String getDate(){ return startDate.getText().toString();}
    public boolean getMon(){
        return Monday.isChecked();
    }
    public boolean getTue(){
        return Tuesday.isChecked();
    }
    public boolean getWed(){
        return Wednesday.isChecked();
    }
    public boolean getThur(){
        return Thursday.isChecked();
    }
    public boolean getFri(){
        return Friday.isChecked();
    }
    public boolean getSat(){
        return Saturday.isChecked();
    }
    public boolean getSun(){
        return Sunday.isChecked();
    }

    // setters for fields
    public ViewHabitBaseFragment setMon(boolean mon){
        this.MonRecurrence = mon;
        return this;
    }
    public ViewHabitBaseFragment setTue(boolean tue){
        this.TueRecurrence = tue;
        return this;
    }
    public ViewHabitBaseFragment setWed(boolean wed){
        this.WedRecurrence = wed;
        return this;
    }
    public ViewHabitBaseFragment setThurs(boolean thurs){
        this.ThurRecurrence = thurs;
        return this;
    }
    public ViewHabitBaseFragment setFri(boolean fri){
        this.FriRecurrence = fri;
        return this;
    }
    public ViewHabitBaseFragment setSat(boolean sat){
        this.SatRecurrence = sat;
        return this;
    }
    public ViewHabitBaseFragment setSun(boolean sun){
        this.SunRecurrence = sun;
        return this;
    }
    public ViewHabitBaseFragment setDate(String date){
        startDateText = date;
        return this;
    }
    public ViewHabitBaseFragment setTitle(String title){
        titleText = title;
        return this;
    }
    public ViewHabitBaseFragment setDesc(String desc){
        descText = desc;
        return this;
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


