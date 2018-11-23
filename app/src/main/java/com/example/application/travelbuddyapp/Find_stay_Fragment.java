package com.example.application.travelbuddyapp;

import android.app.Activity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.Glide;
import com.google.firebase.database.ValueEventListener;
import javax.xml.transform.Result;

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
    DatabaseReference databaseReference;
    DatabaseReference reviewReference;
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

                final String stay_id = stay.getStay_id();
                String stay_name = stay.getStay_name();
                String stay_person = stay.getStay_person();
                String rating = stay.getRating();
                String date = stay.getHostDate();

                final ArrayList<Reviews> reviews = new ArrayList<>();

                Log.d("KEY","ID ID "+stay_id);
                /*
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Stay").child(selectedItemText);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                            String key = snapshot1.getKey();
                            Log.d("KEY","KEY DEKHO "+key);
                            //snapshot1.child("stay_id").getValue().equals(stay_id)
                            if ( snapshot1.hasChild("stay_id") ) {
                                Log.d("KEY","IF INSIDE");
                                reviewReference = FirebaseDatabase.getInstance().getReference().child("Stay").child(key).child("reviews");
                                reviewReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
                                            Log.d("KEY","INSIDE "+snapshot2.getKey());
                                            String username = snapshot2.child("user_id").getValue().toString();
                                            String text = snapshot2.child("review_text").getValue().toString();
                                            String rating = snapshot2.child("rating").getValue().toString();
                                            Log.d("KEY","RATE "+rating);
                                            Reviews r = new Reviews(username,text,rating);
                                            reviews.add(r);
                                        }
                                    }
                                    @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
                                });
                            }
                        }
                    }
                    @Override public void onCancelled(@NonNull DatabaseError databaseError) { } });
                    */
                holder.textView_stay.setText(stay_name);
                holder.textView_person.setText(stay_person);
                holder.textView_rating.setText(rating);
                holder.textView_date.setText(date);
                Glide.with(getActivity()).load(stay.getImage()).into(holder.imageView);

                final StayItemDetailFragment stay_detail = new StayItemDetailFragment();
                final Bundle bunfrag = new Bundle();
                bunfrag.putSerializable(StayFragmentsActivity.STAY_FOR_DETAIL, stay );

                holder.callButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String phoneNumber = "tel:"+"9717439438";
                        Uri number = Uri.parse(phoneNumber);
                        Log.i("phone call", "onClick: Phone call");

                        Intent callIntent = new Intent(Intent.ACTION_CALL, number);

                        if (ActivityCompat.checkSelfPermission( getActivity(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            Log.i("phone call", "onClick: Phone call true");
                            startActivity(callIntent);
                        }


                    }
                });
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i =new Intent(getActivity(), StayFragmentsActivity.class);
                        i.putExtra(StayFragmentsActivity.FRAGMENT_NAME, StayFragmentsActivity.DETAIL_FRAGMENT_NAME);
                        i.putExtra(StayFragmentsActivity.STAY_FOR_DETAIL_BUNDLE, bunfrag );//bunfrag define 4 lines above this line
                        startActivityForResult(i, DISPLAY_REQUEST_CODE);
                    }
                });

            }
            @Override
            public StayViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stay_list_item, parent, false);
                StayViewHolder holder = new StayViewHolder(view);
                return new StayViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void recentSearched()
    {
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference();
        Log.d("RECENT"," OUTSIDE FUNCTION ");
        Ref.child("Recent-Stay-Search").child("search").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if (snapshot.getValue() != null) {
                        try {
                            selectedItemText = (String) snapshot.getValue();
                            Log.d("TAG", "VALUE "+selectedItemText);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d("TAG", "IT IS NULL");
                    }
                } catch (Exception e) {
                    Log.d("TAG", "ERROR");
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        if ( selectedItemText != null && selectedItemText != "" ) {
            firebaseQuery();
        }
    }

    class StayViewHolder extends RecyclerView.ViewHolder {
        TextView textView_person, textView_stay, textView_date, textView_rating;
        ImageButton callButton;
        ImageView imageView;
        public StayViewHolder(View itemView) {
            super(itemView);
            textView_stay =itemView.findViewById(R.id.textView_stay);
            textView_person =itemView.findViewById(R.id.textView_person);
            textView_date = itemView.findViewById(R.id.stay_date);
            callButton = itemView.findViewById(R.id.call_button);
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
                DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("Recent Stay Search");
                Ref.child("search").setValue(selectedItemText).toString();
                firebaseQuery();
                //for(int i=0;i<b.size();i++){ stayList.add((Stay) b.getSerializable(Find_stay_Fragment.STAY_+(i+1))); }
                //adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        recentSearched();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}