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

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TodaysHabitsActivity extends AppCompatActivity implements CustomAdapter.habitViewListener, CustomAdapter.habitCheckListener {

    private ArrayList<Habit> dataList;
    private HabitAdapter habitAdapter;
    private ArrayList<Integer> posInFireBase;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView listView;
    private DocumentReference documentReference;
    private CollectionReference collectionReference;
    private FirebaseFirestore db;
    private String userAcct;
    private SimpleDateFormat simpleDateFormat;
    private Switch reorderT;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        reorderT = findViewById(R.id.ReOrderToday);



        dataList = new ArrayList<>();
        posInFireBase = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);

        listView = findViewById(R.id.habit_list);
        habitAdapter = new HabitAdapter(dataList,posInFireBase,false);
        listView.setAdapter(habitAdapter);
        listView.setLayoutManager(mLayoutManager);
        DatabaseManager.getTodaysHabitsRecycler(dataList, habitAdapter,posInFireBase);


        /*
        reorderT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for(int i=0; i < habitAdapter.getCount(); i++){

                        View view = listView.getChildAt(i);
                        Button UP = view.findViewById(R.id.HabitUp);
                        Button DOWN = view.findViewById(R.id.HabitDown);
                        FloatingActionButton check = view.findViewById(R.id.completeHabit);
                        check.setVisibility(View.GONE);
                        DOWN.setVisibility(View.VISIBLE);
                        UP.setVisibility(View.VISIBLE);

                        int finalI = i;
                        int finalJ = habitAdapter.getCount()-1 ;
                        UP.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ( finalI != 0) {

                                    Habit tempUP1 = dataList.get(finalI);
                                    Habit tempUP2 = dataList.get(finalI-1);
                                    dataList.set(finalI - 1, tempUP1);
                                    dataList.set(finalI, tempUP2);
                                    DatabaseManager.updateHabits(dataList);
                                    DatabaseManager.getTodaysHabits(dataList, habitAdapter,posInFireBase);
                                }
                            }
                        });

                        DOWN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ( finalI != finalJ) {
                                    Habit tempDown1 = dataList.get(finalI);
                                    Habit tempDown2 = dataList.get(finalI+1);
                                    dataList.set(finalI +1, tempDown1);
                                    dataList.set(finalI, tempDown2);
                                    DatabaseManager.updateHabits(dataList);
                                    DatabaseManager.getTodaysHabits(dataList, habitAdapter,posInFireBase);
                                }
                            }
                        });


                    }
                    DatabaseManager.getTodaysHabits(dataList, habitAdapter,posInFireBase);
                } else {
                    for(int i=0; i < habitAdapter.getCount(); i++) {
                        View view = listView.getChildAt(i);
                        Button UP = view.findViewById(R.id.HabitUp);
                        Button DOWN = view.findViewById(R.id.HabitDown);
                        FloatingActionButton check = view.findViewById(R.id.completeHabit);
                        check.setVisibility(View.VISIBLE);
                        DOWN.setVisibility(View.GONE);
                        UP.setVisibility(View.GONE);
                    }
                }

            }
        });
         */

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
        Bundle habitBundle = new Bundle();
        habitBundle.putSerializable("habit",dataList.get(position)); // pass down the habit at the position
        habitBundle.putSerializable("index",position);
        habitBundle.putSerializable("habits",dataList);
        Intent intent = new Intent(this,CreateRecordBase.class);
        intent.putExtras(habitBundle);
        startActivity(intent);
    }



}
