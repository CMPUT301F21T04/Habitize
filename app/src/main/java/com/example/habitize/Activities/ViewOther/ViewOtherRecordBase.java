package com.example.habitize.Activities.ViewOther;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.habitize.Activities.CreateRecord.RecordCreate;
import com.example.habitize.Activities.ViewHabit.ViewHabitImageFragment;
import com.example.habitize.Activities.ViewRecord.MapFragment;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;
import com.example.habitize.Structural.Record;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ViewOtherRecordBase extends AppCompatActivity implements MapFragment.scrollDisabler {
    private ViewPager2 pager;
    private TabLayout tabLayout;
    private Button createButton;
    private Button deleteButton;
    private ViewOtherRecordBase.ViewRecordAdapter mAdapter;
    private Habit passedHabit;
    private ArrayList<Habit> passedHabits;
    private int index;
    private Record passedRecord;
    private ArrayList<Record> passedRecords;
    private int passedIndex;
    private boolean mViewing;
    private Switch editSwitch;
    String[] titles = {"Info", "Image", "Location"};

    public ViewOtherRecordBase() {
    }

    ;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        pager = findViewById(R.id.recordPager);
        tabLayout = findViewById(R.id.recordTabs);
        createButton = findViewById(R.id.createRecord);
        deleteButton = findViewById(R.id.deleteRecord);
        editSwitch = findViewById(R.id.recordEditSwitch);
        createButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
        editSwitch.setVisibility(View.INVISIBLE);

        passedHabit = (Habit) getIntent().getSerializableExtra("habit");
        passedRecord = (Record) getIntent().getSerializableExtra("record");
        passedRecords = (ArrayList<Record>) getIntent().getSerializableExtra("records");
        passedIndex = (int) getIntent().getSerializableExtra("index");

        mAdapter = new ViewOtherRecordBase.ViewRecordAdapter(this);
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

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passedRecords.remove(passedIndex);
                DatabaseManager.updateBecauseDeleted(passedHabit.getRecordAddress(), passedRecords);
                finish();
            }
        });
    }

    // disable tab scroll. Used to be able to use map without scrolling on the tabs.
    @Override
    public void disableScroll() {
        pager.setUserInputEnabled(false);

    }

    // enable tab scroll
    @Override
    public void enableScroll() {
        pager.setUserInputEnabled(true);
    }


    class ViewRecordAdapter extends FragmentStateAdapter {
        public ViewRecordAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment returningFragment;
            switch (position) {
                default:
                    returningFragment = new RecordCreate(passedRecord.getDescription());
                    break;
                case 1:
                    returningFragment = new ViewHabitImageFragment(passedRecord.getByteArr());
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("viewing", true);
                    returningFragment.setArguments(bundle1);
                    break;
                case 2:
                    // setting up the location
                    returningFragment = new MapFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("lon",passedRecord.getLon());
                    bundle2.putSerializable("lat",passedRecord.getLat());
                    returningFragment.setArguments(bundle2);
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
