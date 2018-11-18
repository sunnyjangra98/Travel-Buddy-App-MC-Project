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

public class AddStays extends Fragment implements StayDialog.StayDialogListener {

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
    static int numberOfTimes = 0;
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
        storageReference = FirebaseStorage.getInstance().getReference().child("StayImages");
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
                    if (snapshot1.hasChild(firebaseUser.getUid())) {
                        Log.d("INSIDE", "yay i got this" + snapshot1.getValue().toString());
                        Stay stayFromFB;
                        String fHostName, fStayName, fDate, fStayId;
                        float fRating;
                        Uri fImage;
                        fHostName = snapshot1.child(firebaseUser.getUid()).child("stay_person").getValue().toString();
                        fStayName = snapshot1.child(firebaseUser.getUid()).child("stay_name").getValue().toString();
                        fDate = snapshot1.child(firebaseUser.getUid()).child("hostDate").getValue().toString();
                        fRating = (long) snapshot1.child(firebaseUser.getUid()).child("rating").getValue();
                        fStayId = snapshot1.child(firebaseUser.getUid()).child("stay_id").getValue().toString();
                        String fNewImage = snapshot1.child(firebaseUser.getUid()).child("image").getValue().toString();
                        fImage = Uri.parse(fNewImage);
                        Log.d("TAG", "imageUri:" + fImage.toString());
                        stayFromFB = new Stay(fStayId, fImage, fStayName, fHostName, fRating, fDate);
                        addStayList.add(stayFromFB);
                        furtherAction(root);
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        if (numberOfTimes == 0) {
//            numberOfTimes++;
//            databaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }

        furtherAction(root);

        return root;
    }

    public void furtherAction(View root){
        adapter = new StayAdapter(root.getContext(), addStayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListner(new StayAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                addStayList.get(position);
                loadFragment(new StayCardOpen());
            }
        });
    }

    public void openDialog(){
        StayDialog stayDialog = new StayDialog();
        stayDialog.setTargetFragment(AddStays.this, 1);
        stayDialog.show(getFragmentManager(), "AddStayFragment");
    }

    @Override
    public void sendBackToFragment(String stayName, String hostName, String Place, String Date, String City) {
        rStayName = stayName;
        rHostName = hostName;
        rPlace = Place;
        rDate = Date;
        rCity = City;
        StorageReference sRef = storageReference.child(firebaseUser.getUid());
        newStay = new Stay(firebaseUser.getUid(), imageUri, rStayName, rHostName, 0, rDate);
        sRef.putFile(imageUri);
        Log.d("TAG", "imageUri:" + imageUri.toString());

        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                databaseReference.child(rCity).child(firebaseUser.getUid()).child("image").setValue(uri.toString());
            }
        });

        addStayList.add(newStay);
        databaseReference.child(rCity).child(firebaseUser.getUid()).child("hostDate").setValue(rDate);
        databaseReference.child(rCity).child(firebaseUser.getUid()).child("rating").setValue(0);
        databaseReference.child(rCity).child(firebaseUser.getUid()).child("stay_id").setValue(firebaseUser.getUid());
        databaseReference.child(rCity).child(firebaseUser.getUid()).child("stay_person").setValue(rHostName);
        databaseReference.child(rCity).child(firebaseUser.getUid()).child("stay_name").setValue(rStayName);
        numberOfTimes++;
        furtherAction(root);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }
}
