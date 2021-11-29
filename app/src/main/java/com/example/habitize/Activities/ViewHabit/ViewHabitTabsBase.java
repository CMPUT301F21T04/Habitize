package com.example.habitize.Activities.ViewHabit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;
import com.example.habitize.Structural.Record;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private boolean mViewing;
    private boolean editable;
    private TextView percentValue;
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
        percentValue = findViewById(R.id.percentValue);
        editable = false;
        passedHabit = (Habit)getIntent().getExtras().getSerializable("habit"); // a user
        passedHabits = new ArrayList<>(); // we will get the latest list from the database
        passedIndex = (int)getIntent().getExtras().getSerializable("index");
        mViewing = (boolean)getIntent().getExtras().getSerializable("viewing");


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
        if(mViewing){
            AllowEdit.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
            ConfirmEdit.setVisibility(View.INVISIBLE);
        }

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
                            boolean visible = baseFrag.getVisible();
                            passedHabits.remove(passedIndex); // remove the habit at the position we are on
                            // hash the list and replace the one at the database

                            Habit newHabit = new Habit(title, description,
                                    startDate, monRec, tueRec, wedRec,
                                    thurRec, friRec, satRec, sunRec,new ArrayList<Record>(),passedHabit.getRecordAddress(),visible);
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
                // clear the image associated with the habit in the database
                DatabaseManager.deleteImage(passedHabits.get(passedIndex).getRecordAddress());
                passedHabits.remove(passedIndex); // remove the habit at the position we are on
                // hash the list and replace the one at the database
                DatabaseManager.updateHabits(passedHabits);
                finish();

            }
        });

        passedHabits = (ArrayList<Habit>) getIntent().getSerializableExtra("habits");

        double currentStreak = passedHabits.get(passedIndex).getStreak();
        //the amount of the time the user was supposed to perform record
        double fullStreak = passedHabits.get(passedIndex).computeRecurrence().size();
        double percentageNumber = (currentStreak/fullStreak)* 100;
        int finalPercentageNumber = (int) percentageNumber;
        percentValue.setText("Completed " + String.valueOf(finalPercentageNumber) + "%");
        //System.out.println("DID IT WORKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK " +"percentageNumber "+ finalPercentageNumber+" currentStreak "+currentStreak+" fullStreak "+fullStreak);

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
                    Bundle imgBundle = new Bundle();
                    imgBundle.putSerializable("viewing",mViewing);
                    returningFragment.setArguments(imgBundle);
                    break;
                case 2:
                    returningFragment = new ViewRecordsFragment(passedHabit,passedHabits);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("records",recordList);
                    bundle.putSerializable("viewing",mViewing);
                    returningFragment.setArguments(bundle);
                    break;
                default:
                    // on creation, our passed habit fills in the fragment's information fields
                    returningFragment = new ViewHabitBaseFragment(passedHabit.getName(),passedHabit.getDescription(),passedHabit.getStartDate(),
                            passedHabit.getMondayR(),passedHabit.getTuesdayR(),passedHabit.getWednesdayR(), passedHabit.getThursdayR(),
                            passedHabit.getFridayR(),passedHabit.getSaturdayR(),passedHabit.getSundayR(),passedHabit.getVisibility());
                    Bundle viewBundle = new Bundle();
                    viewBundle.putSerializable("viewing",mViewing);
                    returningFragment.setArguments(viewBundle);
            }
            return returningFragment; }



        @Override
        public int getItemCount() {
            return 3;
        } // why 3
    }

}


