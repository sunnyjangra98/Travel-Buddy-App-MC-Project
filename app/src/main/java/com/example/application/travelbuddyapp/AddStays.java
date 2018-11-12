package com.example.application.travelbuddyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStays extends Fragment implements StayDialog.StayDialogListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FloatingActionButton addStayButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_places, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        addStayButton = (FloatingActionButton) root.findViewById(R.id.addStayButton);

        addStayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        return root;
    }

    public void openDialog(){
        StayDialog stayDialog = new StayDialog();
        stayDialog.setTargetFragment(AddStays.this, 1);
        stayDialog.show(getFragmentManager(), "AddStayFragment");
    }

    @Override
    public void sendBackToFragment(String hostName, String Place, String Date, String City) {
        Toast.makeText(getContext(), "Now to add this in list", Toast.LENGTH_SHORT).show();
    }
}
