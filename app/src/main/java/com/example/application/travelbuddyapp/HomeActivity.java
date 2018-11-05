package com.example.application.travelbuddyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    // Button signout;
    SharedPreferences sp;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference rootReference;
    // TextView username;
    String name = "Welcome, ";

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_trip:

                    loadFragment(new FindTravelFragment());
                    return true;
                case R.id.navigation_stay:

                    loadFragment(new Find_stay_Fragment());
                    return true;
                case R.id.navigation_account:

                    loadFragment(new AccountFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setScrollbarFadingEnabled(true);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());
        sp = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);

        //username = (TextView) findViewById(R.id.userName);
        //signout = (Button) findViewById(R.id.signout_button);
        firebaseAuth = FirebaseAuth.getInstance();
        if (sp.getBoolean("logged", false)) {
            String email = sp.getString("User", "");
            String password = sp.getString("password", "");

            //Logging in If User is already Logged in

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User Not in Database Login Again!", Toast.LENGTH_LONG).show();
                                HomeActivity.this.finish();

                                sp = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
                                sp.edit().putBoolean("logged", false).apply();
                                sp.edit().putString("User", "").apply();
                                sp.edit().putString("password", "").apply();

                                Intent toLoginActivity = new Intent(HomeActivity.this, LoginSignupActivity.class);
                                startActivity(toLoginActivity);
                            }
                        }
                    });
        }

        //firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        rootReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        rootReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name += dataSnapshot.child("username").getValue().toString();
                // username.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

//        signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                firebaseAuth.signOut();
//                Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_LONG).show();
//                finish();
//                Intent toLogin = new Intent(getApplicationContext(), LoginSignupActivity.class);
//                startActivity(toLogin);
//                sp.edit().putBoolean("logged",false).apply();
//                sp.edit().putString("User","").apply();
//                sp.edit().putString("password","").apply();
//            }
//        });



    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
       // transaction.addToBackStack(null);
        transaction.commit();
    }

}