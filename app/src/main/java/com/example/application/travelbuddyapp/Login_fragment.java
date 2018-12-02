package com.example.application.travelbuddyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;


public class Login_fragment extends Fragment implements View.OnClickListener{
    SharedPreferences sp;
    EditText login_email, login_password;
    CheckBox login_showPassword;
    Button login_button, newUser_button;
    RelativeLayout loginRelativeLayout;
    ToAndFro taf;
    FirebaseAuth firebaseAuth;

    String email, password;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        login_email = (EditText) getActivity().findViewById(R.id.email_login);
        login_password = (EditText) getActivity().findViewById(R.id.password_login);
        login_showPassword = (CheckBox) getActivity().findViewById(R.id.showPassword_login);
        login_button = (Button) getActivity().findViewById(R.id.login_button);
        newUser_button = (Button) getActivity().findViewById(R.id.newUser_button);
        taf = (ToAndFro) getActivity();
        firebaseAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(getContext());



        login_showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    login_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else{
                    login_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        login_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i("Login", "DOne Button Clicked");
                    login_button.performClick();
                }
                return false;
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Connecting to server...");
                progressDialog.show();
                if (login_email.getText().toString().equals("") ||
                        login_password.getText().toString().equals("")) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    email = login_email.getText().toString();
                    password = login_password.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                                        getActivity().finish();




                                        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

                                        sp.edit().putBoolean("logged",true).apply();
                                        sp.edit().putString("User",email).apply();
                                        sp.edit().putString("password",password).apply();

                                        Intent toHomeActivity = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(toHomeActivity);
                                    }
                                    else
                                    {
                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Email or password is incorrect", Toast.LENGTH_SHORT).show();
                                    }

                                    }
                            });
                }
            }
        });
        newUser_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fragment signUp_Fragment = new Signup_fragment();
        taf.ChangeFragment(signUp_Fragment);
    }
}
