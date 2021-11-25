package com.example.habitize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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