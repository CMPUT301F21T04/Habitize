package com.example.habitize.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.habitize.Activities.AddHabit.AddHabitTabsBase;
import com.example.habitize.Activities.Followers.FollowingActivity;
import com.example.habitize.Activities.ViewHabitLists.AllHabitsActivity;
import com.example.habitize.Activities.ViewHabitLists.TodaysHabitsActivity;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.R;
import com.example.habitize.Structural.Habit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.StructuredQuery;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private CardView addHabit,allHabits,todaysHabits,followReq,logOut;
    private ProgressBar progressBar3;
    private TextView username;
    private Toolbar toolBar;
    private ImageButton qrBTN;

    private StructuredQuery.FieldReference fieldReference;
    public ArrayList<Habit> habitList;
    private int progressTrack = 0; //starting at 0 (max 100)
    private Integer completion = 0;
    private Integer totalHabits = 0;

    private FloatingActionButton fmain,logoutf,lboardf;
    Float translationYaxis = 100f;
    Boolean menuOpen =false;
    /**
     * Start the mainactivity
     * @param savedInstanceState to be used for Bundle where fragment is re-constructed from a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        logOut = findViewById(R.id.logoutCard);
        addHabit = findViewById(R.id.addHabitCard); // our 4 buttons
        allHabits = findViewById(R.id.allHabitCard);
        todaysHabits = findViewById(R.id.todayHabitCard);
        followReq =  findViewById(R.id.followingCard);
        username = findViewById(R.id.userNameMain);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBarmain);
        qrBTN = findViewById(R.id.qrbtn);




        username.setText(DatabaseManager.getUser());
        // When add habit button is clicked upon, it will bring you to a add activity screen
        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddHabitTabsBase.class);
                startActivity(intent);
            }
        });
        followReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FollowingActivity.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                finish();
            }
        });

        qrBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QRActivity.class);
                startActivity(intent);
            }
        });

        // When allHabits habit button is clicked upon, it will bring you to a view the allhabits activity screen
        allHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllHabitsActivity.class);
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


    }
    /**
     * get this to log out when "back" is pressed on navBar
     */
    public void logout(){
        FirebaseAuth.getInstance().signOut();

    }


}
