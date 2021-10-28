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
    private String currentUser;
    private CollectionReference progress;
    private CollectionReference userHabits;
    private DocumentReference user;
    private StructuredQuery.FieldReference fieldReference;
    private ArrayList<Habit> habitList;


    private int progressTrack = 0; //starting at 0 (max 100)
    Integer completion = 0;
    Integer totalHabits = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logOut = findViewById(R.id.logout);
        db = FirebaseFirestore.getInstance();
        userHabits = db.collection("userHabits");
        currentUser = (String)getIntent().getExtras().getSerializable("User"); // retrieving the user
        user = userHabits.document(currentUser); // gets the habits at the current user


        // populate the list with the values that are in it at first run.
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) documentSnapshot.get("habits");
                habitList = new ArrayList<>(); // reset the list

                for(int i = 0; i < mappedList.size() ; i++){ // looping through every habit
                    Map<String,String> habitFields = (Map<String, String>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = habitFields.get("name");
                    String description = habitFields.get("description");
                    Habit newHabit = new Habit(name,description); // create a new habit out of this information
                    habitList.add(newHabit); // add it to the habitList

                }
            }
        });

        // if we detect a change in the Habits, we repopulate our habitList
        user.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                habitList = new ArrayList<>(); // reset the list
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                for(int i = 0; i < mappedList.size() ; i++){ // get each item one by one
                    Map<String,String> habitFields = (Map<String, String>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = habitFields.get("name");
                    String description = habitFields.get("description");
                    Habit newHabit = new Habit(name,description); // create a new habit out of this information
                    habitList.add(newHabit); // add it to the habitList

                }
            }
        });

        addHabit = findViewById(R.id.addHabit); // our 4 buttons
        allHabits = findViewById(R.id.allHabits);
        todaysHabits = findViewById(R.id.todaysHabits);
        followReq = findViewById(R.id.followReq);
        progressBar3 = (ProgressBar)findViewById(R.id.progressBar3);


       // updateProgress();




        // branch to new activities here

        /**
         * When add habit button is clicked upon, it will bring you to a new activity screen
         */
        addHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddHabitActivity.class);
                // passsing down a list to modify, and a user to change the database of after the change is made
                Bundle userBundle = new Bundle();
                userBundle.putSerializable("list",habitList);
                userBundle.putSerializable("User",currentUser);
                intent.putExtras(userBundle);
                startActivity(intent);

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
                // passing list down to populate listView
                Bundle userBundle = new Bundle();
                userBundle.putSerializable("list",habitList);
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



/////////////////////////////////////////////////////////////
    //This function is finished until the habit class is changed
    public void updateProgress(){
        //.child("userHabits").orderByChild("userHabits").equalTo(userHabits)
        progress = db.collection("Users/userHabits");
        progress.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot QueryDocumentSnapshots) {

                        String habits = "";
                        for (QueryDocumentSnapshot documentSnapshot : QueryDocumentSnapshots){
                            Habit habit = documentSnapshot.toObject(Habit.class);
                            habit.setName(documentSnapshot.getId());
                            String habitName = habit.getName();
                            habits += " " + habitName;

                            completion = completion + habit.getCompletion();// how many habits the user completed today
                            totalHabits = totalHabits + 1;// how many habits the user has
                        }

                    }
                });

        progressTrack = (completion/totalHabits)*100;
        progressBar3.setProgress(progressTrack);//updating the progress bar
    }

}
