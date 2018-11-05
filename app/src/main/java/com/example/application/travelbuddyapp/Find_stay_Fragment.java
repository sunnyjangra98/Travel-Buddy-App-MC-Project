package com.example.application.travelbuddyapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;

public class Find_stay_Fragment extends Fragment {
    public static int SEARCH_REQUEST_CODE =1;
    public static int DISPLAY_REQUEST_CODE=2;

    public static String STAY_ = "STAY_";
    StayAdapter adapter;
    RecyclerView recyclerView;
    List<Stay> stayList;
    View root;
    String selectedItemText;

    DatabaseReference mDatabase;
    FirebaseRecyclerAdapter<Stay,StayViewHolder> firebaseRecyclerAdapter;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public Find_stay_Fragment() {
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
        root =  inflater.inflate(R.layout.fragment_find_stay_, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.stay_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        stayList = new ArrayList<>();
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

    public void firebaseQuery()
    {
        //selectedItemText = "Chennai";
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Stay").child(selectedItemText);
        mDatabase.keepSynced(true);
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("Stay").child(selectedItemText);
        Query query = Ref.orderByKey();
        FirebaseRecyclerOptions<Stay> options = new FirebaseRecyclerOptions.Builder<Stay>().setQuery(query, Stay.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Stay, StayViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StayViewHolder holder, int position, @NonNull Stay stay)
            {
                holder.textView_stay.setText(stay.getStay_name());
                holder.textView_person.setText(stay.getStay_person());
                holder.textView_rating.setText(String.valueOf(stay.getRating()));
                holder.textView_brief.setText(stay.getBrief());
                Glide.with(getActivity()).load(stay.getImage()).into(holder.imageView);
            }
            @Override
            public Find_stay_Fragment.StayViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stay_list_item, parent, false);
                StayViewHolder holder = new StayViewHolder(view);
                return new Find_stay_Fragment.StayViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    class StayViewHolder extends RecyclerView.ViewHolder {
        TextView textView_person, textView_stay, textView_brief, textView_rating;
        ImageView imageView;
        public StayViewHolder(View itemView) {
            super(itemView);
            textView_stay =itemView.findViewById(R.id.textView_stay);
            textView_person =itemView.findViewById(R.id.textView_person);
            textView_brief = itemView.findViewById(R.id.stay_brief);
            textView_rating = itemView.findViewById(R.id.rating_text);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    /*
    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
    */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);Stay toAdd = null;
        if(resultCode == Activity.RESULT_OK){
            if(!data.getExtras().isEmpty()){
                stayList.clear();
                Bundle b = data.getExtras();
                selectedItemText = b.getString("searchQuery");
                firebaseQuery();
                //for(int i=0;i<b.size();i++){ stayList.add((Stay) b.getSerializable(Find_stay_Fragment.STAY_+(i+1))); }
                //adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}