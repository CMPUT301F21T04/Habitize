package com.example.habitize.Activities.CreateRecord;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.habitize.Activities.AddHabit.AddHabitImageFragment;
import com.example.habitize.Activities.ViewRecord.MapFragment;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;
import com.example.habitize.Structural.Record;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class CreateRecordBase extends AppCompatActivity implements MapFragment.scrollDisabler {
    private ViewPager2 pager;
    private TabLayout tabLayout;
    private Button createButton;
    private AddRecordAdapter mAdapter;
    private Habit passedHabit;
    private ArrayList<Habit> passedHabits;
    private int index;
    private Record passedRecord;
    private Switch editSwitch;
    String[] titles = {"Info","Image","Location"};



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        createButton = findViewById(R.id.createRecord);
        pager = findViewById(R.id.recordPager);
        tabLayout = findViewById(R.id.recordTabs);
        editSwitch = findViewById(R.id.recordEditSwitch);
        editSwitch.setVisibility(View.INVISIBLE);

        passedHabits = (ArrayList<Habit>) getIntent().getSerializableExtra("habits");
        passedHabit = (Habit) getIntent().getSerializableExtra("habit");
        passedRecord = (Record) getIntent().getSerializableExtra("record");



        mAdapter = new AddRecordAdapter(this);
        pager.setOffscreenPageLimit(8);
        pager.setAdapter(mAdapter);
        tabLayout = findViewById(R.id.recordTabs);

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        });
        mediator.attach();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecordCreate recordCreateFrag = (RecordCreate) getSupportFragmentManager().findFragmentByTag("f0");
                AddHabitImageFragment imgFrag = (AddHabitImageFragment) getSupportFragmentManager().findFragmentByTag("f1");
                MapFragment mapFrag = (MapFragment) getSupportFragmentManager().findFragmentByTag("f2");

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date d = new Date();
                String currentDate = formatter.format(d);

                String comment = recordCreateFrag.getComment();
                byte[] recordImg = imgFrag.getImageBytes();
                Double lon = mapFrag.getLon();
                Double lat = mapFrag.getLat();
                String id = new UUID(20,10).randomUUID().toString();

                Record newRecord = new Record(currentDate,comment,null,id,lat,lon);
                DatabaseManager.updateRecord(passedHabit.getRecordAddress(),newRecord);
                passedHabit.incrementStreak();
                DatabaseManager.updateHabits(passedHabits);

                if(recordImg != null){
                    DatabaseManager.storeImage(recordImg,newRecord.getRecordIdentifier());
                }
                finish();
            }
        });

        // the maps listens constantly. we need to turn it off and on
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MapFragment fragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("f2");
                int pos = tab.getPosition();
                if(pos == 2){
                    fragment.enableMapScroll();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                MapFragment fragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("f2");
                int pos = tab.getPosition();
                if(pos == 2){
                    fragment.enableMapScroll();
                }
            }
        });

    }

    @Override
    public void disableScroll() {
        pager.setUserInputEnabled(false);

    }

    @Override
    public void enableScroll() {
        pager.setUserInputEnabled(true);
    }


    class AddRecordAdapter extends FragmentStateAdapter{
        public AddRecordAdapter(@NonNull FragmentActivity fragmentActivity){super(fragmentActivity);}

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment returningFragment;
            switch(position){
                default: returningFragment = new RecordCreate();
                break;
                case 1: returningFragment = new AddHabitImageFragment();
                break;
                case 2: returningFragment = new MapFragment();
                break;
            }
            return returningFragment;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
