package com.example.habitize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    //Declare global variables
    FirebaseFirestore db;
    Button create_button;
    EditText firstName_EditText;
    EditText lastName_EditText;
    EditText username_EditText;
    EditText password_EditText;
    EditText email_EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        create_button = findViewById(R.id.create_button);
        firstName_EditText = findViewById(R.id.firstName);
        lastName_EditText = findViewById(R.id.lastName);
        username_EditText = findViewById(R.id.userName);
        password_EditText = findViewById(R.id.password);
        email_EditText = findViewById(R.id.email);

        // Database Accessing
        db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users"); //pointer to the database

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String firstName = firstName_EditText.getText().toString();
                final String lastName = lastName_EditText.getText().toString();
                final String username = username_EditText.getText().toString();
                final String password = password_EditText.getText().toString();
                final String email = email_EditText.getText().toString();
                final String TAG = "Sample"; // idk what this is
                HashMap<String, String> data = new HashMap<>();

                if (firstName.length()>0 && lastName.length()>0 && username.length()>0
                        && password.length()>0 && email.length()>0){
                    data.put("First Name",firstName);
                    data.put("Last Name", lastName);
                    data.put("Password",password);
                    data.put("Username",username);
                    collectionReference
                            .document(email)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "Data has been added successfully!");
                                    firstName_EditText.setText("");
                                    lastName_EditText.setText("");
                                    username_EditText.setText("");
                                    email_EditText.setText("");
                                    password_EditText.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // These are a method which gets executed if there’s any problem
                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                }
                            });
                }

            }
        });
    }
}

// To be implemented:
// * error messages when not all fields are filled
// * check the username in the database, if it already exist then deny register (display error message)