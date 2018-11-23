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

import android.util.Log;
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
    //String name = "Welcome, ";

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
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadFragment(new WelcomeFragment() );
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setScrollbarFadingEnabled(true);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());
        sp = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);

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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        //rootReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras != null){
            if(extras.containsKey("fromNotification")) {
                String msg = extras.getString("fromNotification");
                if (msg.equals("EditProfileFragment")){
                    navigation.setSelectedItemId(R.id.navigation_account);
                    //loadFragment(new AccountFragment());
                    //l
                }
            }
        }


    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
       // transaction.addToBackStack(null);
        transaction.commit();
    }

}
