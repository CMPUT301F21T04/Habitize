package com.example.habitize;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;

import java.util.List;

/**
 * Will populate usernames of requested followers to requestedFollowerName TextView
 * inside of activity_custom_list_of_follow_requests.xml UI.
 * */
public class CustomListOfFollowRequests extends ArrayAdapter<String> {

    private final List<String> followersThatRequested;
    private final Context context;
    private Button unfollowButton;
    private FloatingActionButton deleteFollowRequest;
    private FloatingActionButton acceptFollowRequest;

    /**
     * Initializes list of requested follower usernames and context variables.
     * @param context the context to which the requested followers will be populated.
     * @param followersThatRequested is the list of requested follower usernames as Strings.
     * */
    public CustomListOfFollowRequests(Context context, List<String> followersThatRequested){
        super(context,0, followersThatRequested);
        this.followersThatRequested = followersThatRequested;
        this.context = context;
    }

    /**
     * Sets requested follower usernames as the name field for requested followers
     * inside of custom list.
     * @param position the integer value of a particular follower username
     * inside of followersThatRequested list.
     * @param convertView If possible this is the old view to reuse.
     * @param parent parent of the View to be added.
     * */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_of_follow_requests,parent,false);
        }

        deleteFollowRequest = view.findViewById(R.id.deleteFollowRequestButton);
        acceptFollowRequest = view.findViewById(R.id.acceptFollowRequestButton);



        String requestedFollower = followersThatRequested.get(position);
        TextView nameField = view.findViewById(R.id.requestedFollowerName);

        nameField.setText(requestedFollower);
        unfollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        nameField.setClickable(false);
        deleteFollowRequest.setFocusable(false);
        deleteFollowRequest.setFocusableInTouchMode(false);
        acceptFollowRequest.setFocusable(false);
        acceptFollowRequest.setFocusableInTouchMode(false);

        return view;


    }

}
