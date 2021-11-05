package com.example.habitize;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class FollowingActivity extends AppCompatActivity {
    private CustomListOfExistingFollowers CustomListOfExistingFollowersAdapter;
    FirebaseFirestore fStore;
    private ListView listView;
    private Button followRequestsButton;
    private ArrayList<String> friendList;
    private CustomListOfExistingFollowers mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        //Initialize Firestore database and assign to fStore.
        //Set collectionReference to "Users" until "Followers" collection is implemented.
        //Initialize existingFollowersDataList to append follower userNames to as strings.
        //Assign XML List View to local variable listView.
        fStore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = fStore.collection("Users");
        List<String> existingFollowersDataList = new ArrayList<>();
        listView = findViewById(R.id.listOfExistingFollowers);
        followRequestsButton = findViewById(R.id.followingReq);
        friendList = new ArrayList<>();
        mAdapter = new CustomListOfExistingFollowers(this,friendList);
        listView.setAdapter(mAdapter);
        //Query every document in the collectionReference to obtain each existing "userName" field.
        //Append the existingFollowersDataList with new "userName" string values.
        //Pass the existingFollowersDataList into CustomListOfExistingFollowersAdapter.
        //Set adapter & render each list item in the custom layout file: "listOfExistingFollowers".
        DatabaseManager.getFriends(friendList,mAdapter);

        followRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFollowRequestsActivity();
            }
        });
    }
    public void openFollowRequestsActivity() {
        Intent intent = new Intent(this, FollowRequests.class);
        startActivity(intent);
    }
}