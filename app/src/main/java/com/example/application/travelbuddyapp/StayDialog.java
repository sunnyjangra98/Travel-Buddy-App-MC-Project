package com.example.application.travelbuddyapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class StayDialog extends AppCompatDialogFragment {

    EditText editStayName, editHostName, editPlace, editCity, editPhone, editMaxPeoples, editDetails, editOffers;
    TextView editDate;
    Button stayImageButton;
    ImageButton calendarImageButton;
    private  StayDialogListener listner;
    private int mYear, mMonth, mDay;
    String stayName, hostName, date, city, place, phone, maxPeoples, details, offers;
    int REQUEST_CODE = 1;
    Uri imageUri;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    int drawableId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.addstaydialog, null);

        drawableId = R.drawable.defaulthouse;
        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + getContext().getResources().getResourcePackageName(drawableId)
                + '/' + getContext().getResources().getResourceTypeName(drawableId)
                + '/' + getContext().getResources().getResourceEntryName(drawableId));
        editStayName = (EditText) view.findViewById(R.id.editStayName);
        editHostName = (EditText) view.findViewById(R.id.editHostName);
        editPlace = (EditText) view.findViewById(R.id.editPlace);
        editDate = (TextView) view.findViewById(R.id.editDate);
        editCity = (EditText) view.findViewById(R.id.editCity);
        editPhone = (EditText) view.findViewById(R.id.editPhone);
        editMaxPeoples = (EditText) view.findViewById(R.id.editMaxPeoples);
        editDetails = (EditText) view.findViewById(R.id.editDetails);
        editOffers = (EditText) view.findViewById(R.id.editOffers);
        calendarImageButton = (ImageButton) view.findViewById(R.id.calendarImageButton);
        stayImageButton = (Button) view.findViewById(R.id.stayImageButton);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference().child("Stay-Images");

        stayImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, REQUEST_CODE);
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
                                editDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
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
                stayName = editStayName.getText().toString();
                hostName = editHostName.getText().toString();
                place = editPlace.getText().toString();
                date = editDate.getText().toString();
                city = editCity.getText().toString();
                phone = editPhone.getText().toString();
                maxPeoples = editMaxPeoples.getText().toString();
                details = editDetails.getText().toString();
                offers = editOffers.getText().toString();
                if (editStayName.getText().toString().equals("") ||
                        editHostName.getText().toString().equals("") ||
                        editPlace.getText().toString().equals("") ||
                        editDate.getText().toString().equals("") ||
                        editCity.getText().toString().equals("") ||
                        editPhone.getText().toString().equals("") ||
                        editDetails.getText().toString().equals("") ||
                        editOffers.getText().toString().equals("") ||
                        (editPhone.getText().toString().length() != 10) ||
                        editMaxPeoples.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please fill all fields \n   Nothing to Add", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (phone.matches("\\d+")) {
                        storageReference = storageReference.child(city).child(firebaseUser.getUid());
                        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.d("TAG", "Fucking downloaded:" + uri);
                                        listner.sendBackToFragment(stayName, hostName, place, date, city, phone, maxPeoples, uri.toString(), details, offers);
                                    }
                                });
                            }
                        });
                        getDialog().dismiss();
                    }
                    else Toast.makeText(getContext(), "Incorrect Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listner = (StayDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement StayDialogListner");
        }
    }

    public interface StayDialogListener{
        void sendBackToFragment(String stayName, String hostName, String Place, String Date, String City, String phone, String maxPeoples, String imageURL, String details, String offers);
    }
}
