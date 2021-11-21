package com.example.habitize;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CreateRecordBase extends AppCompatActivity implements MapFragment.scrollDisabler {
    private ViewPager2 pager;
    private TabLayout tabLayout;
    private Button createButton;
    private AddRecordAdapter mAdapter;
    String[] titles = {"Info","Image","Location"};



    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_record);
        createButton = findViewById(R.id.createRecord);
        pager = findViewById(R.id.recordPager);
        tabLayout = findViewById(R.id.recordTabs);



        mAdapter = new AddRecordAdapter(this);
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

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // the maps listens constantly. we need to turn it off and on
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MapFragment fragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("f2");
                int pos = tab.getPosition();
                if(pos == 2){
                    fragment.enableMapScroll();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                MapFragment fragment = (MapFragment) getSupportFragmentManager().findFragmentByTag("f2");
                int pos = tab.getPosition();
                if(pos == 2){
                    fragment.enableMapScroll();
                }
            }
        });

    }

    @Override
    public void disableScroll() {
        pager.setUserInputEnabled(false);

    }

    @Override
    public void enableScroll() {
        pager.setUserInputEnabled(true);
    }


    class AddRecordAdapter extends FragmentStateAdapter{
        public AddRecordAdapter(@NonNull FragmentActivity fragmentActivity){super(fragmentActivity);}

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment returningFragment;
            switch(position){
                default: returningFragment = new RecordCreate();
                break;
                case 1: returningFragment = new AddHabitImageFragment();
                break;
                case 2: returningFragment = new MapFragment();
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
