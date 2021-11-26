package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewHabitTabsBase extends AppCompatActivity {
    private ViewPager2 pager;
    private TabLayout tabLayout;

    private Button ConfirmEdit;
    private Switch AllowEdit;
    private String passedUser;
    private Habit passedHabit; // habit to view
    private FirebaseFirestore db;
    private CollectionReference userCol;
    private DocumentReference docRef;
    private ArrayList<Habit> passedHabits;
    private Button deleteButton;
    private int passedIndex;
    private boolean editable;
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
        editable = false;
        passedHabit = (Habit)getIntent().getExtras().getSerializable("habit"); // a user
        passedHabits = new ArrayList<>(); // we will get the latest list from the database
        passedIndex = (int)getIntent().getExtras().getSerializable("index");



        recordList = new ArrayList<>();
        // pulling the most recent habits
        DatabaseManager.getAllHabits(passedHabits);

        // pager holds fragments, madapter is the adapter needed for it
        ViewAdapter mAdapter = new ViewAdapter(this);
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


        //edit toggle button which
        AllowEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ViewHabitBaseFragment baseFrag = (ViewHabitBaseFragment) getSupportFragmentManager().findFragmentByTag("f0");
                ViewHabitImageFragment imgFrag = (ViewHabitImageFragment) getSupportFragmentManager().findFragmentByTag("f1");
                if (isChecked) {
                    //make input boxes editable
                    imgFrag.setEditable();
                    baseFrag.setEditable();
                    //confirm button to to update new data in database
                    ConfirmEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Init updated  Habit variables
                            String title = baseFrag.getTitle();
                            String description = baseFrag.getDescription();
                            String startDate = baseFrag.getDate();
                            boolean monRec = baseFrag.getMon();
                            boolean tueRec = baseFrag.getTue();
                            boolean wedRec = baseFrag.getWed();
                            boolean thurRec = baseFrag.getThur();
                            boolean friRec = baseFrag.getFri();
                            boolean satRec = baseFrag.getSat();
                            boolean sunRec = baseFrag.getSun();
                            passedHabits.remove(passedIndex); // remove the habit at the position we are on
                            // hash the list and replace the one at the database

                            Habit newHabit = new Habit(title, description,
                                    startDate, monRec, tueRec, wedRec,
                                    thurRec, friRec, satRec, sunRec,new ArrayList<Record>(),passedHabit.getRecordAddress(),true);
                            // add it to the user list
                            passedHabits.add(newHabit);
                            DatabaseManager.storeImage(imgFrag.getImageBytes(),newHabit.getRecordAddress());
                            DatabaseManager.updateHabits(passedHabits);
                            finish();
                        }
                    });
                    // The toggle is disabled
                } else {
                    // The toggle is disabled
                    baseFrag.setNotEditable();
                    imgFrag.setNotEditable();

                }
            }
        });

        // ask user to confirm delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passedHabits.remove(passedIndex); // remove the habit at the position we are on
                // hash the list and replace the one at the database
                DatabaseManager.updateHabits(passedHabits);
                finish();

            }
        });
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
            switch(position){
                case 1: // TODO: This must be reorganized
                    // on creation, our passed habit fills in the fragment's information fields
                    returningFragment = new ViewHabitImageFragment(passedHabit.getRecordAddress());
                    break;
                case 2:
                    returningFragment = new ViewRecordsFragment(passedHabit,passedHabits);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("records",recordList);
                    returningFragment.setArguments(bundle);
                    break;
                default:
                    // on creation, our passed habit fills in the fragment's information fields
                    returningFragment = new ViewHabitBaseFragment(passedHabit.getName(),passedHabit.getDescription(),passedHabit.getStartDate(),
                            passedHabit.getMondayR(),passedHabit.getTuesdayR(),passedHabit.getWednesdayR(), passedHabit.getThursdayR(),
                            passedHabit.getFridayR(),passedHabit.getSaturdayR(),passedHabit.getSundayR(), passedHabit.getTotalComplete(), passedHabit.getTotalDays());
            }
            return returningFragment; }



        @Override
        public int getItemCount() {
            return 3;
        }
    }

}


