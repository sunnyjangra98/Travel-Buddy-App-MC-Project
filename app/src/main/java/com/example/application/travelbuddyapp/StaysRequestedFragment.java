package com.example.application.travelbuddyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class StaysRequestedFragment extends Fragment {

    private ArrayList<Requested_Stay> requestedList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Requested_StayAdapter mAdapter;

    public StaysRequestedFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
}
