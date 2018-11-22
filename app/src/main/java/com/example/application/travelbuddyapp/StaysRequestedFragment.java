package com.example.application.travelbuddyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
<<<<<<< HEAD
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
=======
>>>>>>> 5bf9534eddc3ef617fb6f4d75ee5d8a2d4354e82
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

<<<<<<< HEAD
import java.util.ArrayList;

public class StaysRequestedFragment extends Fragment {

    private ArrayList<Requested_Stay> requestedList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Requested_StayAdapter mAdapter;
=======
public class StaysRequestedFragment extends Fragment {

    public StaysRequestedFragment() {

    }
>>>>>>> 5bf9534eddc3ef617fb6f4d75ee5d8a2d4354e82

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
=======

>>>>>>> 5bf9534eddc3ef617fb6f4d75ee5d8a2d4354e82
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
<<<<<<< HEAD
        Log.d("TAG","REACHING REQUEST");
        View root = inflater.inflate(R.layout.fragment_stays_requested, container, false);
        if ( getArguments() != null )
        {
            requestedList = getArguments().getParcelableArrayList("requests");
        }
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        mAdapter = new Requested_StayAdapter(root.getContext(),requestedList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return root;
    }
=======
        View root = inflater.inflate(R.layout.fragment_stays_requested, container, false);
        
        return root;
    }



>>>>>>> 5bf9534eddc3ef617fb6f4d75ee5d8a2d4354e82
}
