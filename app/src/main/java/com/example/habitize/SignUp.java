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
                fAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // first check if there is no account already made.

                                if (task.isSuccessful()) {
                                    // checking if the user already exists.
                                    users.document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if(!documentSnapshot.exists()){
                                                Toast.makeText(SignUp.this, "You have made an account successfully!",
                                                        Toast.LENGTH_LONG).show();
                                                // we created an account. Lets set up the user stuff
                                                // TODO: Put legit values here later. Just filling the constructor because I
                                                // need it to work
                                                HashMap<String,Object> userNameField = new HashMap<>();
                                                HashMap<String,Object> nameField = new HashMap<>();
                                                HashMap<String,Object> lastNameField = new HashMap<>();
                                                HashMap<String,Object> emailField = new HashMap<>();
                                                HashMap<String, Object> pointField = new HashMap<>();
                                                HashMap<String, Object> progressField = new HashMap<>();
                                                userNameField.put("userName",user);
                                                nameField.put("firstName",first);
                                                lastNameField.put("lastName",last);
                                                emailField.put("email",inputEmail);
                                                pointField.put("points", 0L);
                                                progressField.put("progress",0L);
                                                // adding Data to User collection
                                                users.document(user).set(userNameField);
                                                users.document(user).update(nameField);
                                                users.document(user).update(lastNameField);
                                                users.document(user).update(emailField);
                                                users.document(user).update(pointField);
                                                users.document(user).update(progressField);
                                                // adding Data to UsersHabits collection
                                                HashMap<String,Object> habits = new HashMap<>();
                                                habits.put("habits",new ArrayList<Habit>());
                                                users.document(user).update(habits);
                                                // adding Data to followers Collection
                                                HashMap<String,Object> followList = new HashMap<>();
                                                followList.put("followers",new ArrayList<String>());
                                                users.document(user).update(followList);
                                                // adding Data to following Collection
                                                HashMap<String,Object> followingList = new HashMap<>();
                                                followingList.put("following",new ArrayList<String>());
                                                users.document(user).update(followingList);

                                                HashMap<String,String> emailMap = new HashMap<>();
                                                emailMap.put("user",user);
                                                db.collection("EmailToUser").document(inputEmail).set(emailMap);

                                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                                // we do it with intents so we can pass down arguments.
                                                Bundle userBundle = new Bundle();
                                                userBundle.putSerializable("User", user); // sending user identifier down
                                                intent.putExtras(userBundle);
                                                startActivity(intent); // start the activity with the passed user

                                            }else{
                                                username.setError("This User Name already have been taken!. Please choose another username");
                                                return;
                                            }
                                        }
                                    });




                                } else {
                                    Toast.makeText(SignUp.this, "Something Wrong!" + task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            }
        });

    }
}