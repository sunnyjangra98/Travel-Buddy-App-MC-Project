package com.example.application.travelbuddyapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StayItemDetailFragment extends Fragment {

    TextView ratingText, breifText, stayNameText, stayPersonText, offerText;
    ImageView stayImage = null;
    Button requestStayButton;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, databaseReference1;
    ImageButton calendarImageButton, travellerSub, travellerAdd;
    private int mYear, mMonth, mDay;
    TextView dateShow, travellerNumber;
    Stay stay = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stay_item_detail, container, false);
        if ( getArguments() != null )
        {
           stay = (Stay)getArguments().get(StayFragmentsActivity.STAY_FOR_DETAIL);
        }

        stayImage = root.findViewById(R.id.stayImage);
        ratingText = root.findViewById(R.id.reviewText);
        breifText = root.findViewById(R.id.stayDetail);
        stayNameText = root.findViewById(R.id.stayName);
        stayPersonText = root.findViewById(R.id.stayBy);
        offerText  = root.findViewById(R.id.stayOffers);
        requestStayButton = root.findViewById(R.id.requestStayButton);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        Log.d("TAG 2", "Gotcha:" + stay.getCity());
        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Stay").child(stay.getCity()).child(stay.getStay_id());
        calendarImageButton = root.findViewById(R.id.calendarImageButton);
        travellerAdd = root.findViewById(R.id.travellerAdd);
        travellerSub = root.findViewById(R.id.travellerSub);
        travellerNumber = root.findViewById(R.id.travellerNumber);
        dateShow = root.findViewById(R.id.dateShow);

        int not = Integer.parseInt(travellerNumber.getText().toString());
        if (not == 1)
            travellerSub.setClickable(false);

        if(stay != null){
            ratingText.setText(stay.getRatings());
            breifText.setText(stay.getBrief());
            stayNameText.setText(stay.getStay_name());
            stayPersonText.setText(stay.getStay_person());
            offerText.setText(stay.getThings_to_offer());
            Glide.with(getActivity()).load(stay.getImage()).into(this.stayImage);
        }
        travellerSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfTraveller = Integer.parseInt(travellerNumber.getText().toString());
                if (numberOfTraveller >= 2){
                    numberOfTraveller--;
                    travellerNumber.setText("" + numberOfTraveller);
                    travellerAdd.setClickable(true);
                    travellerSub.setClickable(true);
                }
                else{
                    Toast.makeText(getContext(), "Minimum number is 1", Toast.LENGTH_SHORT).show();
                    travellerSub.setClickable(false);
                }
            }
        });

        travellerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfTraveller = Integer.parseInt(travellerNumber.getText().toString());
                if (numberOfTraveller < 5){
                    numberOfTraveller++;
                    travellerNumber.setText("" + numberOfTraveller);
                    travellerAdd.setClickable(true);
                    travellerSub.setClickable(true);
                }
                else{
                    Toast.makeText(getContext(), "Maximum limit is 5", Toast.LENGTH_SHORT).show();
                    travellerAdd.setClickable(false);
                }
            }
        });

        calendarImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateShow.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        requestStayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateShow.getText().toString().equals(""))
                    Toast.makeText(getContext(), "Please choose date to stay", Toast.LENGTH_SHORT).show();
                else {
                    databaseReference1.child("requests").child(firebaseUser.getUid()).child("status").setValue("0");
                    databaseReference1.child("requests").child(firebaseUser.getUid()).child("username").setValue(stay.getStay_person());
                    databaseReference1.child("requests").child(firebaseUser.getUid() + dateShow.getText().toString()).child("status").setValue("0");
                    //databaseReference1.child("requests").child(firebaseUser.getUid()).child("username").setValue(stayPersonText);
                    databaseReference1.child("requests").child(firebaseUser.getUid()).child("user_id").setValue(firebaseUser.getUid());
                    databaseReference1.child("requests").child(firebaseUser.getUid()).child("stay_city").setValue(stay.getCity());
                    databaseReference1.child("requests").child(firebaseUser.getUid()).child("requested_stay_id").setValue(stay.getStay_id());
                    databaseReference1.child("requests").child(firebaseUser.getUid()).child("dateToStay").setValue(dateShow.getText().toString());
                    databaseReference1.child("requests").child(firebaseUser.getUid()).child("NumberOfTraveller").setValue(travellerNumber.getText().toString());

                    databaseReference.child("requestedStay").child(stay.getCity()).child(stay.getStay_id()).child("status").setValue("0");
                    databaseReference.child("requestedStay").child(stay.getCity()).child(stay.getStay_id()).child("requested_stay_name").setValue(stay.getStay_name());
                    databaseReference.child("requestedStay").child(stay.getCity()).child(stay.getStay_id()).child("requested_stay_person").setValue(stay.getStay_person());
                    Toast.makeText(getContext(), "Request Sent \nStatus: Pending", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ratingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new StayReviewFragment());
            }
        });

        return root;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack("StayItemDetailFragment");
        transaction.commit();
    }
}