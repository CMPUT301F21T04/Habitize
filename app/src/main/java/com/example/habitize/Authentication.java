package com.example.habitize;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.habitize.Habit;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.firestore.FirebaseFirestore;

public class Authentication{
    // db is shared across all instances of the manager
    private static FirebaseFirestore db;
    private static CollectionReference users;
    private static CollectionReference userHabits;
    private static CollectionReference followers;
    private static CollectionReference following;
    public static String SignUpMSG ="You have made an account successfully!";
    private static FirebaseAuth fAuth;


    // we initialize the firestore ONCE. Many objects but all will refer to the same instance
    static {
        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

    }




    // if we create the user/login successfully. we Log the user in




    // does nothing
    Authentication(){

    }

    // this runs after DatabaseManager.checkUsernameAndRegister(). Checks the validity of the email
    public static void checkEmailBeforeRegistering(){
        fAuth.createUserWithEmailAndPassword(DatabaseManager.getInputEmail(),DatabaseManager.getInputPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // email isn't taken. Continue
                if(task.isSuccessful()){
                    DatabaseManager.createUserDocumentAndLogin();
                }
                else{

                }

            }
        });
    }


    public static String createUserWithAllInfo(String inputEmail, String inputPassword, String user,String first,String last){
        db = FirebaseFirestore.getInstance(); // init db
        users = db.collection("Users"); // reference to users collection. check if a user exists here
        userHabits = db.collection("userHabits");
        followers = db.collection("followers");
        following = db.collection("following");

        //SignUpMSG = "TEST";
        fAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // first check if there is no account already made.
                        //SignUpMSG = "TEST";
                        if (task.isSuccessful()) {
                            // if no email is taken
                            // checking if the user already exists.
                            users.document(user).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    if(!documentSnapshot.exists()){
                                        String SignUpMSG = "You have made an account successfully!";

                                        //Toast.makeText(SignUp.class, "You have made an account successfully!", Toast.LENGTH_LONG).show();

                                        //Toast.makeText(SignUp.this, "You have made an account successfully!",
                                        //        Toast.LENGTH_LONG).show();
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
                                        //System.out.println("########increateUserWithEmailAndPassword###");

//                                        Intent intent = new Intent(SignUp.this, MainActivity.class);
//                                         we do it with intents so we can pass down arguments.
//                                        Bundle userBundle = new Bundle();
//                                        userBundle.putSerializable("User", user); // sending user identifier down
//                                        intent.putExtras(userBundle);
//                                        startActivity(intent); // start the activity with the passed userBundle

                                    }else{
                                        SignUpMSG = "This User Name already have been taken!. Please choose another username";
                                        return;
                                    }
                                }
                            });




                        } else {

                            SignUpMSG = task.getException().getMessage();

                            return;
                            //Toast.makeText(SignUp, "Something Wrong!" + task.getException().getMessage(),
                                    //Toast.LENGTH_LONG).show();

                        }
                    }
                });

    return SignUpMSG;
    }


    public static void authenticateAndSignIn(){
        fAuth.signInWithEmailAndPassword(DatabaseManager.getInputEmail(),DatabaseManager.getInputPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    DatabaseManager.signInUser(); // the email and password are correct. Call the database to retrieve data and sign in

                }
                else{

                }
            }
        });
    }

}
