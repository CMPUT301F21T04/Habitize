package com.example.habitize.Activities.AddHabit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.Controllers.ErrorShower;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.UUID;

public class AddHabitTabsBase extends AppCompatActivity implements ErrorShower.ErrorHandler {
    private ViewPager2 pager;
    private TabLayout tabLayout;
    private Button createButton;
    private ArrayList<Habit> passedHabits;
    private addAdapter mAdapter;
    String[] titles = {"Info","Image"};


    /**
     * Initialize activity
     * @param savedInstanceState the previous instance generated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_tabs_base);
        pager = findViewById(R.id.recordPager);
        createButton = findViewById(R.id.create_habit_tabs);
        ErrorShower shower = new ErrorShower(this);

        //to store habits
        passedHabits = new ArrayList<>();
        //We pull the current habit list, modify it, and send it back (only if we create the habit)
        DatabaseManager.getAllHabits(passedHabits);

        // pager holds fragments, madapter is the adapter needed for it
        mAdapter = new addAdapter(this);
        pager.setOffscreenPageLimit(8); // forcing pager to create fragments
        pager.setAdapter(mAdapter);
        tabLayout = findViewById(R.id.AddHabitTabs);

        //to make tabs to switch between
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();

        //listener for create button handles possible user errors and creates habit after clicked
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This block loads the fragments and gets our necessary fields for checking
                AddHabitBaseFragment addFrag = (AddHabitBaseFragment)getSupportFragmentManager().findFragmentByTag("f0");
                AddHabitImageFragment addImage = (AddHabitImageFragment)getSupportFragmentManager().findFragmentByTag("f1");
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

                //check if title empty
                if(title.equals("")){
                    shower.throwError(1);
                    return;
                }

                //check if description empty
                if(description.equals("")){
                    shower.throwError((2));
                    return;
                }

                //check if start date of habit is empty
                if(startDate.equals("")){
                    shower.throwError(3);
                    return;
                }

                //make sure title is up to 20 chars
                if (title.length() > 20){
                    shower.throwError(4);
                    return;
                }
                if(monRec == false && tueRec == false && wedRec == false && thurRec == false && friRec == false
                        && satRec == false && sunRec == false){
                    shower.throwError((5));
                    return;
                }
                //make sure habit description is up to 30 chars
                if (description.length() > 30){
                    shower.throwError(6);
                    return;
                }


                //creates the habit and stores in database only if validation above is correct
                if (!(title.equals("")) && (!(description.equals(""))) &&
                        (!(startDate.equals("")))) {
                    // Create the habit
                    Habit newHabit = new Habit(title, description,
                            startDate, monRec, tueRec, wedRec,
                            thurRec, friRec, satRec, sunRec,new ArrayList<>(),new UUID(20,10).randomUUID().toString()
                    ,true);
                    // add it to the user list
                    passedHabits.add(newHabit);
                    DatabaseManager.incrementPoints(1);
                    DatabaseManager.storeImage(addImage.getImageBytes(),newHabit.getRecordAddress());
                    DatabaseManager.updateHabits(passedHabits);
                    finish();
                }
            }
        });

    }

    /**
     * @param errorCode is the Java error which happens when the user tries to create a habit
     * without including a required field. From here we include a switch case to create a custom
     * error message.*/
    @Override
    public String getErrorMessage(int errorCode) {
        switch(errorCode){
            case 1:
                return "Habit requires a title";
            case 2:
                return "Habit requires a description";
            case 3:
                return "Habit requires a start date";
            case 4:
                return "Habit title should be less than 20 characters";
            case 5:
                return "Habit should occur atleast once a week";
            default:
                return "Habit description should be lass than 30 characters";
        }
    }


    /**
     * Makes an adapter to create the fragments for tabs
     * */
    class addAdapter extends FragmentStateAdapter{

        /**
         * makes adapter to create fragments
         * @param fragmentActivity
         */
        public addAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        /**
         * creating fragments at specific positions in the viewpager
         * @param position is the screen that the habit is going to go
         * @return returns the fragment depending on which screen was clicked on
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment returningFragment;
            switch(position){
                case 1: returningFragment = new AddHabitImageFragment();
                    break;
                default:
                    returningFragment = new AddHabitBaseFragment();
            }
            return returningFragment; }

        /**
         * gets the item count
         * @return number 2
         */
        @Override
        public int getItemCount() {
            return 2;
        }
    }
}