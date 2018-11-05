package com.example.application.travelbuddyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class Find_stay_Fragment extends Fragment {
    public static int SEARCH_REQUEST_CODE =1;
    public static int DISPLAY_REQUEST_CODE=2;

    public static String STAY_ = "STAY_";
    StayAdapter adapter;
    RecyclerView recyclerView;

    List<Stay> stayList;
    View root;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public Find_stay_Fragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Find_stay_Fragment newInstance(String param1, String param2) {
        Find_stay_Fragment fragment = new Find_stay_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       root =  inflater.inflate(R.layout.fragment_find_stay_, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.stay_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        stayList = new ArrayList<>();
        stayList.add(
                new Stay(1, R.drawable.stay_sample_image, "Mera Ghar", "Baba ji Dwara", "4.5", "Padharo mhare desh"));
        stayList.add(
                new Stay(2, R.drawable.stay_sample_image, "Mera Ghar", "Baba ji Dwara", "4.5", "Padharo mhare desh"));
        stayList.add(
                new Stay(3, R.drawable.stay_sample_image, "Mera Ghar", "Baba ji Dwara", "4.5", "Padharo mhare desh"));
        stayList.add(
                new Stay(4, R.drawable.stay_sample_image, "Mera Ghar", "Baba ji Dwara", "4.5", "Padharo mhare desh"));
       adapter = new StayAdapter(root.getContext(), stayList);
        recyclerView.setAdapter(adapter);

        Button searchButton = root.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(), StayFragmentsActivity.class);
                i.putExtra(StayFragmentsActivity.FRAGMENT_NAME, StayFragmentsActivity.SEARCH_FRAGMENT_NAME);

                startActivityForResult(i, SEARCH_REQUEST_CODE);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);Stay toAdd = null;
        if(resultCode == Activity.RESULT_OK){
            if(!data.getExtras().isEmpty()){
                stayList.clear();
                Bundle b = data.getExtras();
                for(int i=0;i<b.size();i++){
                    stayList.add((Stay) b.getSerializable(Find_stay_Fragment.STAY_+(i+1)));
                }
                adapter.notifyDataSetChanged();

            }
        }

    }



    @Override
    public void onDetach() {
        super.onDetach();

    }



}
