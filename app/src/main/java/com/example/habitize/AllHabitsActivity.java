package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AllHabitsActivity extends AppCompatActivity implements CustomAdapter.habitViewListener, CustomAdapter.habitCheckListener{
    private ArrayList<Habit> dataList;
    private CustomAdapter habitAdapter;
    private ListView list;
    private DocumentReference docRef;
    private CollectionReference colRef;
    private FirebaseFirestore db;
    private String passedUser;
    private Switch reorder;
    





    /**
     * Initialize activity
     * @param savedInstanceState the previous instance generated
     */
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_habits);
        reorder = findViewById(R.id.ReOrderToday);
        
        dataList = new ArrayList<>(); // reset the list

        list = findViewById(R.id.habit_list);
        habitAdapter = new CustomAdapter(this,dataList);
        list.setAdapter(habitAdapter);

        // getting the habits from database and updating the view with them.
        DatabaseManager.getAllHabits(dataList,habitAdapter);


        reorder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for(int i=0; i < habitAdapter.getCount(); i++){

                        View view = list.getChildAt(i);
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
                                    habitAdapter.notifyDataSetChanged();
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
                                    habitAdapter.notifyDataSetChanged();
                                }
                            }
                        });


                    }
                    habitAdapter.notifyDataSetChanged();
                } else {
                    for(int i=0; i < habitAdapter.getCount(); i++) {
                        View view = list.getChildAt(i);
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

    }




    /**
     * view the habit that we clicked on
     * @param position
     */
    @Override
    public void viewHabitPressed(int position) {
        //on all habits screen
        Intent intent = new Intent(AllHabitsActivity.this,ViewHabitTabsBase.class);
        Bundle habitBundle = new Bundle();
        //pass down the habit at the position
        habitBundle.putSerializable("habit",dataList.get(position));
        habitBundle.putSerializable("index",position);
        habitBundle.putSerializable("habits",dataList);
        habitBundle.putSerializable("User",passedUser);
        //to use on another activity
        intent.putExtras(habitBundle);
        startActivity(intent);
    }


    /**
     * open the recording fragment
     * @param position
     */
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
