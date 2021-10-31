package com.example.habitize;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AddHabitBase extends Fragment {
    private String title;
    private int ViewId;

    public AddHabitBase(int ViewId) {
        // Required empty public constructor
        this.ViewId = ViewId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(ViewId, container, false);
        return root;
    }
}