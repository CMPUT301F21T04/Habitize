package com.example.habitize.Activities.SignupAndLogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.habitize.Activities.MainActivity;
import com.example.habitize.Controllers.Authentication;
import com.example.habitize.Controllers.DatabaseManager;
import com.example.habitize.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private com.google.android.gms.common.SignInButton googleSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 401;

    /**
     * Will instantiate the UI view of the activity screen.
     * @param savedInstanceState to be used for Bundle where fragment is re-constructed from a previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);
        // UI components
        email_EditText = findViewById(R.id.email_login);
        password_EditText = findViewById(R.id.password_login);
        login_Button = findViewById(R.id.LoginBTN);
        register_Button = findViewById(R.id.RegisterBTN);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        DatabaseManager.setLoginContext(this);

        // Create a Pop up dialog when user forgot password
        resetPass_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();


        com.google.android.gms.common.SignInButton googleSignIn = findViewById(R.id.signIn_google);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                signIn();
                //Toast.makeText(SignUp.this,"google",Toast.LENGTH_LONG).show();
            }
        });

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
                Toast.makeText(Login_Activity.this,"Successful sign up with google welcome " +account.getDisplayName(),Toast.LENGTH_LONG).show();
                firebaseAuthWithGoogle(account.getEmail(),account.getId());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(Login_Activity.this,"Failed sign up with google ",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String email, String password) {

        DatabaseManager.setInfoForLogin(email,password);
        DatabaseManager.setLoginListener(Login_Activity.this);
        Authentication.authenticateAndSignIn();




    }

    /**
     * This method will be called to transition from the login screen to the main screen.
     */
    @Override
    public void loginUser() {
        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
        startActivity(intent);
    }
}