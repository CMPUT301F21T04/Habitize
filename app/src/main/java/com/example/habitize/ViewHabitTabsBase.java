package com.example.habitize;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewHabitTabsBase extends AppCompatActivity {
    private ViewPager2 pager;
    private TabLayout tabLayout;

    private Button editButton;
    private String passedUser;
    private Habit passedHabit; // habit to view
    private FirebaseFirestore db;
    private CollectionReference userCol;
    private DocumentReference docRef;
    private ArrayList<Habit> passedHabits;
    private Button deleteButton;
    private int passedIndex;
    private boolean editable;
    String[] titles = {"INFO","IMAGE","RECORDS"};

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_tabs);
        pager = findViewById(R.id.viewPager);
        editButton = findViewById(R.id.edit_habit_tabs);
        deleteButton = findViewById(R.id.delete_button_tabs);
        editable = false;
        passedUser = (String)getIntent().getExtras().getSerializable("User"); // we get passed a habit
        passedHabit = (Habit)getIntent().getExtras().getSerializable("habit"); // a user
        passedHabits = new ArrayList<>(); // we will get the latest list from the database

        // If we choose to modify our habit, we will modify the entire list and push it into the database
        db = FirebaseFirestore.getInstance(); // document references
        userCol = db.collection("Users");
        docRef = userCol.document(passedUser);

        // pulling the most recent habits
        DatabaseManager.getAllHabits(passedHabits);

        // pager holds fragments, madapter is the adapter needed for it
        ViewAdapter mAdapter = new ViewAdapter(this);
        pager.setOffscreenPageLimit(8);
        pager.setAdapter(mAdapter);
        tabLayout = findViewById(R.id.ViewHabitTabs);

        // connects the tablayout to the pager navigation. Now they are synced on swipes
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // update the list here.
                ViewHabitBaseFragment baseFrag = (ViewHabitBaseFragment) getSupportFragmentManager().findFragmentByTag("f0");
                ViewHabitImageFragment imgFrag = (ViewHabitImageFragment) getSupportFragmentManager().findFragmentByTag("f1");
                if(!editable) {
                    imgFrag.setEditable();
                    baseFrag.setEditable();
                    editable = true;
                }
                else{
                    baseFrag.setNotEditable();
                    imgFrag.setNotEditable();
                    editable = false;

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
                case 1:
                    // on creation, our passed habit fills in the fragment's information fields
                    returningFragment = new ViewHabitImageFragment();
                    break;
                case 2:
                    returningFragment = new ViewRecordsFragment();
                    break;
                default:
                    // on creation, our passed habit fills in the fragment's information fields
                    returningFragment = new ViewHabitBaseFragment(passedHabit.getName(),passedHabit.getDescription(),passedHabit.getStartDate(),
                            passedHabit.getMondayR(),passedHabit.getTuesdayR(),passedHabit.getWednesdayR(), passedHabit.getThursdayR(),
                            passedHabit.getFridayR(),passedHabit.getSaturdayR(),passedHabit.getSundayR());
            }
            return returningFragment; }


        @Override
        public int getItemCount() {
            return 3;
        }
    }

}
