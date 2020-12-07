package com.example.growfast.NavigationItemsFolder.CoreFragments;
/*
 * Created by Mohd Asim 7/12/2020
 *
 *  Step 1-Always start an Crop Image Activity by using start(getContext(),FragmentName.this);
 * else it will be meaningless, and onActivityResult is not triggered in Fragment.
 *
 * To make it effective always use the step 1;
 *
 * */

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.growfast.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileDetails extends Fragment {

    CircleImageView profilePic;
    Button saveButton;
    Uri picUri;
    String mypicUri, myname;


    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageTask uploadTask;
    StorageReference storageProfilePicsRef;


    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View itemView;

    public ProfileDetails() {

    }


    // TODO: Rename and change types and number of parameters
    public static ProfileDetails newInstance(String param1, String param2) {
        ProfileDetails fragment = new ProfileDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //Intitialize database work
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile Pic");
        getSavedUserInfo();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_profile_details, container, false);

        profilePic = itemView.findViewById(R.id.profile_image);
        saveButton = itemView.findViewById(R.id.savebtn);

        profilePic.setOnClickListener(v -> {
            //Step 1 implementation is used over here
            CropImage.activity().setAspectRatio(1, 1).start(getContext(), ProfileDetails.this);
        });
        saveButton.setOnClickListener(v -> savedToStorageDatabase());

        return itemView;
    }

    private void getSavedUserInfo() {

        try {
            Log.d("tagger", "onCreate: " + "\n" +
                    "FUID -->" + FirebaseAuth.getInstance().getUid() + "\n" +
                    "CurrentUser FUID-->" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                    if (snapshot.hasChild("image")) {
                        String image = snapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profilePic);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            picUri = result.getUri();
            profilePic.setImageURI(picUri);

        } else {
            Toast.makeText(getActivity(), "Error: Try again...", Toast.LENGTH_SHORT).show();
        }
    }

    private void savedToStorageDatabase() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Saving Profile");
        progressDialog.setMessage("Please wait... uploading your profile data");
        progressDialog.show();


        if (picUri != null) {
            final StorageReference fileRef = storageProfilePicsRef.child(mAuth.getCurrentUser().getUid() + ".jpg");
            uploadTask = fileRef.putFile(picUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        mypicUri = downloadUri.toString();


                        HashMap<String, Object> uniqueUserData = new HashMap<>();
                        uniqueUserData.put("image", mypicUri);

                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(uniqueUserData);
                        progressDialog.dismiss();


                    }
                }
            });
        } else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Profile Picture not selected", Toast.LENGTH_SHORT).show();
        }
    }

}