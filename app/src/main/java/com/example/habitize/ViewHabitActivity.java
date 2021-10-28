package com.example.habitize;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class ViewHabitActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_info);
    }
}
