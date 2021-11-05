package com.example.habitize;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.DatabaseErrorHandler;
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

        dataList = new ArrayList<>(); // reset the list

        list = findViewById(R.id.habit_list);
        habitAdapter = new CustomAdapter(this,dataList);
        list.setAdapter(habitAdapter);

        // getting the habits from database and updating the view with them.

        DatabaseManager.getAllHabits(dataList,habitAdapter);

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
