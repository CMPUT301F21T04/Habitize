package com.example.habitize.Activities.ViewHabitLists;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

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


public class TodaysHabitsActivity extends AppCompatActivity implements HabitAdapter.activityEnder, HabitAdapter.reorderEnabler{

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
    private ArrayList<Habit> allHabits;
    private TextView emptyView;
    private ImageView emptyImg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        reorderT = findViewById(R.id.ReOrderToday);

        emptyImg = (ImageView) findViewById(R.id.emptyImg);
        emptyView = (TextView) findViewById(R.id.empty);

        dataList = new ArrayList<>();
        posInFireBase = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(this);
        allHabits = new ArrayList<>();
        listView = findViewById(R.id.habit_list);
        habitAdapter = new HabitAdapter(dataList, posInFireBase, allHabits, false);
        listView.setAdapter(habitAdapter);
        listView.setLayoutManager(mLayoutManager);
        listView.setItemAnimator(null);




        DatabaseManager.getAllHabits(allHabits);
        DatabaseManager.getTodaysHabitsRecycler(dataList, habitAdapter, posInFireBase);

        listView.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        emptyImg.setVisibility(View.GONE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                setVisibility();
            }
        }, 1000);



    }

    public void setVisibility(){
        if (habitAdapter.getItemCount() != 0) {
            listView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            emptyImg.setVisibility(View.GONE);
        }
        else {
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyImg.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void endActivity() {
        finish();
    }

    @Override
    public boolean reoderEnabled() {
        return reorderT.isChecked();
    }
}
