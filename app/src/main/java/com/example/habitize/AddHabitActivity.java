package com.example.habitize;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddHabitActivity extends AppCompatActivity {
    private EditText Title;
    private EditText Description;
    private EditText StartDate;
    private Button Monday;
    private Button Tuesday;
    private Button Wednesday;
    private Button Thursday;
    private Button Friday;
    private Button Saturday;
    private Button Sunday;
    private Switch Geolocation;
    private Button imageBtn;
    private Button locationBtn;
    private Button createHabit;
    private FirebaseFirestore db;
    private CollectionReference userCol;
    private DocumentReference docRef;
    private String passedEmail;
    private List<Habit> passedHabits;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit_info);

        TabLayout tabLayout = findViewById(R.id.simpleTabLayout); // get the reference of TabLayout
        TabLayout.Tab firstTab = tabLayout.newTab(); // Create a new Tab names "First Tab"
        firstTab.setText("First Tab"); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.ic_baseline_check_24); // set an icon for the first tab
        tabLayout.addTab(firstTab,0); // add  the tab to the TabLayout
        TabLayout.Tab secondTab = tabLayout.newTab(); // Create a new Tab names "First Tab"
        secondTab.setText("Second Tab"); // set the Text for the first Tab
        secondTab.setIcon(R.drawable.ic_baseline_check_24); // set an icon for the first tab
        tabLayout.addTab(secondTab,1); // add  the tab to the TabLayout
        TabLayout.Tab thirdTab = tabLayout.newTab(); // Create a new Tab names "First Tab"
        thirdTab.setText("Third Tab"); // set the Text for the first Tab
        thirdTab.setIcon(R.drawable.ic_baseline_check_24); // set an icon for the first tab
        tabLayout.addTab(thirdTab,2); // add  the tab to the TabLayout
        passedEmail = (String)getIntent().getExtras().getSerializable("User"); // retrieving passed user

        createHabit = findViewById(R.id.createHabit);
        Title = findViewById((R.id.habitTitle));
        Description = findViewById((R.id.habitDescription));
        db = FirebaseFirestore.getInstance();
        userCol = db.collection("userHabits");
        docRef = userCol.document(passedEmail);



        // We pull the current habit list, modify it, and send it back (only if we create the habit)
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                passedHabits = new ArrayList<>(); // reset the list
                ArrayList<Habit> mappedList =  (ArrayList<Habit>) value.get("habits");
                for(int i = 0; i < mappedList.size() ; i++){ // get each item one by one
                    Map<String,String> habitFields = (Map<String, String>) mappedList.get(i); // map to all the fields
                    // retrieves all the habit information and adds it to the habitList
                    String name = habitFields.get("name");
                    String description = habitFields.get("description");
                    Habit newHabit = new Habit(name,description); // create a new habit out of this information
                    passedHabits.add(newHabit); // add it to the habitList

                }
            }
        });

        createHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the habit
                Habit newHabit = new Habit(Title.getText().toString(), Description.getText().toString());
                // add it to the user list
                passedHabits.add(newHabit);
                // Hash it for transportation to database
                HashMap<String,Object> listMap = new HashMap<>();
                listMap.put("habits",passedHabits);
                // send to database and close
                docRef.set(listMap);
                finish();
            }
        });




    }
}
