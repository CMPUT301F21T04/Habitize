package com.example.habitize;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
    String[] titles = {"INFO","IMAGE","RECORDS"};

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_habit_tabs);
        pager = findViewById(R.id.viewPager);
        editButton = findViewById(R.id.edit_habit_tabs);

        passedUser = (String)getIntent().getExtras().getSerializable("User");
        passedHabit = (Habit)getIntent().getExtras().getSerializable("habit");
        passedHabits = new ArrayList<>();

        db = FirebaseFirestore.getInstance(); // document references
        userCol = db.collection("Users");
        docRef = userCol.document(passedUser);

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
        ViewAdapter mAdapter = new ViewAdapter(this);
        pager.setOffscreenPageLimit(8);
        pager.setAdapter(mAdapter);
        tabLayout = findViewById(R.id.ViewHabitTabs);
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



    class ViewAdapter extends FragmentStateAdapter{

        public ViewAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment returningFragment;
            switch(position){
                case 1: returningFragment = new ViewHabitImageFragment();
                    break;
                default:
                    returningFragment = new ViewHabitBaseFragment();
            }
            return returningFragment; }


        @Override
        public int getItemCount() {
            return 3;
        }
    }

}
