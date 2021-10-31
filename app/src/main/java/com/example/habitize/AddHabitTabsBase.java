package com.example.habitize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class AddHabitTabsBase extends AppCompatActivity {
    private ViewPager2 pager;
    private int[] views = {R.layout.fragment_add_habit_base,R.layout.fragment_add_habit_image,R.layout.fragment_add_habit_geolocation};
    private TabLayout tabLayout;


    private AddHabitBaseFragment addHabitBaseFragment;
    private AddHabitImageFragment addHabitImageFragment;
    private AddHabitLocationFragment addHabitLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_tabs_base);
        pager = findViewById(R.id.viewPager);
        pager.setAdapter(
                new addAdapter(this)
        );
        tabLayout = findViewById(R.id.AddHabitTabs);
        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText("Tab");
                tab.setIcon(R.drawable.ic_baseline_check_24);
            }
        }).attach();




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
            return returningFragment;
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}