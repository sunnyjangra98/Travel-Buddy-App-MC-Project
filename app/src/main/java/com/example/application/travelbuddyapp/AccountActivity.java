package com.example.application.travelbuddyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AccountActivity  extends AppCompatActivity  {
    public static String FRAGMENT_NAME="FRAGMENT_NAME";
    public static String MY_TRIPS_REQUESTS_FRAGMENT_NAME = "MY_TRIPS";
    public static String EDIT_PROFILE_FRAGMENT_NAME="PROFILE";
    public static String ADD_STAY_FRAGMENT_NAME="ADD";
    public static String STAY_REQUESTS_FRAGMENT_NAME="REQUESTS";
    public static String REQUESTED_STAY_FRAGMENT_NAME="REQUESTED";
    FrameLayout frame;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
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
            //transaction.addToBackStack(null);
            transaction.commit();

        }
        else if(fragmentName.equals(MY_TRIPS_REQUESTS_FRAGMENT_NAME)){
            /*
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame2, new StayRequestsFragment());
            transaction.commit();
            */
        }
        else if(fragmentName.equals(REQUESTED_STAY_FRAGMENT_NAME)){
            ArrayList<Requested_Stay> stay_requests = getRequest();
            /*
            Log.d("TAG","SIZE "+stay_requests.size());
            for ( Requested_Stay r : stay_requests ) {
                Log.d("TAH", "INSIDEVALUES " + r.requested_city + "  " + r.requested_status + "  " + r.requested_stay_name);
            }
            */
            Bundle bunfrag = new Bundle();
            bunfrag.putParcelableArrayList("your_requests",stay_requests);
            StaysRequestedFragment your_stay_requests = new StaysRequestedFragment();
            your_stay_requests.setArguments(bunfrag);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame2, your_stay_requests);
            transaction.commit();
//            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.frame2, new StaysRequestedFragment());
//            transaction.commit();
        }
    }

    String city ;
    ArrayList<Requested_Stay> your_requests;
    public ArrayList<Requested_Stay> getRequest()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        your_requests = new ArrayList<Requested_Stay>();
        DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid()).child("requestedStay");
        requestRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    for (DataSnapshot data1 : data.getChildren()){
                        Log.d("TAG", "HELL YEAH:" + data1.getValue());
                        Requested_Stay r = new Requested_Stay();
                        r.requested_city = data.getKey();
                        if ( data1.hasChild("status") ) {
                            if ( data1.child("status").getValue().toString() != null ) {
                                int intStatus = Integer.parseInt(data1.child("status").getValue().toString());
                                String stringStatus = "";
                                if (intStatus == 0) stringStatus = "Pending";
                                else if (intStatus == 1) stringStatus = "Approved";
                                else stringStatus = "Rejected";
                                r.requested_status = stringStatus;
                            }
                        }
                        if ( data1.hasChild("requested_stay_name")) { if ( data1.child("requested_stay_name").getValue().toString() != null )
                            { r.requested_stay_name = data1.child("requested_stay_name").getValue().toString(); } }
                        if ( data1.hasChild("requested_stay_person")) { if ( data1.child("requested_stay_person").getValue().toString() != null )
                        { r.requested_stay_person = data1.child("requested_stay_person").getValue().toString(); } }
                        your_requests.add(r);
                    }
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        return your_requests;
    }
}
