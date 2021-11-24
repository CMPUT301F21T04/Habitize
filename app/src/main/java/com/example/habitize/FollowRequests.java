package com.example.habitize;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

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