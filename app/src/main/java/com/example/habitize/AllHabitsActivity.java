package com.example.habitize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AllHabitsActivity extends AppCompatActivity implements CustomAdapter.habitViewListener{
    private ArrayList<Habit> dataList;
    private CustomAdapter habitAdapter;
    private ListView list;
    private DocumentReference docRef;
    private CollectionReference colRef;
    private FirebaseFirestore db;
    private String passedUser;
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        // retrieving passed list to populate listview
        passedUser = (String)getIntent().getExtras().getSerializable("user");
        db = FirebaseFirestore.getInstance();
        colRef = db.collection("Users");
        docRef = colRef.document(passedUser);
        dataList = new ArrayList<>(); // reset the list

        list = findViewById(R.id.habit_list);
        habitAdapter = new CustomAdapter(this,dataList);
        list.setAdapter(habitAdapter);

        // getting the habits from database and updating the view with them.
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                // updating the
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                habitAdapter.clear();
                for(int i = 0; i < mappedList.size() ; i++){ // get each item one by one
                    Map<String,Object> habitFields = (Map<String, Object>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = (String)habitFields.get("name");
                    String description = (String)habitFields.get("description");
                    String date = (String)habitFields.get("startDate");
                    boolean mondayRec = (boolean) habitFields.get("mondayR");
                    boolean tuesdayRec = (boolean) habitFields.get("tuesdayR");
                    boolean wednesdayRec = (boolean) habitFields.get("wednesdayR");
                    boolean thursdayRec = (boolean) habitFields.get("thursdayR");
                    boolean fridayRec = (boolean) habitFields.get("fridayR");
                    boolean saturdayRec = (boolean) habitFields.get("saturdayR");
                    boolean sundayRec = (boolean) habitFields.get("sundayR");

                    Habit newHabit = new Habit(name,description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec); // create a new habit out of this information
                    dataList.add(newHabit); // add it to the habitList

                }
                habitAdapter.notifyDataSetChanged();
            }
        });


    }


    @Override
    public void viewHabitPressed(int position) {
        Intent intent = new Intent(AllHabitsActivity.this,ViewHabitTabsBase.class);
        Bundle habitBundle = new Bundle();
        habitBundle.putSerializable("habit",dataList.get(position)); // pass down the habit at the position
        habitBundle.putSerializable("index",position);
        habitBundle.putSerializable("habits",dataList);
        habitBundle.putSerializable("User",passedUser);
        intent.putExtras(habitBundle);
        startActivity(intent);
    }


}
