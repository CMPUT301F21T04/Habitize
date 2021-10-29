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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AllHabitsActivity extends AppCompatActivity implements CustomAdapter.habitViewListener{
    private ArrayList<Habit> dataList;
    private CustomAdapter habitAdapter;
    private ListView list;
    private DocumentReference userRef;
    private CollectionReference colRef;
    private FirebaseFirestore db;
    private String user;
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        // retrieving passed list to populate listview
        dataList = (ArrayList<Habit>)getIntent().getExtras().getSerializable("list");
        user = (String)getIntent().getExtras().getSerializable("user");
        db = FirebaseFirestore.getInstance();
        colRef = db.collection("userHabits");
        userRef = colRef.document("stasfedyk1911@gmail.com");

        list = findViewById(R.id.habit_list);


        habitAdapter = new CustomAdapter(this,dataList);
        list.setAdapter(habitAdapter);



        // testing here


    }


    @Override
    public void viewHabitPressed(int position) {
        Intent intent = new Intent(AllHabitsActivity.this,ViewHabitActivity.class);
        Bundle habitBundle = new Bundle();
        habitBundle.putSerializable("habit",dataList.get(position)); // pass down the habit at the position
        habitBundle.putSerializable("index",position);
        habitBundle.putSerializable("habits",dataList);
        habitBundle.putSerializable("user",user);
        intent.putExtras(habitBundle);
        startActivity(intent);
    }
    public abstract class habitRefresher {
        ArrayList<Habit> currentList;
        public abstract void refreshList(); // will be called in main to refresh


    }

}
