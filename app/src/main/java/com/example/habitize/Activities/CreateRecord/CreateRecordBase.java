package com.example.habitize.Activities.CreateRecord;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CreateRecordBase extends AppCompatActivity implements MapFragment.scrollDisabler {
    private ViewPager2 pager;
    private TabLayout tabLayout;
    private Button createButton;
    private AddRecordAdapter mAdapter;
    private Habit passedHabit;
    private ArrayList<Habit> passedHabits;
    private int index;
    private Record passedRecord;
    String[] titles = {"Info","Image","Location"};



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        createButton = findViewById(R.id.createRecord);
        pager = findViewById(R.id.recordPager);
        tabLayout = findViewById(R.id.recordTabs);

        checkStreak();
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
                passedHabit.setTotalComplete(passedHabit.getTotalComplete()+1);
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



    public void checkStreak(){
        int dayOfWeekNow;
        int dayOfWeek;
        boolean found = false;
        int track = 0;
        int countDays = 0;
        int dow = 0;
        Date today = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        Calendar calPast = Calendar.getInstance();
        calPast.setTime(passedHabit.getLastCheckIn());

        dayOfWeekNow = cal.get(Calendar.DAY_OF_WEEK);// sunday = 1, sat = 7
        dayOfWeek = calPast.get(Calendar.DAY_OF_WEEK);// sunday = 1, sat = 7
        List<Boolean> occurence = new ArrayList<Boolean>();
        occurence.add(passedHabit.getSundayR());
        occurence.add(passedHabit.getMondayR());
        occurence.add(passedHabit.getTuesdayR());
        occurence.add(passedHabit.getWednesdayR());
        occurence.add(passedHabit.getThursdayR());
        occurence.add(passedHabit.getFridayR());
        occurence.add(passedHabit.getSaturdayR());
        long diffInMil = Math.abs(passedHabit.getLastCheckIn().getTime() - today.getTime());
        int diff = (int)TimeUnit.DAYS.convert(diffInMil, TimeUnit.MILLISECONDS);




        if (diff > 7){
            passedHabit.resetStreak();
        }
        else if(diff < 7){
            while(found == false){
                if(occurence.get(dayOfWeekNow-1) == true && dayOfWeek == dayOfWeekNow){
                    found = true;
                    passedHabit.incrementStreak();
                }
                else if(occurence.get(dayOfWeekNow-1) == true && dayOfWeek != dayOfWeekNow){
                    found = true;
                    passedHabit.resetStreak();
                }
                else{
                    if(dayOfWeekNow <= 1){
                        dayOfWeekNow = 7;
                    }
                    else{
                        dayOfWeekNow--;
                    }
                }
            }
        }
        else{
            passedHabit.incrementStreak();
        };

        //Checking how many days to add to total
        for(int i = 0; i < diff;i++){
            if(occurence.get(dow) == true){
                countDays++;
            }
            if(dow == 6){
                dow = 0;
            }
            else{
                dow++;
            }
        }


        passedHabit.setTotalDays(passedHabit.getTotalDays()+countDays);


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
