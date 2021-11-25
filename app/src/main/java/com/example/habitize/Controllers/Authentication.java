package com.example.habitize.Controllers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Authentication{
    public static String SignUpMSG ="You have made an account successfully!";
    private static FirebaseAuth fAuth;


    // we initialize the firestore ONCE. Many objects but all will refer to the same instance
    static {
        fAuth = FirebaseAuth.getInstance();
    }




    // if we create the user/login successfully. we Log the user in




    Authentication(){
    }

    /**
     * this runs after DatabaseManager.checkUsernameAndRegister(). Checks the validity of the email
     */

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


    /**
     * This function takes users and log them in
     */
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
