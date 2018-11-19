package com.example.application.travelbuddyapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.List;

public class searchStayFragment extends Fragment {

    View root;
    SearchView searchView;
    Button searchButton;
    List<Stay> stayList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public searchStayFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static searchStayFragment newInstance(String param1, String param2) {
        searchStayFragment fragment = new searchStayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String query = "Chennai";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =inflater.inflate(R.layout.fragment_search_stay, container, false);
        searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                query = s;
                return false;
            }
        });
       /*
       These are just to check the list
       Update list by quering the database and update list then send it to actvity which sent intent to this
        */
/*
        stayList = new ArrayList<>();
        stayList.add(
                new Stay(1, R.drawable.stay_sample_image, "Tera Ghar", "YOYO ji Dwara", "4.5", "Padharo mhare desh"));
        stayList.add(
                new Stay(2, R.drawable.stay_sample_image, "Tera Ghar", "YOYO ji Dwara", "4.5", "Padharo mhare desh"));
        stayList.add(
                new Stay(3, R.drawable.stay_sample_image, "Tera Ghar", "HAMARE ji Dwara", "4.5", "Padharo mhare desh"));
*/
        return root;
    }
    @Override
    public void onStart(){
        super.onStart();
        searchButton = root.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("searchQuery",query);
                //for(int i= 0; i<stayList.size();i++){
                //data.putExtra(Find_stay_Fragment.STAY_+(i+1),stayList.get(i));}
                getActivity().setResult(Activity.RESULT_OK, data);
                getActivity().finish();
            }
        });
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}

/*
package com.example.application.travelbuddyapp;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import java.util.ArrayList;
import java.util.List;
public class searchStayFragment extends Fragment {
    View root;
    SearchView searchView;
    Button searchButton;
    List<Stay> stayList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
=======

    }

>>>>>>> 70238b35ae8d025948c4beb1ad984543d914e42f
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       root =inflater.inflate(R.layout.fragment_search_stay, container, false);
<<<<<<< HEAD
       searchView = root.findViewById(R.id.searchView);
=======

       searchView = root.findViewById(R.id.searchView);

>>>>>>> 70238b35ae8d025948c4beb1ad984543d914e42f
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               return false;
           }
<<<<<<< HEAD
=======

>>>>>>> 70238b35ae8d025948c4beb1ad984543d914e42f
           @Override
           public boolean onQueryTextChange(String s) {
               return false;
           }
       });
<<<<<<< HEAD
        stayList = new ArrayList<>();
        stayList.add(
                new Stay("1", R.drawable.stay_sample_image, "Tera Ghar", "YOYO ji Dwara", "4.5", "Padharo mhare desh"));
        stayList.add(
                new Stay("2", R.drawable.stay_sample_image, "Tera Ghar", "YOYO ji Dwara", "4.5", "Padharo mhare desh"));
        stayList.add(
                new Stay("3", R.drawable.stay_sample_image, "Tera Ghar", "HAMARE ji Dwara", "4.5", "Padharo mhare desh"));
        return root;
    }
*/