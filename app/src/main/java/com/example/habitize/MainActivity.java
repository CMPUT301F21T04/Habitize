package com.example.habitize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button addHabit;
    private Button allHabits;
    private Button todaysHabits;
    private Button followReq;
    private Button logOut;
    private Toolbar toolBar;
    private User currentUser;


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


        // branch to new activities here
        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}