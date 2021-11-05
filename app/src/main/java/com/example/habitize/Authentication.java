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

    public static String SignUpMSG ="You have made an account successfully!";
    private static FirebaseAuth fAuth;


    // we initialize the firestore ONCE. Many objects but all will refer to the same instance
    static {
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
                    System.out.println("Somethingwrong");
                }

            }
        });
    }




    public static void authenticateAndSignIn(){
        fAuth.signInWithEmailAndPassword(DatabaseManager.getInputEmail(),DatabaseManager.getInputPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    DatabaseManager.signInUser(); // the email and password are correct. Call the database to retrieve data and sign in
                }
                else{
                    System.out.println("broken");
                }
            }
        });
    }

}
