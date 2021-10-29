package com.example.habitize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FollowingActivity extends AppCompatActivity {
    private List<Habit> dataList;
    private CustomListOfExistingFollowers CustomListOfExistingFollowersAdapter;
    private ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        // retrieving passed list to populate listview
        String[] dataList = {"John Wick", "Dwight Shrute", "Jim Halpert", "Pamela Beasley", "Michael Scott", "Erin Hannon"};
        listView = findViewById(R.id.listOfExistingFollowers);

        CustomListOfExistingFollowersAdapter = new CustomListOfExistingFollowers(this,dataList);
        listView.setAdapter(CustomListOfExistingFollowersAdapter);

        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });



    }
}