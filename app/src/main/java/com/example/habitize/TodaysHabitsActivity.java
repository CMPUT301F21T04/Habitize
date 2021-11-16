package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TodaysHabitsActivity extends AppCompatActivity implements CustomAdapter.habitViewListener, CustomAdapter.habitCheckListener {

    private ArrayList<Habit> dataList;
    private CustomAdapter habitAdapter;
    private ArrayList<Integer> posInFireBase;
    private ListView listView;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private FirebaseFirestore db;
    private String userAcct;
    private SimpleDateFormat simpleDateFormat;
    private ProgressBar progressBar4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todays_habits);
        progressBar4 = findViewById(R.id.progressBar4);

        dataList = new ArrayList<>();
        posInFireBase = new ArrayList<>();


        listView = findViewById(R.id.todaysHabit_list);
        habitAdapter = new CustomAdapter(this, dataList);
        listView.setAdapter(habitAdapter);

        DatabaseManager.getTodaysHabits(dataList, habitAdapter,posInFireBase);

        int totalHabits = posInFireBase.size();


        //getTodaysHabits(ArrayList<Habit> recievingList,CustomAdapter habitAdapter,ArrayList<Integer>
        //        posInFirebase)
    }


    @Override
    public void viewHabitPressed(int position) {
        Intent intent = new Intent(TodaysHabitsActivity.this, ViewHabitTabsBase.class);
        Bundle habitBundle = new Bundle();
        habitBundle.putSerializable("habit", dataList.get(position));
        habitBundle.putSerializable("index",posInFireBase.get(position));
        habitBundle.putSerializable("habits",dataList);
        intent.putExtras(habitBundle);
        startActivity(intent);
    }

    @Override
    public void recordEvent(int position) {
        Intent intent = new Intent(TodaysHabitsActivity.this,RecordCreate.class);
        Bundle habitBundle = new Bundle();
        habitBundle.putSerializable("habit",dataList.get(position)); // pass down the habit at the position
        habitBundle.putSerializable("index",position);
        habitBundle.putSerializable("habits",dataList);
        intent.putExtras(habitBundle);
        RecordCreate newRecord =  new RecordCreate();
        newRecord.show(getSupportFragmentManager(),"new record");
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
