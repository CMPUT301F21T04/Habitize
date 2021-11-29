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

import java.util.ArrayList;

public class AllHabitsActivity extends AppCompatActivity implements HabitAdapter.activityEnder, HabitAdapter.reorderEnabler {
    private ArrayList<Habit> dataList;
    private HabitAdapter habitAdapter;
    private RecyclerView list;
    private LinearLayoutManager mLayoutManager;
    private Switch reorder;
    private TextView emptyView;
    private ImageView emptyImg;


    /**
     * Initialize activity
     * @param savedInstanceState the previous instance generated
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        reorder = findViewById(R.id.ReOrderToday);
        emptyImg = (ImageView) findViewById(R.id.emptyImg);
        emptyView = (TextView) findViewById(R.id.empty);

        dataList = new ArrayList<>(); // reset the list
        mLayoutManager = new LinearLayoutManager(this);
        list = findViewById(R.id.habit_list);
        habitAdapter = new HabitAdapter(dataList, false);
        list.setAdapter(habitAdapter);
        list.setItemAnimator(null);
        list.setLayoutManager(mLayoutManager);


        DatabaseManager.getAllHabitsRecycler(dataList, habitAdapter);

        list.setVisibility(View.GONE);
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
        if (dataList.size() != 0) {
            list.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            emptyImg.setVisibility(View.GONE);
        }
        else {
            list.setVisibility(View.GONE);
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
        return reorder.isChecked();
    }
}
