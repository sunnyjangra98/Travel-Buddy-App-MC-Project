package com.example.application.travelbuddyapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TripReviewsFragment extends Fragment
{
    public void onCreate(Bundle savedOnInstance)
    {
        super.onCreate(savedOnInstance);
    }

    private ArrayList<Reviews> reviewsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReviewsAdapter mAdapter;
    TextView user_review, int_rate;
    Button reviewSubmit;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    public TripReviewsFragment() { }
    String user_input;
    String rate_int;
    String rCity , trip_id;
    String username;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stay_review, container, false);
        if ( getArguments() != null )
        {
            reviewsList = getArguments().getParcelableArrayList("your_requests");
        }
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Log.d("TAG","REACHING "+reviewsList.size());
        if ( reviewsList.size() != 0 )
        {
            rCity = reviewsList.get(0).city;
            trip_id = reviewsList.get(0).trip_id;
        }
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        user_review = root.findViewById(R.id.writeReview);
        int_rate = root.findViewById(R.id.number_rate);
        reviewSubmit = root.findViewById(R.id.submitReview);
        reviewSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_input =  user_review.getText().toString();
                rate_int = int_rate.getText().toString();
                if ( user_input.length() == 0 )
                {
                    Toast.makeText(getActivity(),"Cant Submit Empty Review ",Toast.LENGTH_LONG).show();
                }
                else
                {
                    updateReview();
                }
            }
        });
        mAdapter = new ReviewsAdapter(root.getContext(),reviewsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return root;
    }

    public void updateReview()
    {
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference();
        Ref.child("Users").child(firebaseUser.getUid()).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            username = (String) snapshot.getValue();
                        } catch (Exception e) { e.printStackTrace(); }
                    } else { } } catch (Exception e) { e.printStackTrace(); } }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference().child("travel");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long getChildrenCount = 0;
                    if (dataSnapshot.hasChild(rCity))
                    {
                        getChildrenCount = dataSnapshot.child(rCity).getChildrenCount();
                    }
                    String addingChild = "User" + (getChildrenCount + 1);
                    databaseReference.child(rCity).child(trip_id).child("reviews").child(addingChild).child("text").setValue(user_input);
                    databaseReference.child(rCity).child(trip_id).child("reviews").child(addingChild).child("city").setValue(rCity);
                    databaseReference.child(rCity).child(trip_id).child("reviews").child(addingChild).child("review_rating").setValue(rate_int);
                    databaseReference.child(rCity).child(trip_id).child("reviews").child(addingChild).child("review_username").setValue(username);
                    databaseReference.child(rCity).child(trip_id).child("reviews").child(addingChild).child("trip_id").setValue(trip_id);
                    user_review.setText("");
                    int_rate.setText("");
                    Toast.makeText(getActivity()," Review Updated ",Toast.LENGTH_LONG).show();
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

}
