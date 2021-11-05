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

public class FollowRequests extends AppCompatActivity {
    private CustomListOfFollowRequests CustomListOfRequestedFollowersAdapter;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String currentUser;
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
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        currentUser = fAuth.getCurrentUser().getEmail();
        CollectionReference collectionReference = fStore.collection("followers");
        List<String> followRequestsDataList = new ArrayList<>();
        listView = findViewById(R.id.listOfFollowRequests);

        //Get the currentUser's document from the followers collection.
        //If the document exists, create a list for the requestedToFollowMe array of users.
        //Pass the list into CustomListOfRequestedFollowersAdapter.
        //Set adapter & render each list item in the custom layout file: "listOfFollowRequests".
        collectionReference.document(currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        ArrayList<String> list = (ArrayList<String>) document.get("requestedToFollowMe");
                        CustomListOfRequestedFollowersAdapter = new CustomListOfFollowRequests(FollowRequests.this, list);
                        listView.setAdapter(CustomListOfRequestedFollowersAdapter);
                    }
                }
            }
        });
    }

}