package com.example.habitize.Activities.ViewHabitLists;

import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.Controllers.HabitAdapter;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AllHabitsActivity extends AppCompatActivity implements HabitAdapter.activityEnder {
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
        habitAdapter = new HabitAdapter(dataList, false);
        list.setAdapter(habitAdapter);
        list.setLayoutManager(mLayoutManager);


        DatabaseManager.getAllHabitsRecycler(dataList, habitAdapter);
    }

    @Override
    public void endActivity() {
        finish();
    }
}
