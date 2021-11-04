package com.example.habitize;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    //Initializing variables
    private FirebaseFirestore db;
    private CollectionReference users;
    private CollectionReference userHabits;
    private CollectionReference followers;
    private CollectionReference following;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connecting variables with XML
        setContentView(R.layout.signup_activity);
        Button login = findViewById(R.id.loginbutton);
        Button create = findViewById(R.id.create_button);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText username = findViewById(R.id.userName);
        EditText password = findViewById(R.id.password);
        EditText conPassword = findViewById(R.id.conPassword);
        EditText email = findViewById(R.id.email);
        ProgressBar progressBar = findViewById(R.id.progressBar2);

        db = FirebaseFirestore.getInstance(); // init db
        users = db.collection("Users"); // reference to users collection. check if a user exists here
        userHabits = db.collection("userHabits");
        followers = db.collection("followers");
        following = db.collection("following");

        //Getting fireBase Authentication
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.GONE);

        // disabling so i stop getting merked
        /*
         * if (fAuth.getCurrentUser() != null){ startActivity(new
         * Intent(getApplicationContext(),MainActivity.class)); finish(); }
         * 
         */

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            }
        });
        //Create Account button
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // trimming the white space from the input
                String inputEmail = email.getText().toString().trim();
                String inputPassword = password.getText().toString().trim();
                String inputConPass = conPassword.getText().toString().trim();
                String first = firstName.getText().toString().trim();
                String last = lastName.getText().toString().trim();
                String user = username.getText().toString().trim();

                //Showing Error on the input box when the input is incorrect
                if (TextUtils.isEmpty(inputEmail)) {
                    email.setError("Enter an email please!");
                    return;
                }
                if (TextUtils.isEmpty(inputPassword)) {
                    password.setError("Please enter a password!");
                    return;
                }
                if (inputPassword.length() < 8) {
                    password.setError("Passsword should be greater than 8 characters");
                    return;
                }


                //if (inputPassword != inputConPass) {
                 //   conPassword.setError("The passwords are not the same!");
                 //   return;
                //}

                if (!inputPassword.equals(inputConPass)) {
                    conPassword.setError("The passwords are not the same!");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                // TODO: the startActivity here might mess with NAVCONTROLLER
                String errMSG;
                ////Call Authentication class and pass all user input in createUserWithAllInfo///
                errMSG = Authentication.createUserWithAllInfo( inputEmail,  inputPassword,  user, first, last);

//                if (SignUpMSG.equals("You have made an account successfully!")) {
//                    Intent intent = new Intent(SignUp.this, MainActivity.class);
//                    // we do it with intents so we can pass down arguments.
//                    Bundle userBundle = new Bundle();
//                    userBundle.putSerializable("User", user); // sending user identifier down
//                    intent.putExtras(userBundle);
//                    startActivity(intent); // start the activity with the passed user
//                }
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                // we do it with intents so we can pass down arguments.
                Bundle userBundle = new Bundle();
                userBundle.putSerializable("User", user); // sending user identifier down
                intent.putExtras(userBundle);
                startActivity(intent); // start the activity with the passed user
                String test = "Nicccce";


                Toast.makeText(SignUp.this, errMSG, Toast.LENGTH_LONG).show();

            }
        });

    }
}