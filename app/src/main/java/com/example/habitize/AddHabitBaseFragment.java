package com.example.habitize;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class AddHabitBaseFragment extends Fragment {
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
    private String MonRecurrence;
    private String TueRecurrence;
    private String WedRecurrence;
    private String ThurRecurrence;
    private String FriRecurrence;
    private String SatRecurrence;
    private String SunRecurrence;
    private Switch geolocation;
    private Switch Geolocation;
    private Button imageBtn;
    private Button locationBtn;
    private FirebaseFirestore db;
    private CollectionReference userCol;
    private DocumentReference docRef;
    private String passedEmail;
    private List<Habit> passedHabits;
    private static final String TAG = "MainActivity";

    public AddHabitBaseFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_habit_base, container, false);
        
        return root;
    }
}