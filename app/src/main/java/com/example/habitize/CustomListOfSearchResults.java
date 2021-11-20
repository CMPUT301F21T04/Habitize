package com.example.habitize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomListOfSearchResults extends ArrayAdapter<String> {

    private final List<String> users;
    private final Context context;

    public CustomListOfSearchResults(Context context, List<String> users){
        super(context,0, users);
        this.users = users;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_of_search_results,parent,false);
        }

        String user = users.get(position);
        TextView nameField = view.findViewById(R.id.userName);

        nameField.setText(user);


        return view;


    }

}