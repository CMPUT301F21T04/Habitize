package com.example.habitize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private ListView listView;
    FirebaseFirestore fStore;

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

        //Query every document in the collectionReference to obtain each existing "userName" field.
        //Append the existingFollowersDataList with new "userName" string values.
        //Pass the existingFollowersDataList into CustomListOfExistingFollowersAdapter.
        //Set adapter & render each list item in the custom layout file: "listOfExistingFollowers".
        //This code will store the data from firebase to an array for use in local code.
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

        //TODO: implement this functionality at a later date.
        listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });

    }
}