package com.example.habitize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

/**
 * Will populate usernames of requested followers to requestedFollowerName TextView
 * inside of activity_custom_list_of_follow_requests.xml UI.
 * */
public class CustomListOfFollowRequests extends ArrayAdapter<String> {

    private final List<String> followersThatRequested;
    private final Context context;
    private FloatingActionButton deleteFollowRequest;
    private FloatingActionButton acceptFollowRequest;
    TextView nameField;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String currentLoggedInUser;

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
        nameField = view.findViewById(R.id.requestedFollowerName);

        String requestedFollower = followersThatRequested.get(position);
        nameField.setText(requestedFollower);
        nameField.setClickable(false);
        deleteFollowRequest.setFocusable(false);
        deleteFollowRequest.setFocusableInTouchMode(false);
        acceptFollowRequest.setFocusable(false);
        acceptFollowRequest.setFocusableInTouchMode(false);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        CollectionReference collectionReference = fStore.collection("Users");
        Query currentUserDocQuery = collectionReference.whereEqualTo("email", fAuth.getCurrentUser().getEmail());


        deleteFollowRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserDocQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                currentLoggedInUser = document.getString("userName");
                                collectionReference.document(currentLoggedInUser).update("followers", FieldValue.arrayRemove(requestedFollower));
                            }
                        }
                    }
                });
            }
        });

        acceptFollowRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUserDocQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                currentLoggedInUser = document.getString("userName");
                                collectionReference.document(currentLoggedInUser).update("followers", FieldValue.arrayRemove(requestedFollower));
                                collectionReference.document(currentLoggedInUser).update("following", FieldValue.arrayUnion(requestedFollower));
                            }
                        }
                    }
                });
            }
        });





        return view;


    }


}
