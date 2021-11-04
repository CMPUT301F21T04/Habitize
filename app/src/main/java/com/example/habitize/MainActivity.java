package com.example.habitize;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.StructuredQuery;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button addHabit;
    private Button allHabits;
    private Button todaysHabits;
    private Button followReq;
    private Button logOut;
    private ProgressBar progressBar3;
    private TextView textView2;
    private Toolbar toolBar;

    private StructuredQuery.FieldReference fieldReference;
    public ArrayList<Habit> habitList;
    private int progressTrack = 0; //starting at 0 (max 100)
    private Integer completion = 0;
    private Integer totalHabits = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logOut = findViewById(R.id.logout);
        addHabit = findViewById(R.id.addHabit); // our 4 buttons
        allHabits = findViewById(R.id.allHabits);
        todaysHabits = findViewById(R.id.todaysHabits);
        followReq = (Button) findViewById(R.id.followReq);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3);



       // updateProgress();





        // branch to new activities here

        /**
         * When add habit button is clicked upon, it will bring you to a new activity screen
         */
        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddHabitTabsBase.class);

                startActivity(intent);

            }
        });
        allHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllHabitsActivity.class);
                // passing list down to populate listView
                startActivity(intent);
            }
        });
        todaysHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        followReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFollowingPage();

            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(); // logout
                finish(); // close, leave to parent activity.
            }
        });




    }
    // get this to log out when "back" is pressed on navBar
    public void logout(){
        FirebaseAuth.getInstance().signOut();

    }

///////////////////////////////////////////////////////////////
//    //This function is finished until the habit class is changed
//    public void updateProgress(){
//        //.child("userHabits").orderByChild("userHabits").equalTo(userHabits)
//        progress = db.collection("Users/userHabits");
//        progress.get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot QueryDocumentSnapshots) {
//
//                        String habits = "";
//                        for (QueryDocumentSnapshot documentSnapshot : QueryDocumentSnapshots){
//                            Habit habit = documentSnapshot.toObject(Habit.class);
//                            habit.setName(documentSnapshot.getId());
//                            String habitName = habit.getName();
//                            habits += " " + habitName;
//
//                            completion = completion + habit.getCompletion();// how many habits the user completed today
//                            totalHabits = totalHabits + 1;// how many habits the user has
//                        }
//
//                    }
//                });
//
//        progressTrack = (completion/totalHabits)*100;
//        progressBar3.setProgress(progressTrack);//updating the progress bar
//    }


    public void openFollowingPage() {
        Intent intent = new Intent(this, FollowingActivity.class);
        startActivity(intent);
    }





}
