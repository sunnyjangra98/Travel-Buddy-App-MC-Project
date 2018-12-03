package com.example.application.travelbuddyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {

    SharedPreferences sp;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    TextView EditProfile, StayRequests, RequestedStays, AddStay, SignOut, nameView, emailText;
    ImageView profileImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        storageReference = FirebaseStorage.getInstance().getReference().child("User-Images").child(firebaseUser.getUid());

        EditProfile =  root.findViewById(R.id.editProfile);
        StayRequests =  root.findViewById(R.id.stayRequests);
        RequestedStays = root.findViewById(R.id.requestedStays);
        AddStay =  root.findViewById(R.id.addStay);
        SignOut =  root.findViewById(R.id.signOut);
        nameView = (TextView) root.findViewById(R.id.nameView);
        profileImage = (ImageView) root.findViewById(R.id.profileImage);
        emailText = (TextView) root.findViewById(R.id.emailText);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameView.setText(dataSnapshot.child("username").getValue().toString());
                emailText.setText(dataSnapshot.child("email").getValue().toString());
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getContext()).load(uri).into(profileImage);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity().getApplicationContext(), AccountActivity.class);
                i.putExtra(AccountActivity.FRAGMENT_NAME, AccountActivity.EDIT_PROFILE_FRAGMENT_NAME);
                getActivity().startActivity(i);

            }
        });

        StayRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity().getApplicationContext(), AccountActivity.class);
                i.putExtra(AccountActivity.FRAGMENT_NAME, AccountActivity.STAY_REQUESTS_FRAGMENT_NAME);
                startActivity(i);
            }
        });

        RequestedStays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity().getApplicationContext(), AccountActivity.class);
                i.putExtra(AccountActivity.FRAGMENT_NAME, AccountActivity.REQUESTED_STAY_FRAGMENT_NAME);
                startActivity(i);
            }
        });
        AddStay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity().getApplicationContext(), AccountActivity.class);
                i.putExtra(AccountActivity.FRAGMENT_NAME, AccountActivity.ADD_STAY_FRAGMENT_NAME);
                getActivity().startActivity(i);
               // loadFragment(new AddStaysFragment());
            }
        });

        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(getActivity().getApplicationContext(), "Logout successful", Toast.LENGTH_LONG).show();
                getActivity().finish();
                Intent toLogin = new Intent(getActivity().getApplicationContext(), LoginSignupActivity.class);
                getActivity().startActivity(toLogin);
                sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sp.edit().putBoolean("logged",false).apply();
                sp.edit().putString("User","").apply();
                sp.edit().putString("password","").apply();
//                sp.edit().putBoolean("logged",false).apply();
//                sp.edit().putString("User","").apply();
//                sp.edit().putString("password","").apply();
            }
        });

        return root;
    }

}
