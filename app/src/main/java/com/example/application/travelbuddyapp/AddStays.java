package com.example.application.travelbuddyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AddStays extends Fragment implements StayDialog.StayDialogListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, databaseReference1;
    StorageReference storageReference;
    FloatingActionButton addStayButton;
    StayAdapter adapter;
    RecyclerView recyclerView;
    List<Stay> addStayList;
    Stay newStay;
    String rHostName, rPlace, rDate, rCity, rName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_places, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Stay");
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        storageReference = FirebaseStorage.getInstance().getReference().child("StayImages");
        addStayButton = (FloatingActionButton) root.findViewById(R.id.addStayButton);
        recyclerView = (RecyclerView) root.findViewById(R.id.add_stay_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        addStayList = new ArrayList<>();
        addStayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        /*
        adapter = new StayAdapter(root.getContext(), addStayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListner(new StayAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                addStayList.get(position);
                loadFragment(new StayCardOpen());
            }
        });
        */

        return root;
    }

    public void openDialog(){
        StayDialog stayDialog = new StayDialog();
        stayDialog.setTargetFragment(AddStays.this, 1);
        stayDialog.show(getFragmentManager(), "AddStayFragment");
    }

    @Override
    public void sendBackToFragment(String hostName, String Place, String Date, String City) {
        rHostName = hostName;
        rPlace = Place;
        rDate = Date;
        rCity = City;

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uName = dataSnapshot.child("username").getValue().toString();
                databaseReference.child(rCity).child(firebaseUser.getUid()).child("stay_name").setValue(uName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        newStay = new Stay();
        newStay.stay_id = firebaseUser.getUid();
        newStay.stay_name = rName;
        newStay.stay_person = rHostName;
        newStay.hostDate = rDate;
        //newStay.rating = Double.valueOf(0);

        //Log.d("TAG", "username got:" + uName);
        addStayList.add(newStay);
        databaseReference.child(rCity).child(firebaseUser.getUid()).setValue(newStay);

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
