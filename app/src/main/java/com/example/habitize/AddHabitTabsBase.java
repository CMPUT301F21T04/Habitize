package com.example.habitize;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddHabitTabsBase extends AppCompatActivity {
    private ViewPager2 pager;
    private TabLayout tabLayout;

    private Button createButton;
    private String passedUser;
    private FirebaseFirestore db;
    private CollectionReference userCol;
    private DocumentReference docRef;
    private List<Habit> passedHabits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_tabs_base);
        pager = findViewById(R.id.viewPager);
        createButton = findViewById(R.id.create_habit_tabs);

        passedUser = (String)getIntent().getExtras().getSerializable("User"); // retrieving passed user
        passedHabits = new ArrayList<>();

        db = FirebaseFirestore.getInstance(); // document references
        userCol = db.collection("Users");
        docRef = userCol.document(passedUser);

        //We pull the current habit list, modify it, and send it back (only if we create the habit)
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                passedHabits.clear(); // reset the list
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                for(int i = 0; i < mappedList.size() ; i++){ // get each item one by one
                    Map<String,Object> habitFields = (Map<String, Object>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = (String) habitFields.get("name");
                    String description = (String) habitFields.get("description");
                    String date = (String) habitFields.get("startDate");
                    boolean mondayRec = (boolean) habitFields.get("mondayR");
                    boolean tuesdayRec = (boolean) habitFields.get("tuesdayR");
                    boolean wednesdayRec = (boolean) habitFields.get("wednesdayR");
                    boolean thursdayRec = (boolean) habitFields.get("thursdayR");
                    boolean fridayRec = (boolean) habitFields.get("fridayR");
                    boolean saturdayRec = (boolean) habitFields.get("saturdayR");
                    boolean sundayRec = (boolean) habitFields.get("sundayR");

                    Habit newHabit = new Habit(name,description, date, mondayRec, tuesdayRec, wednesdayRec,
                            thursdayRec, fridayRec, saturdayRec, sundayRec); // create a new habit out of this information
                    passedHabits.add(newHabit); // add it to the habitList
                }
            }
        });


        // pager holds fragments, madapter is the adapter needed for it
        addAdapter mAdapter = new addAdapter(this);
        pager.setOffscreenPageLimit(8); // forcing pager to create fragments
        pager.setAdapter(mAdapter);
        tabLayout = findViewById(R.id.AddHabitTabs);
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText("Tab");
                tab.setIcon(R.drawable.ic_baseline_check_24);
            }
        }).attach();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // This block loads the fragments and gets our necessary fields for checking
                AddHabitBaseFragment addFrag = (AddHabitBaseFragment)getSupportFragmentManager().findFragmentByTag("f0");
                AddHabitImageFragment addImage = (AddHabitImageFragment)getSupportFragmentManager().findFragmentByTag("f1");
                Uri img = addImage.getImage();
                String title = addFrag.getTitle();
                String description = addFrag.getDescription();
                String startDate = addFrag.getDate();
                boolean monRec = addFrag.getMon();
                boolean tueRec = addFrag.getTue();
                boolean wedRec = addFrag.getWed();
                boolean thurRec = addFrag.getThur();
                boolean friRec = addFrag.getFri();
                boolean satRec = addFrag.getSat();
                boolean sunRec = addFrag.getSun();



                // TODO: this is not finished yet. need to check more fields
                //check if empty and user left fields blank
                if(title == ""){
                    Toast.makeText(AddHabitTabsBase.this,"Enter a habit title",Toast.LENGTH_LONG).show();
                }

                if(description == ""){
                    Toast.makeText(AddHabitTabsBase.this,"Enter a habit description",Toast.LENGTH_LONG).show();
                }

                if(startDate == ""){
                    Toast.makeText(AddHabitTabsBase.this,"Enter a habit start date",Toast.LENGTH_LONG).show();
                }

                //make sure title is up to 20 chars
                if (title.length() > 20){
                    Toast.makeText(AddHabitTabsBase.this,"title length too long",Toast.LENGTH_LONG).show();
                }

                //make sure habit description is up to 30 chars
                if (description.length() > 30){
                    Toast.makeText(AddHabitTabsBase.this,"Enter a habit title",Toast.LENGTH_LONG).show();

                }


                //creates the habit and stores in database only if validation above is correct
                if (!(title == "") && (!(description == "")) &&
                        (!(startDate == ""))) {
                    // Create the habit
                    Habit newHabit = new Habit(title, description,
                            startDate, monRec, tueRec, wedRec,
                            thurRec, friRec, satRec, sunRec);
                    // add it to the user list
                    passedHabits.add(newHabit);
                    // Hash it for transportation to database
                    HashMap<String, Object> listMap = new HashMap<>();
                    listMap.put("habits", passedHabits);
                    // send to database and close
                    docRef.update(listMap);
                    finish();
                }

            }
        });


    }

    class addAdapter extends FragmentStateAdapter{

        public addAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment returningFragment;
            switch(position){
                case 1: returningFragment = new AddHabitImageFragment();
                    break;
                case 2: returningFragment = new AddHabitLocationFragment();
                    break;
                default:
                    returningFragment = new AddHabitBaseFragment();
            }
            return returningFragment; }


        @Override
        public int getItemCount() {
            return 3;
        }
    }
}