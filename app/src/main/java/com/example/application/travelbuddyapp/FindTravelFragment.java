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
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import com.google.firebase.storage.StorageReference;

import javax.xml.transform.Result;

public class FindTravelFragment extends Fragment implements TravelDialog.TravelDialogListener{
    public static int DISPLAY_REQUEST_CODE=2;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TravelAdapter adapter;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    List<travel> travelList;
    View root;
    String selectedItemText;
    SearchView searchView;

    travel travelfromfb;
    String ftripName, ftripDetail, fPlace, fDate, fCity;
    String rtripName, rtripDetail, rPlace, rDate, rCity, rInterested;

    String query = "";

    DatabaseReference mDatabase;
    DatabaseReference databaseReference;
    DatabaseReference reviewReference;
    FirebaseRecyclerAdapter<travel, TravelViewHolder> firebaseRecyclerAdapter;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public FindTravelFragment() {
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
        root =  inflater.inflate(R.layout.fragment_find_travel, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("travel");
        fab = (FloatingActionButton) root.findViewById(R.id.travel_addfab);
        searchView = root.findViewById(R.id.travel_searchbar);
        recyclerView = (RecyclerView) root.findViewById(R.id.travel_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        travelList = new ArrayList<>();
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.travel_addfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        return root;
    }
    public void openDialog(){
        TravelDialog travelDialog = new TravelDialog();
        travelDialog.setTargetFragment(FindTravelFragment.this, 1);
        travelDialog.show(getFragmentManager(), "FindTravelFragment");
    }

    @Override
    public void sendBackToFragment(String tripName, String tripDetail, String Place, String Date, String City) {
        rtripName = tripName;
        rtripDetail = tripDetail;
        rPlace = Place;
        rDate = Date;
        rCity = City;

        //Checking
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long getChildrenCount = 0;
                boolean changed = false;
                if (!changed) {
                    if (dataSnapshot.hasChild(rCity)) {
                        getChildrenCount = dataSnapshot.child(rCity).getChildrenCount();
                        Log.d("TAG HAI BAS", "Children count inside:" + dataSnapshot.child(rCity).getChildrenCount());
                        Log.d("TAG HAI BAS", "Data count:" + getChildrenCount);
                    } else getChildrenCount = 0;
                    Log.d("TAG HAI BAS", "Children count outside:" + getChildrenCount);
                    String addingChild = "User" + (getChildrenCount + 1);
                    databaseReference.child(rCity).child(addingChild).child("travel_id").setValue(addingChild);
                    databaseReference.child(rCity).child(addingChild).child("place").setValue(rCity);
                    databaseReference.child(rCity).child(addingChild).child("interested").setValue(rtripName);
                    databaseReference.child(rCity).child(addingChild).child("no_of_going").setValue(rtripDetail);
                    databaseReference.child(rCity).child(addingChild).child("details").setValue(rDate);
                    databaseReference.child(rCity).child(addingChild).child("image").setValue(rPlace);
                    databaseReference.child(rCity).child(addingChild).child("unique_id").setValue(firebaseUser.getUid());
                    changed = true;
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        // searchView = root.findViewById(R.id.staySearchView);
        try {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    if (s.isEmpty())
                        return false;
                    else if (s.equals(""))
                        return false;
                    else {
                        travelList.clear();
                        selectedItemText = s;
                        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("Recent Stay Search");
                        Ref.child("search").setValue(selectedItemText).toString();
                        firebaseQuery();
                        searchView.clearFocus();
                        return true;
                    }
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    query = s;
                    return false;
                }
            });
        }
        catch (Exception e){
            Log.i("Find STAY", "Exception"+ e.getMessage());
        }
    }

    public void firebaseQuery()
    {
        //selectedItemText = "Delhi";
        mDatabase = FirebaseDatabase.getInstance().getReference().child("travel").child(selectedItemText);
        mDatabase.keepSynced(true);
        DatabaseReference Ref = FirebaseDatabase.getInstance().getReference().child("travel").child(selectedItemText);
        Query query = Ref.orderByKey();
        Log.i("Find STAY", "REACH ");
        FirebaseRecyclerOptions<travel> options = new FirebaseRecyclerOptions.Builder<travel>().setQuery(query, travel.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<travel, TravelViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TravelViewHolder holder, int position, @NonNull travel travel2)
            {
                Log.d("KEY","ID REACHING ");

                //final String travel_id = travel2.gettravel_id();
                String travel_gointto = travel2.getno_of_going();
                String travel_detail=travel2.getdetails();
                String travel_host = travel2.gethost();
                String travel_place = travel2.getplace();
                String travel_image = travel2.getImage();
                String travel_interested = travel2.getInterested();

                final ArrayList<Reviews> reviews = new ArrayList<>();

                holder.textView_place.setText(travel_place);
                holder.textView_host.setText(travel_host);
                holder.textView_detail.setText(travel_detail);
                holder.textView_interested.setText(travel_interested);
                holder.textView_goingno.setText(String.valueOf(travel_gointto));


                final TravelItemDetailFragment stay_detail = new TravelItemDetailFragment();
                final Bundle bunfrag2 = new Bundle();
                bunfrag2.putSerializable(StayFragmentsActivity.STAY_FOR_DETAIL, travel2 );

                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i =new Intent(getActivity(), StayFragmentsActivity.class);
                        i.putExtra(StayFragmentsActivity.FRAGMENT_NAME, StayFragmentsActivity.DETAIL_FRAGMENT_NAME);
                        i.putExtra(StayFragmentsActivity.STAY_FOR_DETAIL_BUNDLE, bunfrag2 );//bunfrag define 4 lines above this line
                        startActivityForResult(i, DISPLAY_REQUEST_CODE);
                    }
                });

                holder.interested.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Interested",Toast.LENGTH_SHORT).show();
                    }
                });
                holder.going.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Going",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public TravelViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.travel_list_item, parent, false);
                TravelViewHolder holder = new TravelViewHolder(view);
                return new TravelViewHolder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
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


    class TravelViewHolder extends RecyclerView.ViewHolder {
        TextView textView_host, textView_place, textView_detail, textView_goingno, textView_interested;
        ImageView imageView;
        ImageButton interested , going;
        public TravelViewHolder(View itemView) {
            super(itemView);
            interested = itemView.findViewById(R.id.travel_button_willjoin);
            going = itemView.findViewById(R.id.travel_button_interested);
            textView_place =itemView.findViewById(R.id.travel_place);
            textView_host =itemView.findViewById(R.id.travel_host);
            textView_detail = itemView.findViewById(R.id.travel_details);
            textView_goingno = itemView.findViewById(R.id.travel_goingno);
            imageView = itemView.findViewById(R.id.travel_hostpic);
            textView_interested = itemView.findViewById(R.id.travel_interested);
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
    public void onResume() {
        super.onResume();
        recentSearched();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
