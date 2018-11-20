package com.example.application.travelbuddyapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class StayFragmentsActivity extends AppCompatActivity {
    public static String FRAGMENT_NAME="FRAGMENT_NAME";
    public static String SEARCH_FRAGMENT_NAME="SEARCH";
    public static String DETAIL_FRAGMENT_NAME="DETAIL";
    FrameLayout frame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay_fragments);
        }
    @Override
    protected void onStart() {
        super.onStart();
        Intent i = getIntent();
        frame = findViewById(R.id.frame);
        Bundle extra = i.getExtras();
        String fragmentName = extra.getString(FRAGMENT_NAME).toString();
        if (fragmentName.equals(SEARCH_FRAGMENT_NAME)) {
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, new searchStayFragment());
            transaction.commit();
        }
    }
}
