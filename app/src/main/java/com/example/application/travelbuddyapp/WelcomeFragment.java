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

public class WelcomeFragment extends Fragment {

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
        root = inflater.inflate(R.layout.fragment_welcome, container, false);
        return root;
    }


    @Override
    public void onStart(){
        super.onStart();
        int i =0;


    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
