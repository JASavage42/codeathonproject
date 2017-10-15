package codeathon.ku.mentr;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class mentorActivity extends AppCompatActivity {

    //Tag
    private static final String TAG = "ViewDatabase";

    //Firbase
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRef;
    private String userID;
    private ListView mListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);

        mListView = (ListView) findViewById(R.id.listView);

        //DECLARE
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        //Monitors changes
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Toast.makeText(mentorActivity.this, "Signed in with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(mentorActivity.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                }
                // ...
            }
        };

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showProfiles(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void showProfiles(DataSnapshot dataSnapshot) {
        //Loop through the database and show user's Name, Email, and Mentor
  /*      for (DataSnapshot ds : dataSnapshot.getChildren()) {
            UserInfo userInfo = new UserInfo();
            //userInfo.setName(ds.child(userID).getValue(UserInfo.class).getName());
            //userInfo.setEmail(ds.child(userID).getValue(UserInfo.class).getEmail());
            //userInfo.setMentor(ds.child(userID).getValue(UserInfo.class).getMentor());

            //Display!
            Log.d(TAG, "showProfiles: name: " + userInfo.getName());
            Log.d(TAG, "showProfiles: email: " + userInfo.getEmail());
            Log.d(TAG, "showProfiles: mentor: " + userInfo.getMentor());

            ArrayList<String> a = new ArrayList<>();
            a.add(userInfo.getName());
            a.add(userInfo.getEmail());
            a.add(userInfo.getMentor());
            ArrayAdapter adapter = new ArrayAdapter(mentorActivity.this, android.R.layout.simple_list_item_1, a);
            mListView.setAdapter(adapter); */

        ArrayList<String> a = new ArrayList<>();
        a.add("Testy");
        a.add("McTestFace");
        a.add("Please work");
        ArrayAdapter adapter = new ArrayAdapter(mentorActivity.this, android.R.layout.simple_list_item_1, a);
        mListView.setAdapter(adapter);
        //}
    }
}
