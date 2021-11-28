package com.example.habitize.Activities.Followers;

import android.os.Bundle;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class PublicHabitsActivity extends AppCompatActivity implements HabitAdapter.activityEnder {

    private ArrayList<Habit> dataList;
    private HabitAdapter habitAdapter;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Integer> posInFireBase;
    private RecyclerView listView;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private FirebaseFirestore db;
    private String userAcct;
    private SimpleDateFormat simpleDateFormat;
    private String userNameToFollow;
   // private ArrayList<Habit> allHabits;

    public PublicHabitsActivity() {
    }

    /**
     * creates the activity and calls DataBaseManager to display all public habits
     * @param savedInstanceState instance that was last saved
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_habits);

        dataList = new ArrayList<>();
        posInFireBase = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        //allHabits = new ArrayList<>();
        listView = findViewById(R.id.publicHabit_list);
        habitAdapter = new HabitAdapter(dataList,true);
        listView.setAdapter(habitAdapter);
        listView.setLayoutManager(mLayoutManager);

        Bundle b = getIntent().getExtras();
        //sets var string to the username the user clicked on which will be passed into DataBaseManager
        userNameToFollow = b.getString("name");

        //DatabaseManager.getAllHabits(allHabits);
        DatabaseManager.getPublicHabitsRecycler(dataList, habitAdapter, posInFireBase, userNameToFollow);
    }

    @Override
    public void endActivity() {
        // doesn't have to do anything since we cant edit other's habits.
    }
}
