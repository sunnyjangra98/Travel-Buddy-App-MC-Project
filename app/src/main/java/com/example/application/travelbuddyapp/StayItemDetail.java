package com.example.application.travelbuddyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StayItemDetail extends Fragment
{
    private List<Reviews> reviewList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReviewsAdapter mAdapter;
    String detail,max_people,interests,things_to_offer;
    TextView stay_max,stay_interests,stay_offer,stay_details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stay_item_detail, container, false);
        if ( getArguments() != null )
        {
            detail = getArguments().getString("details");
            max_people = getArguments().getString("max_people");
            interests = getArguments().getString("interests");
            things_to_offer = getArguments().getString("things_to_offer");
            //reviewList = getArguments().getParcelableArrayList("reviews");
        }
        stay_max = root.findViewById(R.id.maxStayPeople);
        stay_interests = root.findViewById(R.id.stayInterests);
        stay_offer = root.findViewById(R.id.stayOffer);
        stay_details = root.findViewById(R.id.stayDetails);

        stay_max.setText(max_people);
        stay_interests.setText(interests);
        stay_offer.setText(things_to_offer);
        stay_details.setText(detail);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        mAdapter = new ReviewsAdapter(reviewList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        //prepareReviews();
        return root;
    }

    public void prepareReviews()
    {/*
        Reviews movie = new Reviews("Mad Max: Fury Road", "Action & Adventure",5.1);
        reviewList.add(movie);
        movie = new Reviews("Inside Out", "Animation, Kids & Family", 1.0);
        reviewList.add(movie);
        movie = new Reviews("Inside Out", "Animation, Kids & Family", 1.0);
        reviewList.add(movie);
        movie = new Reviews("Inside Out", "Animation, Kids & Family", 1.0);
        reviewList.add(movie);
        movie = new Reviews("Inside Out", "Animation, Kids & Family", 1.0);
        reviewList.add(movie);
        movie = new Reviews("Inside Out", "Animation, Kids & Family", 1.0);
        reviewList.add(movie);
        movie = new Reviews("Inside Out", "Animation, Kids & Family", 1.0);
        reviewList.add(movie);
        movie = new Reviews("Inside Out", "Animation, Kids & Family", 1.0);
        reviewList.add(movie);*/
        mAdapter.notifyDataSetChanged();
    }
}