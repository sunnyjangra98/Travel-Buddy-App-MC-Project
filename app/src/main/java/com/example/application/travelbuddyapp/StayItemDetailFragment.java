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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class StayItemDetailFragment extends Fragment
{

    TextView ratingText, breifText, stayNameText, stayPersonText, offerText;
    ImageView stayImage = null;

    Stay stay = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stay_item_detail, container, false);
        if ( getArguments() != null )
        {
           stay = (Stay)getArguments().get(StayFragmentsActivity.STAY_FOR_DETAIL);

        }

        stayImage = root.findViewById(R.id.stayImage);
        ratingText = root.findViewById(R.id.reviewText);
        breifText = root.findViewById(R.id.stayDetail);
        stayNameText =root.findViewById(R.id.stayName);
        stayPersonText = root.findViewById(R.id.stayBy);
        offerText  = root.findViewById(R.id.stayOffers);

        if(stay != null){
            ratingText.setText(stay.getRatings());
            breifText.setText(stay.getBrief());
            stayNameText.setText(stay.getStay_name());
            stayPersonText.setText(stay.getStay_person());
            offerText.setText(stay.getThings_to_offer());
            Glide.with(getActivity()).load(stay.getImage()).into(this.stayImage);
        }
        //add reviews in new fragment;
        return root;
    }

}