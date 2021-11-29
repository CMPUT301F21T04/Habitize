package com.example.habitize.Activities.Followers;

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

/**
 * FollowingActivity gets launched when user clicks Following button on MainActivity.
 * Populates activity_following.xml UI with data retrieved from firestore.
 * Calls the CustomListOfExistingFollowers adapter to render requested follower usernames
 * as custom list items.
 * */
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

    /**
     * Launches FollowRequests activity when the user clicks the followRequests button from
     * FollowingActivity. */
    public void openFollowRequestsActivity() {
        Intent intent = new Intent(this, FollowRequests.class);
        startActivity(intent);
    }

    /**
     * Launches SearchResults activity when the user performs a search by clicking the
     * search floating action button from FollowingActivity. */
    public void openSearchResultsActivity() {
        Intent intent = new Intent(this, SearchResults.class);
        intent.putExtra(USER_INPUT_SEARCH, userSearchInput);
        startActivity(intent);
    }

    /**
     * Allows screen to gain a "cancelled" toast message when they have an empty intentResult */
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
                // if the intentResult is not null
                // we send a request to the owner of the QR code

                DatabaseManager.requestFollow(intentResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }





}