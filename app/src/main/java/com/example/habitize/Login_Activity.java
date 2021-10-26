package com.example.habitize;

import androidx.annotation.NonNull;
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

public class Login_Activity extends AppCompatActivity {
    EditText email_EditText, password_EditText;
    Button login_Button, register_Button, forgot_Button;
    ProgressBar progressBar;
    AlertDialog.Builder resetPass_alert;
    LayoutInflater inflater;
    FirebaseAuth Authenticator;


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

        // Access to Firebase
        Authenticator = FirebaseAuth.getInstance();

        // Create a Pop up dialog when user forgot password
        resetPass_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_EditText.getText().toString().trim();
                String password = password_EditText.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    email_EditText.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    password_EditText.setError("Password is Required");
                    return;
                }

                if (password.length() < 8) {
                    password_EditText.setError("Password must be at least 8 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // User Authentication
                Authenticator.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Determine if the login is successful or not
//                      // If successful, display a success message and redirect user to MainActivity
                        if (task.isSuccessful()) {
                            Toast.makeText(Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Login_Activity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        register_Button.setOnClickListener(new View.OnClickListener() {
            //when register button is clicked, redirect user to register screen
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
            }
        });
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
}