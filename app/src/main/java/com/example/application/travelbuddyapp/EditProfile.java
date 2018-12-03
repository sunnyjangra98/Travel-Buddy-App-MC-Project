package com.example.application.travelbuddyapp;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class EditProfile extends Fragment {

    View root;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    EditText editName, editEmail, editAddress;
    Button saveButton;
   // FloatingActionButton chooseButton;
    String name, email, address, originalName, originalEmail, originalAddress;
    int REQUEST_CODE = 1;
    Uri imageUri;
    ImageView newImage;
    ProgressDialog progressDialog;
    private final String CHANNEL_ID = "sample notification";
    private final int NOTIFICATION_ID = 001;
    String message = "fromNotification";
    Boolean ImageChanged =false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        storageReference = FirebaseStorage.getInstance().getReference().child("User-Images");
        newImage = (ImageView) root.findViewById(R.id.newImage);
        editName = (EditText) root.findViewById(R.id.editName);
        editEmail = (EditText) root.findViewById(R.id.editEmail);
        editAddress = (EditText) root.findViewById(R.id.editAddress);
       // chooseButton = (FloatingActionButton) root.findViewById(R.id.chooseButton);
        saveButton = (Button) root.findViewById(R.id.saveButton);
        progressDialog = new ProgressDialog(getContext());
        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.default_person_image));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("TAG", "Is this tag");
                name = dataSnapshot.child("username").getValue().toString();
                originalName = name;
                email = dataSnapshot.child("email").getValue().toString();
                originalEmail = email;

                if (dataSnapshot.hasChild("address"))
                    address = dataSnapshot.child("address").getValue().toString();
                else{
                    databaseReference.child("address").setValue("");
                    address = dataSnapshot.child("address").getValue().toString();
                }
                originalAddress = address;
                editName.setText(name);
                editEmail.setText(email);
                editAddress.setText(address);

                storageReference.child(firebaseUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Download URI:", "Download URI:" + uri.toString());
                        Glide.with(getContext()).load(uri.toString()).into(newImage);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        newImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(v);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                StorageReference sRef = storageReference.child(firebaseUser.getUid());
                progressDialog.setMessage("Updating Record");

                if (originalName.equals(editName.getText().toString()) && originalEmail.equals(editEmail.getText().toString())
                        && originalAddress.equals(editAddress.getText().toString()) && !ImageChanged) {
                    Toast.makeText(getContext(), "Nothing to update", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialog.show();
                    if(!originalName.equals(editName.getText().toString()))
                        databaseReference.child("username").setValue(editName.getText().toString());
                    if(!originalEmail.equals(editEmail.getText().toString()))
                        databaseReference.child("email").setValue(editEmail.getText().toString());
                    if(! originalAddress.equals(editAddress.getText().toString()))
                        databaseReference.child("address").setValue(editAddress.getText().toString());
                    if(ImageChanged){
                        sRef.putFile(imageUri)
                                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()){
                                            String update = "Data updated";
                                            Toast.makeText(getContext(), update, Toast.LENGTH_SHORT).show();

                                        }
                                        else {
                                            Toast.makeText(getContext(), "Image upload failed...Try again", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                    }
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                    displayNotification(v, "User Profile", "Record was updated successfully");
                }
            }
        });

        return root;
    }

    public void openGallery(View v){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            newImage.setImageURI(imageUri);
            ImageChanged =true; //image was changed
        }
    }

    //For Notification of record update
    public void displayNotification(View v, String data, String description){
        createNotificationChannel(data, description);  //For API version above 26, we need to create a channel

        //Notification Action here
        Intent notificationIntent = new Intent(getContext(), HomeActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        notificationIntent.putExtra(message, "EditProfileFragment");

        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.travelbuddy_logo);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        builder.setContentTitle(data);
        builder.setContentText(description);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    public void createNotificationChannel(String data, String description){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, data, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
