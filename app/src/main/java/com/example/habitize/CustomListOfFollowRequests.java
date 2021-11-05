package com.example.habitize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;

import java.util.List;

public class CustomListOfFollowRequests extends ArrayAdapter<String> {

    private final List<String> followersThatRequested;
    private final Context context;

    // TODO: Add more fields here. Image..etc

    public CustomListOfFollowRequests(Context context, List<String> followersThatRequested){
        super(context,0, followersThatRequested);
        this.followersThatRequested = followersThatRequested;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_of_follow_requests,parent,false);
        }

        String requestedFollower = followersThatRequested.get(position);
        TextView nameField = view.findViewById(R.id.requestedFollowerName);

        nameField.setText(requestedFollower);

        return view;


    }

}
