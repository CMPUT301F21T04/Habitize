package com.example.habitize.Activities.SignupAndLogin;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.habitize.Activities.MainActivity;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.Controllers.ErrorShower;
import com.example.habitize.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity implements DatabaseManager.onRegistrationLoginListener, ErrorShower.ErrorHandler {
    private static final int RC_SIGN_IN = 301;
    //Initializing variables
    private FirebaseFirestore db;
    private CollectionReference users;
    private CollectionReference userHabits;
    private CollectionReference followers;
    private CollectionReference following;
    //google sign in init
    private com.google.android.gms.common.SignInButton googleSignUp;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;



    /**
     * Initialize activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Connecting variables with XML
        setContentView(R.layout.signup_activity_ui);
        Button login = findViewById(R.id.loginbutton);
        Button create = findViewById(R.id.create_button);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText username = findViewById(R.id.userName);
        EditText password = findViewById(R.id.password);
        EditText conPassword = findViewById(R.id.conPassword);
        EditText email = findViewById(R.id.email);
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        DatabaseManager.setRegistrationListener(this);
        com.google.android.gms.common.SignInButton googleSignUp = findViewById(R.id.signUp_google);



        progressBar.setVisibility(View.GONE);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                signIn();
                //Toast.makeText(SignUp.this,"google",Toast.LENGTH_LONG).show();
            }
        });

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
                //check if the password are the same
                if (!inputPassword.equals(inputConPass)) {
                    conPassword.setError("The passwords are not the same!");
                    return;
                }


                progressBar.setVisibility(View.VISIBLE);
                // TODO: the startActivity here might mess with NAVCONTROLLER
                // call authentication here using data Manager
                DatabaseManager.setInfoForRegistration(user,inputEmail,first,last,inputConPass);
                //check the user if is already signedUp
                DatabaseManager.checkUsernameAndRegister();


            }
        });

    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(SignUp.this,"Successful sign up with google welcome " +account.getDisplayName(),Toast.LENGTH_LONG).show();
                firebaseAuthWithGoogle(account.getDisplayName(),account.getEmail(),account.getGivenName(),account.getFamilyName(), account.getId());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(SignUp.this,"Failed sign up with google ",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String username,String email, String firstName, String lastName, String password) {

        DatabaseManager.setInfoForRegistration(username, email, firstName, lastName, password);
        //check the user if is already signedUp
        DatabaseManager.checkUsernameAndRegister();
    }

    @Override
    public void loginUser() {
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public String getErrorMessage(int errorCode) {
        switch (errorCode) {
            case 1:
                return "Enter an email";
            case 2:
                return "Password is required";
            case 3:
                return "Password should be greater than 8 characters";
            case 4:
                return "Registration with google success";
            case 5:
                return "Failed to register with google";
            default:
                return "Passwords do not match";
        }
    }
}