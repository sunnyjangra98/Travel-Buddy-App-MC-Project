package com.example.application.travelbuddyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

public class AccountActivity  extends AppCompatActivity  {
    public static String FRAGMENT_NAME="FRAGMENT_NAME";
    public static String EDIT_PROFILE_FRAGMENT_NAME="PROFILE";
    public static String ADD_STAY_FRAGMENT_NAME="ADD";
    public static String STAY_REQUESTS_FRAGMENT_NAME="REQUESTS";
    public static String REQUESTED_STAY_FRAGMENT_NAME="REQUESTED";
    FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        frame = findViewById(R.id.frame2);
        Bundle extra = i.getExtras();
        String fragmentName = extra.getString(FRAGMENT_NAME).toString();
        if (fragmentName.equals(EDIT_PROFILE_FRAGMENT_NAME)) {
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame2, new EditProfile());
            transaction.commit();
        }
        else if(fragmentName.equals(ADD_STAY_FRAGMENT_NAME)){

            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame2, new AddStaysFragment());
            transaction.commit();

        }
        else if(fragmentName.equals(STAY_REQUESTS_FRAGMENT_NAME)){

//            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame2, new StayRequestsFragment());
//            transaction.commit();

        }
        else if(fragmentName.equals(REQUESTED_STAY_FRAGMENT_NAME)){

           // android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
           // transaction.replace(R.id.frame2, new StaysRequestedFragment());
           // transaction.commit();

        }
    }
}
