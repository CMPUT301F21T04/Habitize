package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AllHabitsActivity extends AppCompatActivity {
    private ArrayList<Habit> dataList;
    private HabitAdapter habitAdapter;
    private RecyclerView list;
    private LinearLayoutManager mLayoutManager;
    private DocumentReference docRef;
    private CollectionReference colRef;
    private FirebaseFirestore db;
    private String passedUser;
    private Switch reorder;
    

    /**
     * Initialize activity
     * @param savedInstanceState the previous instance generated
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        reorder = findViewById(R.id.ReOrderToday);
        
        dataList = new ArrayList<>(); // reset the list
        mLayoutManager = new LinearLayoutManager(this);
        list = findViewById(R.id.habit_list);
        habitAdapter = new HabitAdapter(dataList,false);
        list.setAdapter(habitAdapter);
        list.setLayoutManager(mLayoutManager);


        DatabaseManager.getAllHabitsRecycler(dataList,habitAdapter);


    }




}
