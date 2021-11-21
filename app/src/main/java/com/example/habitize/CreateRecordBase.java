package com.example.habitize;

import android.os.Bundle;
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

public class CreateRecordBase extends AppCompatActivity {
    private ViewPager2 pager;
    private TabLayout tabLayout;
    private Button createButton;
    private AddRecordAdapter mAdapter;
    String[] titles = {"Info","Image"};


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

        new TabLayoutMediator(tabLayout, pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


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
            }
            return returningFragment;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
