package codeathon.ku.mentr;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    //Tag
    private static final String TAG = "SignupActivity";

    //Final for required items
    private static final String REQUIRED = "Required";

    //Initialize Database
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;

    //Initialize variables
    private EditText newFirstName;
    private EditText newLastName;
    private Button newSignUpButton;
    private RadioButton newStudentButton;
    private RadioButton newMentorButton;

    //Happens when opened.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        newFirstName = (EditText) findViewById(R.id.firstName);
        newLastName = (EditText) findViewById(R.id.lastName);
        newSignUpButton = (Button) findViewById(R.id.signUpButton);
        newStudentButton = (RadioButton) findViewById(R.id.studentRadio);
        newMentorButton = (RadioButton) findViewById(R.id.mentorRadio);

        //Database stuff
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();

        //Monitors changes
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(SignupActivity.this, "Signed in with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(SignupActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };

        //Read from the database
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
             Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        //Listens for click, then adds user information to database
        newSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Adding User.");
                String name = newFirstName.getText().toString() + " " + newLastName.getText().toString();
                if(!name.equals("")) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    mRef.child("users").child(userID).child("name").setValue(name);

                    if (newStudentButton.isChecked()) {
                        mRef.child("users").child(userID).child("mentor").setValue(false);
                    }
                    else if (newMentorButton.isChecked()) {
                        mRef.child("users").child(userID).child("mentor").setValue(true);
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        //To see if user is signed in and refresh user interface
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    @Override
    public void onStop() {
        super.onStop();
        ;
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
