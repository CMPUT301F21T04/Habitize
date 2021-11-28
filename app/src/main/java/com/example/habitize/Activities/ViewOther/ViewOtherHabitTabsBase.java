package com.example.habitize.Activities.ViewOther;

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

import com.example.habitize.Activities.ViewHabit.ViewHabitBaseFragment;
import com.example.habitize.Activities.ViewHabit.ViewHabitImageFragment;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;
import com.example.habitize.Structural.Record;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ViewOtherHabitTabsBase extends AppCompatActivity {
    private ViewPager2 pager;
    private TabLayout tabLayout;

    private Button ConfirmEdit;
    private Switch AllowEdit;
    private Habit passedHabit; // habit to view
    private ArrayList<Habit> passedHabits;
    private Button deleteButton;
    private ArrayList<Record> recordList;
    String[] titles = {"INFO","IMAGE","RECORDS"};

    /**
     * Initialize activity
     * @param savedInstanceState the previous instance generated
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Connecting view habits variables to xml files
        setContentView(R.layout.activity_view_habit_tabs);
        pager = findViewById(R.id.recordPager);
        ConfirmEdit = findViewById(R.id.ConfirmEdit);
        AllowEdit = findViewById(R.id.AllowEditing);
        deleteButton = findViewById(R.id.delete_button_tabs);
        passedHabit = (Habit) getIntent().getExtras().getSerializable("habit"); // a user
        passedHabits = new ArrayList<>(); // we will get the latest list from the database

        deleteButton.setVisibility(View.INVISIBLE);
        recordList = new ArrayList<>();
        // pulling the most recent habits

        // pager holds fragments, madapter is the adapter needed for it
        ViewAdapter mAdapter = new ViewAdapter(this);
        // set high off screen page limit so every fragment exists. Otherwise they would die
        // when we swipe away from them
        pager.setOffscreenPageLimit(8);
        pager.setAdapter(mAdapter);
        tabLayout = findViewById(R.id.recordTabs);

        // connects the tablayout to the pager navigation. Now they are synced on swipes
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();

        AllowEdit.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
        ConfirmEdit.setVisibility(View.INVISIBLE);


        //edit toggle button which
    }


    // class for managing the views in the pager. Tells the pager where to get the fragments on
    // navigation.


    class ViewAdapter extends FragmentStateAdapter{

        public ViewAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment returningFragment;
            switch(position) {
                case 1:
                    // pass down the needed information into the fragment
                    returningFragment = new ViewHabitImageFragment(passedHabit.getRecordAddress());
                    Bundle imgBundle = new Bundle();
                    imgBundle.putSerializable("viewing", true);
                    returningFragment.setArguments(imgBundle);
                    break;
                case 2:
                    // pass down the needed information into the fragment
                    returningFragment = new ViewOtherRecordFragment(passedHabit, passedHabits);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("records", recordList);
                    bundle.putSerializable("viewing",true);
                    returningFragment.setArguments(bundle);
                    break;
                default:
                    // pass down the needed information into the fragment
                    returningFragment = new ViewHabitBaseFragment(passedHabit.getName(),passedHabit.getDescription(),passedHabit.getStartDate(),
                            passedHabit.getMondayR(),passedHabit.getTuesdayR(),passedHabit.getWednesdayR(), passedHabit.getThursdayR(),
                            passedHabit.getFridayR(),passedHabit.getSaturdayR(),passedHabit.getSundayR(),passedHabit.getVisibility());
                    Bundle viewBundle = new Bundle();
                    viewBundle.putSerializable("viewing",true);
                    returningFragment.setArguments(viewBundle);
            }
            return returningFragment; }



        @Override
        public int getItemCount() {
            return 3;
        }
    }

}


