package com.example.habitize;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

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
    private CardView addHabit;
    private CardView allHabits;
    private CardView todaysHabits;
    private CardView followReq;
    private Button leaderboard;
    private Button logOut;
    private ProgressBar progressBar3;
    private TextView textView2;
    private Toolbar toolBar;

    private StructuredQuery.FieldReference fieldReference;
    public ArrayList<Habit> habitList;
    private int progressTrack = 0; //starting at 0 (max 100)
    private Integer completion = 0;
    private Integer totalHabits = 0;

    /**
     * Start the mainactivity
     * @param savedInstanceState to be used for Bundle where fragment is re-constructed from a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logOut = findViewById(R.id.logoutmain);
        addHabit = findViewById(R.id.addHabitCard); // our 4 buttons
        allHabits = findViewById(R.id.allHabitCard);
        todaysHabits = findViewById(R.id.todayHabitCard);
        followReq =  findViewById(R.id.followingCard);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBarmain);
        leaderboard = findViewById(R.id.leaderboard);

       // Branch the activities here:

        // When add habit button is clicked upon, it will bring you to a add activity screen
        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddHabitTabsBase.class);
                startActivity(intent);

            }
        });

        // When allHabits habit button is clicked upon, it will bring you to a view the allhabits activity screen
        allHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllHabitsActivity.class);
                // passing list down to populate listView
                startActivity(intent);
            }
        });

        // When todayshabit button is clicked upon, it will bring you to a today's habit activity screen
        todaysHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TodaysHabitsActivity.class);
                Bundle userBundle = new Bundle();
                intent.putExtras(userBundle);
                startActivity(intent);
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Leaderboard.class);
                startActivity(intent);
            }
        });

        // When following habit button is clicked upon, it will bring you to a following activity screen
        followReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFollowingPage();

            }
        });
        // When logout habit button is clicked upon, it will bring you to a logout activity screen
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(); // logout
                finish(); // close, leave to parent activity.
            }
        });

    }

    /**
     * get this to log out when "back" is pressed on navBar
     */
    public void logout(){
        FirebaseAuth.getInstance().signOut();

    }

    /**
     * This method sends the user to a new activity screen: the following list.
     */
    public void openFollowingPage() {
        Intent intent = new Intent(this, FollowingActivity.class);
        startActivity(intent);
    }

}
