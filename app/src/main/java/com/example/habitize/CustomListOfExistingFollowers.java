package com.example.habitize;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/**
 * Will populate usernames of existing followers to existingFollowerName TextView
 * inside of activity_custom_list_of_existing_followers.xml UI.
 * */
public class CustomListOfExistingFollowers extends ArrayAdapter<String> {

    private final List<String> followers;
    private final Context context;
    TextView tv;

    // TODO: Add more fields here. Image..etc


    /**
     * Initializes list of existing follower usernames and context variables.
     * @param context the context to which the followers will be populated.
     * @param followers is the list of existing follower usernames as Strings.
     * */
    public CustomListOfExistingFollowers(Context context, List<String> followers){
        super(context,0, followers);
        this.followers = followers;
        this.context = context;
    }

    /**
     * Sets existing follower usernames as the name field for existing followers
     * inside of custom list.
     * @param position the integer value of a particular existing follower username
     * inside of followers list.
     * @param convertView If possible this is the old view to reuse.
     * @param parent parent of the View to be added.
     * */
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


//    public void usersPublicHabits(View view) {
//        Intent intent = new Intent(this, PublicHabitList.class);
//    }

}
