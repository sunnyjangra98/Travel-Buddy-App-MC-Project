package com.example.application.travelbuddyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    TextView EditProfile, StayRequests, RequestedStays, AddStay, SignOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());

        EditProfile =  root.findViewById(R.id.editProfile);
        StayRequests =  root.findViewById(R.id.stayRequests);
        RequestedStays = root.findViewById(R.id.requestedStays);
        AddStay =  root.findViewById(R.id.addStay);
        SignOut =  root.findViewById(R.id.signOut);

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity().getApplicationContext(), AccountActivity.class);
                i.putExtra(AccountActivity.FRAGMENT_NAME, AccountActivity.EDIT_PROFILE_FRAGMENT_NAME);
                getActivity().startActivity(i);

            }
        });

        StayRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity().getApplicationContext(), AccountActivity.class);
                i.putExtra(AccountActivity.FRAGMENT_NAME, AccountActivity.STAY_REQUESTS_FRAGMENT_NAME);
                startActivity(i);
            }
        });

        RequestedStays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity().getApplicationContext(), AccountActivity.class);
                i.putExtra(AccountActivity.FRAGMENT_NAME, AccountActivity.REQUESTED_STAY_FRAGMENT_NAME);
                startActivity(i);
            }
        });
        AddStay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity().getApplicationContext(), AccountActivity.class);
                i.putExtra(AccountActivity.FRAGMENT_NAME, AccountActivity.ADD_STAY_FRAGMENT_NAME);
                getActivity().startActivity(i);
               // loadFragment(new AddStaysFragment());
            }
        });

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(getActivity().getApplicationContext(), "Logout successful", Toast.LENGTH_LONG).show();
                getActivity().finish();
                Intent toLogin = new Intent(getActivity().getApplicationContext(), LoginSignupActivity.class);
                getActivity().startActivity(toLogin);
//                sp.edit().putBoolean("logged",false).apply();
//                sp.edit().putString("User","").apply();
//                sp.edit().putString("password","").apply();
            }
        });

        return root;
    }

}
