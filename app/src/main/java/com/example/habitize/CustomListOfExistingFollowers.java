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

public class CustomListOfExistingFollowers extends ArrayAdapter<String> {

    private final List<String> followers;
    private final Context context;

    // TODO: Add more fields here. Image..etc


    public CustomListOfExistingFollowers(Context context, List<String> followers){
        super(context,0, followers);
        this.followers = followers;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_of_existing_followers,parent,false);
        }

        String follower = followers.get(position);
        TextView nameField = view.findViewById(R.id.existingFollowerName);

        nameField.setText(follower);


        return view;


    }

}
