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


public class PublicHabitsActivity extends AppCompatActivity implements CustomAdapter.habitViewListener, CustomAdapter.habitCheckListener {

    private ArrayList<Habit> dataList;
    private CustomAdapter habitAdapter;
    private ArrayList<Integer> posInFireBase;
    private ListView listView;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private FirebaseFirestore db;
    private String userAcct;
    private SimpleDateFormat simpleDateFormat;
    private String userNameToFollow;

    /**
     * creates the activity and calls DataBaseManager to display all public habits
     * @param savedInstanceState instance that was last saved
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_habits);

        dataList = new ArrayList<>();
        posInFireBase = new ArrayList<>();

        listView = findViewById(R.id.publicHabit_list);
        habitAdapter = new CustomAdapter(this, dataList);
        listView.setAdapter(habitAdapter);

        Bundle b = getIntent().getExtras();
        //sets var string to the username the user clicked on which will be passed into DataBaseManager
        userNameToFollow = b.getString("name");

        DatabaseManager.getPublicHabits(dataList, habitAdapter,posInFireBase, userNameToFollow);
    }

    @Override
    public void viewHabitPressed(int position) {
        Intent intent = new Intent(this, ViewHabitTabsBase.class);
        Bundle habitBundle = new Bundle();
        habitBundle.putSerializable("habit", dataList.get(position));
        habitBundle.putSerializable("index",posInFireBase.get(position));
        habitBundle.putSerializable("habits",dataList);
        intent.putExtras(habitBundle);
        startActivity(intent);
    }

    @Override
    public void recordEvent(int position) {
        /*
        Bundle habitBundle = new Bundle();
        habitBundle.putSerializable("habit",dataList.get(position)); // pass down the habit at the position
        habitBundle.putSerializable("index",position);
        habitBundle.putSerializable("habits",dataList);
        RecordCreate newRecord =  new RecordCreate();
        newRecord.setArguments(habitBundle);
        newRecord.show(getSupportFragmentManager(),"new record");

         */
    }




}
