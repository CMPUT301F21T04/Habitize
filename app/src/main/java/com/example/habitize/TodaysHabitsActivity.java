package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TodaysHabitsActivity extends AppCompatActivity implements CustomAdapter.habitViewListener {

    private ArrayList<Habit> dataList;
    private CustomAdapter habitAdapter;
    private ListView listView;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private FirebaseFirestore db;
    private String userAcct;
    private SimpleDateFormat simpleDateFormat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todays_habits);

        userAcct = (String) getIntent().getExtras().getSerializable("user");
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Users");
        documentReference = collectionReference.document(userAcct);
        dataList = new ArrayList<>();

        listView = findViewById(R.id.todaysHabit_list);
        habitAdapter = new CustomAdapter(this, dataList);
        listView.setAdapter(habitAdapter);

        DatabaseManager.getTodaysHabits(userAcct, dataList, habitAdapter);
    }


    @Override
    public void viewHabitPressed(int position) {
        Intent intent = new Intent(TodaysHabitsActivity.this, ViewHabitTabsBase.class);
        Bundle habitBundle = new Bundle();
        habitBundle.putSerializable("habit", dataList.get(position));
        habitBundle.putSerializable("index",position);
        habitBundle.putSerializable("habits",dataList);
        habitBundle.putSerializable("User",userAcct);
        intent.putExtras(habitBundle);
        startActivity(intent);
    }


//    public void
//    //compare true with day of week
//    //add to data list
//

//    if(it is monday today){
//        display all monday habits
//    }
    //if current day == monday
    //then display mondays habits

    //so on and so on



}
