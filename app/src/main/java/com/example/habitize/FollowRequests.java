package com.example.habitize;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class FollowRequests extends AppCompatActivity {
    private CustomListOfFollowRequests CustomListOfRequestedFollowersAdapter;
    FirebaseFirestore fStore;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);

        //Initialize Firestore database and assign to fStore.
        //Set collectionReference to "Users" until "FollowerRequests" collection is implemented.
        //Initialize followRequestsDataList to append follower userNames to as strings.
        //Assign XML List View to local variable listView.
        fStore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = fStore.collection("Users");
        List<String> followRequestsDataList = new ArrayList<>();
        listView = findViewById(R.id.listOfFollowRequests);

        //Query every document in the collectionReference to obtain each existing "userName" field.
        //Append the followRequestsDataList with new "userName" string values.
        //Pass the followRequestsDataList into CustomListOfRequestedFollowersAdapter.
        //Set adapter & render each list item in the custom layout file: "listOfFollowRequests".
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        followRequestsDataList.add(document.getString("userName"));
                        CustomListOfRequestedFollowersAdapter = new CustomListOfFollowRequests(FollowRequests.this, followRequestsDataList);
                        listView.setAdapter(CustomListOfRequestedFollowersAdapter);
                    }
                }
            }
        });
    }

}