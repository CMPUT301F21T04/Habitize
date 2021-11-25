package com.example.habitize.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.habitize.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Leaderboard extends AppCompatActivity {

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String currentLoggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);


    /*
    go through users documents and for each one, find their points collection
    compare their points to every user. Find algorithm to do that. Then whoever has
    the most points, will be ordered from 1st to last. populate
    listview with the leaderboard
     */

//        fStore = FirebaseFirestore.getInstance();
//        fAuth = FirebaseAuth.getInstance();
//        CollectionReference collectionReference = fStore.collection("Users");
//
//        Query queryToFindUsersPoints =  collectionReference.whereEqualTo("userName", true);
//
//        queryToFindUsersPoints.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()) {
//
//                    //for every document find the ones that match query
//                    for(QueryDocumentSnapshot document : task.getResult()) {
//
//                    }
//                } else{
//
//                }
//            }
//        });
    }
}