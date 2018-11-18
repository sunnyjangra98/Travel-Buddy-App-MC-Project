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
    Uri imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
               return false;
           }
       });

       /*
       These are just to check the list
       Update list by quering the database and update list then send it to actvity which sent intent to this
        */

        stayList = new ArrayList<>();
        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.stay_sample_image));;
        stayList.add(
                new Stay("1", imageUri, "Tera Ghar", "YOYO ji Dwara", 4.5f, "Padharo mhare desh"));

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
                for(int i= 0; i<stayList.size();i++){
                    data.putExtra(Find_stay_Fragment.STAY_+(i+1),stayList.get(i));
                }

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
