package com.example.habitize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddHabitTabsBase extends AppCompatActivity {
    private ViewPager2 pager;
    private TabLayout tabLayout;

    private Button createButton;
    private AddHabitBaseFragment addHabitBaseFragment;
    private AddHabitImageFragment addHabitImageFragment;
    private AddHabitLocationFragment addHabitLocation;
    private String passedUser;
    private FirebaseFirestore db;
    private CollectionReference userCol;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_tabs_base);
        pager = findViewById(R.id.viewPager);
        createButton = findViewById(R.id.create_habit_tabs);
        //passedUser = (String)getIntent().getExtras().getSerializable("User"); // retrieving passed user
        //db = FirebaseFirestore.getInstance(); // document references
        //userCol = db.collection("Users");
        //docRef = userCol.document(passedUser);

        addAdapter mAdapter = new addAdapter(this);
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
                AddHabitBaseFragment addFrag = (AddHabitBaseFragment)getSupportFragmentManager().findFragmentByTag("f0");
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

                Habit newHabit = new Habit(title,description,startDate,monRec,tueRec,wedRec,thurRec,friRec,satRec,sunRec);
                AddHabitImageFragment imageFrag = (AddHabitImageFragment)getSupportFragmentManager().findFragmentByTag("f1");
                Uri image = imageFrag.getImage();
                System.out.println("Hello");

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