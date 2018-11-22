package com.example.application.travelbuddyapp;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class AddStaysFragment extends Fragment implements StayDialog.StayDialogListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FloatingActionButton addStayButton;
    StayAdapter adapter;
    RecyclerView recyclerView;
    List<Stay> addStayList;
    Stay newStay;
    Uri imageUri;
    View root;
    Stay stayFromFB;
    String fHostName, fStayName, fDate, fStayId, fCity, fRating, fImage;
    String rHostName, rPlace, rDate, rCity, rStayName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_places, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Stay");
        storageReference = FirebaseStorage.getInstance().getReference().child("User-Images");
        addStayButton = (FloatingActionButton) root.findViewById(R.id.addStayButton);
        recyclerView = (RecyclerView) root.findViewById(R.id.add_stay_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        addStayList = new ArrayList<>();
        int drawableId = R.drawable.defaulthouse;
        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getContext().getResources().getResourcePackageName(drawableId)
                + '/' + getContext().getResources().getResourceTypeName(drawableId)
                + '/' + getContext().getResources().getResourceEntryName(drawableId));
        Log.d("TAG", "This is uri:" + imageUri);

        addStayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                    Log.d("TAG", "HELL NO" + snapshot1.getValue().toString());
                    if (snapshot1.child("unique_id").getValue().toString().equals(firebaseUser.getUid())) {
                        fHostName = snapshot1.child("stay_person").getValue().toString();
                        fStayName = snapshot1.child("stay_name").getValue().toString();
                        fDate = snapshot1.child("hostDate").getValue().toString();
                        fRating = snapshot1.child("rating").getValue().toString();
                        fStayId = snapshot1.child("stay_id").getValue().toString();
                        fCity = snapshot1.child("city").getValue().toString();
                        fImage = snapshot1.child("image").getValue().toString();
                        stayFromFB = new Stay(fStayId, fImage, fStayName, fHostName, fRating, fDate, fCity);
                        addStayList.add(stayFromFB);
                        //furtherAction(root);
                    }
                }
            }
            @Override public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        furtherAction(root);
        return root;
    }

    public void furtherAction(View root){
        adapter = new StayAdapter(root.getContext(), addStayList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListner(new StayAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Stay query = addStayList.get(position);
                Toast.makeText(getContext(), "STAY ID "+query.city, Toast.LENGTH_SHORT).show();

                ArrayList<Requests> stay_requests = getRequest(query.stay_id,query.city);

                Bundle bunfrag = new Bundle();
                bunfrag.putParcelableArrayList("requests",stay_requests);
                bunfrag.putString("city",query.city);
                bunfrag.putString("stay_id",query.stay_id);
                AddedStayRequests added_stay_requests = new AddedStayRequests();
                added_stay_requests.setArguments(bunfrag);

                loadFragment(added_stay_requests);
            }
        });
    }

    public ArrayList<Requests> getRequest(String id,String city)
    {
        Log.d("TAH","IN "+id);
        final ArrayList<Requests> requests = new ArrayList<Requests>();
        DatabaseReference requestRef = FirebaseDatabase.getInstance().getReference().child("Stay").child(city).child(id).child("requests");
        requestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for ( DataSnapshot data : dataSnapshot.getChildren() )
                {
                    Log.d("TAH","INSIDE");
                    Requests r = new Requests();
                    r.NumberOfTraveller = data.child("NumberOfTraveller").getValue().toString();
                    r.dateToStay = data.child("dateToStay").getValue().toString();
                    //r.username = data.child("username").getValue().toString();
                    r.user_id = data.child("user_id").getValue().toString();
                    Log.d("TAG","VALUES "+r.NumberOfTraveller+"  "+r.dateToStay+"  "+r.username+"  "+r.user_id);
                    requests.add(r);
                }
            }
            @Override public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        return requests;
    }

    public void openDialog(){
        StayDialog stayDialog = new StayDialog();
        stayDialog.setTargetFragment(AddStaysFragment.this, 1);
        stayDialog.show(getFragmentManager(), "AddStayFragment");
    }

    @Override
    public void sendBackToFragment(String stayName, String hostName, String Place, String Date, String City) {
        rStayName = stayName;
        rHostName = hostName;
        rPlace = Place;
        rDate = Date;
        rCity = City;

        //Checking
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long getChildrenCount = 0;
                if (dataSnapshot.hasChild(rCity)){
                    getChildrenCount = dataSnapshot.getChildrenCount();
                    Log.d("TAG HAI BAS", "Children count inside:" + getChildrenCount);
                }
                else getChildrenCount = 0;
                Log.d("TAG HAI BAS", "Children count outside:" + getChildrenCount);
                String addingChild = "User" + (getChildrenCount+1);
                databaseReference.child(rCity).child(addingChild).child("stay_id").setValue(addingChild);
                databaseReference.child(rCity).child(addingChild).child("stay_person").setValue(rHostName);
                databaseReference.child(rCity).child(addingChild).child("stay_name").setValue(rStayName);
                databaseReference.child(rCity).child(addingChild).child("hostDate").setValue(rDate);
                databaseReference.child(rCity).child(addingChild).child("rating").setValue("0");
                databaseReference.child(rCity).child(addingChild).child("city").setValue(rCity);
                databaseReference.child(rCity).child(addingChild).child("image").setValue("");
                databaseReference.child(rCity).child(addingChild).child("unique_id").setValue(firebaseUser.getUid());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        //Till here
//        databaseReference.child(rCity).child(firebaseUser.getUid()).child("stay_id").setValue(firebaseUser.getUid());
//        databaseReference.child(rCity).child(firebaseUser.getUid()).child("stay_person").setValue(rHostName);
//        databaseReference.child(rCity).child(firebaseUser.getUid()).child("stay_name").setValue(rStayName);
//        databaseReference.child(rCity).child(firebaseUser.getUid()).child("hostDate").setValue(rDate);
//        databaseReference.child(rCity).child(firebaseUser.getUid()).child("rating").setValue("0");
//        databaseReference.child(rCity).child(firebaseUser.getUid()).child("city").setValue(rCity);
//        databaseReference.child(rCity).child(firebaseUser.getUid()).child("image").setValue("");
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}