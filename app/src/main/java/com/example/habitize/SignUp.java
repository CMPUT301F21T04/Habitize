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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        Button login = findViewById(R.id.loginbutton);
        Button create = findViewById(R.id.create_button);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText username = findViewById(R.id.userName);
        EditText password = findViewById(R.id.password);
        EditText ConPassword = findViewById(R.id.conPassword);
        EditText email = findViewById(R.id.email);
        ProgressBar progressBar = findViewById(R.id.progressBar2);

        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.GONE);

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login_Activity.class));
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = email.getText().toString().trim();
                String inputPassword = password.getText().toString().trim();
                String inputConPassword = ConPassword.getText().toString().trim();
                if (TextUtils.isEmpty(firstName.getText().toString().trim())){
                    firstName.setError("Enter a name please!");
                }
                if (TextUtils.isEmpty(lastName.getText().toString().trim())){
                    lastName.setError("Enter a name please!");
                }
                if (TextUtils.isEmpty(username.getText().toString().trim())){
                    username.setError("Enter a username please!");
                }
                if (TextUtils.isEmpty(inputEmail)){
                    email.setError("Enter an email please!");
                    return;
                }
                if (TextUtils.isEmpty(inputPassword)){
                    password.setError("Please enter a password!");
                    return;
                }
                if (inputPassword.length() < 8){
                    password.setError("Passsword should be greater than 8 characters");
                    return;
                }
                if (TextUtils.isEmpty(inputConPassword)){
                    ConPassword.setError("Please enter a password!");
                    return;
                }
                if (inputConPassword != inputPassword){
                    ConPassword.setError("The passwords are not the same!");
                }
                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(inputEmail,inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SignUp.this,"You have made an account successfully!",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(SignUp.this,"Something Wrong!" + task.getException().getMessage(),Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });




    }
}