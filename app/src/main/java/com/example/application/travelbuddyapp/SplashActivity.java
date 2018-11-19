package com.example.application.travelbuddyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                sp = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);

                if(!sp.getBoolean("logged",false)){
                    Intent i = new Intent(SplashActivity.this, LoginSignupActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent toHomeActivity = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(toHomeActivity);
                    finish();

                }


            }
        }, 1000);


//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
    }
}
