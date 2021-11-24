package com.example.habitize;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login_Activity extends AppCompatActivity implements DatabaseManager.onLoginListener {
    // variables to be worked in
    EditText email_EditText, password_EditText;
    Button login_Button, register_Button, forgot_Button;
    ProgressBar progressBar;
    AlertDialog.Builder resetPass_alert;
    LayoutInflater inflater;
    FirebaseAuth Authenticator;
    private FirebaseFirestore db; // our database
    private CollectionReference UsersCol;
    private DocumentReference userRef;
    private DocumentSnapshot userData;

    /**
     * Will instantiate the UI view of the activity screen.
     * @param savedInstanceState to be used for Bundle where fragment is re-constructed from a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        // UI components
        email_EditText = findViewById(R.id.email_login);
        password_EditText = findViewById(R.id.password_login);
        login_Button = findViewById(R.id.LoginBTN);
        register_Button = findViewById(R.id.RegisterBTN);
        forgot_Button = findViewById(R.id.forgotPassBTN);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        DatabaseManager.setLoginContext(this);

        // Create a Pop up dialog when user forgot password
        resetPass_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();



        //  Listener for the login button. When clicked and passed authentication,
        // then redirect to the main screen of the app.
        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_EditText.getText().toString().trim();
                String password = password_EditText.getText().toString().trim();
                // Error handlers to make sure the required fields are filled.
                if (TextUtils.isEmpty(email)){
                    email_EditText.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    password_EditText.setError("Password is Required.");
                    return;
                }

                if (password.length() < 8) {
                    password_EditText.setError("Password must be at least 8 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // User Authentication
                DatabaseManager.setInfoForLogin(email,password);
                DatabaseManager.setLoginListener(Login_Activity.this);
                Authentication.authenticateAndSignIn();

            }
        });


        // Listener for the register button. When user clicked the register button,
        // redirect user to register screen.
        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });

        // Listener for the forgot password button. When user clicked the register button,
        //redirect user to forgot password screen.
        forgot_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // have a alertDialog
                View v = inflater.inflate(R.layout.reset_layout,null);
                resetPass_alert.setTitle("Reset Password?")
                        .setMessage("Enter your email. A reset link will be then sent to your email.")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // if the email address is valid then send the reset link
                                EditText email_EditText = v.findViewById(R.id.email_reset);
                                String email = email_EditText.getText().toString();
                                if(email.isEmpty()){
                                    email_EditText.setError("Email is required.");
                                }
                                Authenticator.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Login_Activity.this,"Reset Email Sent",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login_Activity.this,"Reset Email Failed: " + e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .setView(v)
                        .create().show();
            }
        });
    }

    /**
     * This method will be called to transition from the login screen to the main screen.
     */
    @Override
    public void loginUser() {
        Intent intent = new Intent(Login_Activity.this,MainActivity.class);
        startActivity(intent);
    }
}