package com.example.habitize.Activities.ViewOther;

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

import com.example.habitize.Activities.ViewHabit.ViewHabitBaseFragment;
import com.example.habitize.Activities.ViewHabit.ViewHabitImageFragment;
import com.example.habitize.Activities.ViewHabit.ViewRecordsFragment;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;
import com.example.habitize.Structural.Record;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewOtherHabitTabsBase extends AppCompatActivity {
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
        mViewing = (boolean)getIntent().getExtras().getSerializable("viewing");


        recordList = new ArrayList<>();
        // pulling the most recent habits

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
            switch(position){
                case 1: // TODO: This must be reorganized
                    // on creation, our passed habit fills in the fragment's information fields
                    returningFragment = new ViewHabitImageFragment(passedHabit.getRecordAddress());
                    Bundle imgBundle = new Bundle();
                    imgBundle.putSerializable("viewing",true);
                    returningFragment.setArguments(imgBundle);
                    break;
                case 2:
                    returningFragment = new ViewRecordsFragment(passedHabit,passedHabits);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("records",recordList);
                    bundle.putSerializable("viewing",true);
                    returningFragment.setArguments(bundle);
                    break;
                default:
                    // on creation, our passed habit fills in the fragment's information fields
                    returningFragment = new ViewHabitBaseFragment(passedHabit.getName(),passedHabit.getDescription(),passedHabit.getStartDate(),
                            passedHabit.getMondayR(),passedHabit.getTuesdayR(),passedHabit.getWednesdayR(), passedHabit.getThursdayR(),
                            passedHabit.getFridayR(),passedHabit.getSaturdayR(),passedHabit.getSundayR());
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


