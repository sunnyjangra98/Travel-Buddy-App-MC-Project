package com.example.application.travelbuddyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

public class AddedStayRequests extends Fragment
{
    private ArrayList<Requests> requestsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("TAG","REACHING REQUEST");
        View root = inflater.inflate(R.layout.fragment_added_stay_requests, container, false);
        if ( getArguments() != null )
        {
            requestsList = getArguments().getParcelableArrayList("requests");
        }
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        mAdapter = new RequestsAdapter(root.getContext(),requestsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        return root;
    }
}