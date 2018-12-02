package com.example.application.travelbuddyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.travelbuddyapp.Login_fragment;
import com.example.application.travelbuddyapp.R;
import com.example.application.travelbuddyapp.ToAndFro;
import com.example.application.travelbuddyapp.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_fragment extends Fragment implements View.OnClickListener{

    EditText register_userName, register_email, register_password, register_reTypePassword;
    CheckBox register_showPassword;
    Button register_button, alreadyUser_button;
    ToAndFro taf;
    FirebaseAuth firebaseAuth;
    DatabaseReference rootReference;
    FirebaseUser firebaseUser;
    String email, password;
    Context activityContext = getActivity();
    ProgressDialog progressDialog;
    Fragment login_Fragment = new Login_fragment();
    // RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        register_userName = (EditText) getActivity().findViewById(R.id.register_username);
        register_email = (EditText) getActivity().findViewById(R.id.register_email);
        register_password = (EditText) getActivity().findViewById(R.id.register_password);
        register_reTypePassword = (EditText) getActivity().findViewById(R.id.register_reTypePassword);
        register_showPassword = (CheckBox) getActivity().findViewById(R.id.showPassword_register);
        register_button = (Button) getActivity().findViewById(R.id.register_button);
        alreadyUser_button = (Button) getActivity().findViewById(R.id.alreadyUser_button);
        firebaseAuth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(getContext());
        // radioGroup = (RadioGroup) getActivity().findViewById(R.id.RadioGroup);

        taf = (ToAndFro) getActivity();

        register_showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    register_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    register_reTypePassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else{
                    register_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    register_reTypePassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        register_reTypePassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("Signup", "DOne Button Clicked");
                    register_button.performClick();
                }
                return false;
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Connecting to Server...");
                progressDialog.show();

                if (register_userName.getText().toString().equals("") ||
                        register_email.getText().toString().equals("") ||
                        register_password.getText().toString().equals("") ||
                        register_reTypePassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {
                    if (!register_password.getText().toString().equals(register_reTypePassword.getText().toString())) {
                        Toast.makeText(getActivity(), "Password not matching", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else {
                        email = register_email.getText().toString();
                        password = register_password.getText().toString();
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            firebaseUser = firebaseAuth.getCurrentUser();
                                            String signType = "";
                                            //radioButton = (RadioButton)getActivity().findViewById(radioGroup.getCheckedRadioButtonId());
                                            signType = "Traveller";
                                            UserData userData = new UserData(signType, register_userName.getText().toString(), email);
                                            rootReference.child("Users").child(firebaseUser.getUid()).setValue(userData)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(getActivity().getApplicationContext().getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                taf.ChangeFragment(login_Fragment);
                                                            }
                                                            else
                                                                Toast.makeText(getActivity().getApplicationContext().getApplicationContext(), "Failed to store user data", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                        else {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Registration Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });

        alreadyUser_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        taf.ChangeFragment(login_Fragment);
    }
}