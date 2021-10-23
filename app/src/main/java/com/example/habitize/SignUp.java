package com.example.habitize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        Button create = findViewById(R.id.create_button);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText username = findViewById(R.id.userName);
        EditText password = findViewById(R.id.password);            //find a way that password will not be shown
        EditText confirmPass = findViewById(R.id.conPassword);
        EditText email = findViewById(R.id.email);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputEmail = email.getText().toString().trim();

                if (TextUtils.isEmpty(inputEmail)){
                    email.setError("Enter an email please!");
                    return;
                }
            }
        });




    }
}