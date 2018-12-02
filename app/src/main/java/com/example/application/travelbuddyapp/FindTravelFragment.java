package com.example.application.travelbuddyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class FindTravelFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    TravelAdapter adapter;
    RecyclerView recyclerView;
    List<travel> travelList;
    View view;

    public FindTravelFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FindTravelFragment newInstance(String param1, String param2) {
        FindTravelFragment fragment = new FindTravelFragment();
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
        view = inflater.inflate(R.layout.fragment_find_travel, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.travel_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        Log.i("gggg", "++ ON START ++");
        travelList = new ArrayList<>();
        travelList.add(
                new travel(1, R.drawable.profpic, "Goa(India)", "Naman Kumar", "18", "December End"));
        travelList.add(
                new travel(2, R.drawable.profpic, "Manali(India)", "Chandan Prajapati", "5", "Jab Bolo"));
        travelList.add(
                new travel(3, R.drawable.profpic, "Miami(Florida)", "Ashwat", "2", "Papa batayenge"));
        travelList.add(
                new travel(4, R.drawable.profpic, "Sydney(Australia)", "Dushyant", "4", "This week"));
        adapter = new TravelAdapter(view.getContext(), travelList);
        Log.i("hhhh", "done");
        recyclerView.setAdapter(adapter);

        SearchView searchView =(SearchView) view.findViewById(R.id.travel_searchbar);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        RadioButton radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
        RadioButton radioButton2 = (RadioButton) view.findViewById(R.id.radioButton2);
        FloatingActionButton fab =(FloatingActionButton) view.findViewById(R.id.travel_addfab);

        return view;
    }





    @Override
    public void onDetach() {
        super.onDetach();

    }

}
