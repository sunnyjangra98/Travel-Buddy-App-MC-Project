package com.example.application.travelbuddyapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class TravelDialog extends AppCompatDialogFragment {
    EditText TravelAddName, TravelAddDetail, TravelAddPlace, TravelAddDate, TravelAddCity;
    ImageButton calendarButton;
    private TravelDialog.TravelDialogListener listner;
    private int mYear, mMonth, mDay;
    String tripName, tripDetail, date, city, place;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addtraveldialog, null);

        TravelAddName = (EditText) view.findViewById(R.id.TravelAddName);
        TravelAddDetail = (EditText) view.findViewById(R.id.TravelAddDetail);
        TravelAddPlace = (EditText) view.findViewById(R.id.TravelAddPlace);
        TravelAddDate = (EditText) view.findViewById(R.id.TravelAddDate);
        TravelAddCity = (EditText) view.findViewById(R.id.TravelAddCity);
        calendarButton = (ImageButton) view.findViewById(R.id.calendarButton);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TravelAddDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDialog().dismiss();
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tripName = TravelAddName.getText().toString();
                        tripDetail = TravelAddDetail.getText().toString();
                        place = TravelAddPlace.getText().toString();
                        date = TravelAddDate.getText().toString();
                        city = TravelAddCity.getText().toString();
                        if (TravelAddName.getText().toString().equals("") ||
                                TravelAddDetail.getText().toString().equals("") ||
                                TravelAddPlace.getText().toString().equals("") ||
                                TravelAddDate.getText().toString().equals("") ||
                                TravelAddCity.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Please fill all fields \n   Nothing to Add", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            listner.sendBackToFragment(tripName, tripDetail, place, date, city);
                            getDialog().dismiss();
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listner = (TravelDialog.TravelDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement TravelDialogListner");
        }
    }

    public interface TravelDialogListener{
        void sendBackToFragment(String tripName, String tripDetail, String Place, String Date, String City);
    }
}
