package com.example.habitize;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.Authenticator;
import java.util.HashMap;

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
    private User currentUser;
    private CollectionReference progress;


    private int progressTrack = 0; //starting at 0 (max 100)
    Integer completion = 0;
    Integer totalHabits = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logOut = findViewById(R.id.logout);

        // currentUser = (User)getIntent().getExtras().getSerializable("User"); // retrieving the user


        // System.out.println(currentUser.getFirstName());
        db = FirebaseFirestore.getInstance(); // initialize database. We don't really need to do this here.
        // we can have the database instantiated in only the activities where it is needed to pull data
        addHabit = findViewById(R.id.addHabit); // our 4 buttons
        allHabits = findViewById(R.id.allHabits);
        todaysHabits = findViewById(R.id.todaysHabits);
        followReq = findViewById(R.id.followReq);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3);


//        updateProgress();




        // branch to new activities here

        /**
         * When add habit button is clicked upon, it will bring you to a new activity screen
         */
        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addHabitIntent = new Intent(MainActivity.this, AddHabitActivity.class);
                startActivity(addHabitIntent);
            }
        });
        allHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        todaysHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AllHabitsActivity.class);
                Bundle userBundle = new Bundle(); // bundling user and sending them down
                userBundle.putSerializable("User",currentUser);
                intent.putExtras(userBundle);
                startActivity(intent);

            }
        });
        followReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

}
