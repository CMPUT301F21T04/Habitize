package com.example.habitize;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class FollowingActivity extends AppCompatActivity {
    private CustomListOfExistingFollowers CustomListOfExistingFollowersAdapter;
    FirebaseFirestore fStore;
    public static final String USER_INPUT_SEARCH = "com.example.habitize.USER_INPUT_SEARCH";
    private ListView listView;
    private Button followRequestsButton;
    private FloatingActionButton searchButton;
    private EditText userSearchInputEditText;
    private String userSearchInput;

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
        searchButton = findViewById(R.id.searchButton);
        userSearchInputEditText = findViewById(R.id.editTextTextPersonName);

        //Query every document in the collectionReference to obtain each existing "userName" field.
        //Append the existingFollowersDataList with new "userName" string values.
        //Pass the existingFollowersDataList into CustomListOfExistingFollowersAdapter.
        //Set adapter & render each list item in the custom layout file: "listOfExistingFollowers".
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        existingFollowersDataList.add(document.getString("userName"));
                        CustomListOfExistingFollowersAdapter = new CustomListOfExistingFollowers(FollowingActivity.this, existingFollowersDataList);
                        listView.setAdapter(CustomListOfExistingFollowersAdapter);
                    }
                }
            }
        });

        followRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFollowRequestsActivity();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userSearchInput = userSearchInputEditText.getText().toString();
                openSearchResultsActivity();
            }
        });


    }
    public void openFollowRequestsActivity() {
        Intent intent = new Intent(this, FollowRequests.class);
        startActivity(intent);
    }

    public void openSearchResultsActivity() {
        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra(USER_INPUT_SEARCH, userSearchInput);
        startActivity(intent);
    }
}