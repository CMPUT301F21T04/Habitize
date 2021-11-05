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
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class FollowRequests extends AppCompatActivity {
    private CustomListOfFollowRequests CustomListOfRequestedFollowersAdapter;
    FirebaseFirestore fStore;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_requests);

        fStore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = fStore.collection("Users");
        List<String> followRequestsDataList = new ArrayList<>();
        listView = findViewById(R.id.listOfFollowRequests);


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