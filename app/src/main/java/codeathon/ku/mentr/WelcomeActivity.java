package codeathon.ku.mentr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private TextView mUsername, mPassword;
    private Button signUpButton, loginButton, logoutButton;

    //Log
    private static final String TAG = "MainActivity";

    //Declare Firebase Authorization and listener
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Views
        mUsername = (TextView) findViewById(R.id.usernameLogin);
        mPassword = (TextView) findViewById(R.id.passwordLogin);

        //Buttons
        signUpButton = (Button) findViewById(R.id.signUpButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);


        //Initialize authorization
        mAuth = FirebaseAuth.getInstance();

        //Monitors changes
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(WelcomeActivity.this, "Signed in with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(WelcomeActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };

        //Login in button functionality
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mUsername.getText().toString();
                String pass = mPassword.getText().toString();
                //Success
                if(!email.equals("") && !pass.equals("")) {
                    mAuth.signInWithEmailAndPassword(email,pass);
                }
                //Failure
                else {
                    Toast.makeText(WelcomeActivity.this, "Email and Password Required", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(WelcomeActivity.this, mentorActivity.class);
                startActivity(intent);
            }
        });

        //Log out button functionality
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(WelcomeActivity.this, "Logging off...", Toast.LENGTH_SHORT).show();
            }
        });

        //Sign up button functionality
        signUpButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mUsername.getText().toString();
                String pass = mPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, pass);
                Toast.makeText(WelcomeActivity.this, "Creating new user", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        }));
    }

    @Override
    public void onStart() {
        super.onStart();

        //To see if user is signed in and refresh user interface
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    @Override
    public void onStop() {
        super.onStop();;
        if (mAuthListener !=null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}