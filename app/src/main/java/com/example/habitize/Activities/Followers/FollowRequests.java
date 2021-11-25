package com.example.habitize.Activities.Followers;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.habitize.Controllers.CustomListOfFollowRequests;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.R;

import java.util.ArrayList;

/**
 * FollowRequests gets launched when user clicks followRequestsButton on FollowingActivity.
 * Populates activity_follow_requests.xml UI with data retrieved from firestore.
 * Calls the CustomListOfFollowRequests adapter to render requested follower usernames
 * as custom list items.
 * */
public class FollowRequests extends AppCompatActivity {
    private CustomListOfFollowRequests CustomListOfRequestedFollowersAdapter;
    private ArrayList<String> followers;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);

        //Initialize Firestore database and assign to fStore.
        //Initialize Firestore auth and assign to fAuth.
        //Obtain currentUser which is the logged-in application user's unique email address.
        //Set collectionReference to "followers".
        //Initialize followRequestsDataList to append follower userNames to as strings.
        //Assign XML List View to local variable listView.
        followers = new ArrayList<String>();
        CustomListOfRequestedFollowersAdapter = new CustomListOfFollowRequests(this,followers);
        listView = findViewById(R.id.listOfFollowRequests);
        listView.setAdapter(CustomListOfRequestedFollowersAdapter);
        DatabaseManager.getFollowers(followers, CustomListOfRequestedFollowersAdapter);
        //Get the currentUser's document from the followers collection.
        //If the document exists, create a list for the requestedToFollowMe array of users.
        //Pass the list into CustomListOfRequestedFollowersAdapter.
        //Set adapter & render each list item in the custom layout file: "listOfFollowRequests".
    }
}