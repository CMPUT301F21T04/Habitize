package com.example.habitize.Activities.Followers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.habitize.Controllers.CustomListOfExistingFollowers;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class FollowingActivity extends AppCompatActivity {
    private CustomListOfExistingFollowers CustomListOfExistingFollowersAdapter;
    FirebaseFirestore fStore;
    public static final String USER_INPUT_SEARCH = "com.example.habitize.USER_INPUT_SEARCH";
    private ListView listView;
    private Button followRequestsButton;
    private FloatingActionButton searchButton;
    private EditText userSearchInputEditText;
    private String userSearchInput;
    private ArrayList<String> existingFollowers;
    private Button addViaQrBtn;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        //Initialize Firestore database and assign to fStore.
        //Set collectionReference to "Users" until "Followers" collection is implemented.
        //Initialize existingFollowersDataList to append follower userNames to as strings.
        //Assign XML List View to local variable listView.
        fStore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = fStore.collection("Users");
        ArrayList<String> existingFollowersDataList = new ArrayList<>();
        followRequestsButton = findViewById(R.id.followingReq);
        searchButton = findViewById(R.id.searchButton);
        userSearchInputEditText = findViewById(R.id.editTextTextPersonName);
        addViaQrBtn = findViewById(R.id.openCameraButton);
        //Query every document in the collectionReference to obtain each existing "userName" field.
        //Append the existingFollowersDataList with new "userName" string values.
        //Pass the existingFollowersDataList into CustomListOfExistingFollowersAdapter.
        //Set adapter & render each list item in the custom layout file: "listOfExistingFollowers".
        existingFollowers = new ArrayList<String>();
        CustomListOfExistingFollowersAdapter = new CustomListOfExistingFollowers(this,existingFollowers);
        listView = findViewById(R.id.listOfExistingFollowers);
        listView.setAdapter(CustomListOfExistingFollowersAdapter);
        DatabaseManager.getFollowing(existingFollowers, CustomListOfExistingFollowersAdapter);


        addViaQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(FollowingActivity.this);
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                System.out.println(intentResult.getContents());
                DatabaseManager.requestFollow(intentResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }





}