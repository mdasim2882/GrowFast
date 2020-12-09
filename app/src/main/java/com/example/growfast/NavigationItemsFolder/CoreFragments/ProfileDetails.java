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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.growfast.NavigationItemsFolder.BusinessManagement;
import com.example.growfast.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileDetails extends Fragment {

    public static final String NAME = "name";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String FACEBOOK_ID = "FacebookID";
    public static final String LINKED_IN_ID = "LinkedInID";
    public static final String WHATSAPP_NO = "WhatsappNo";
    public static final String WEBSITE_URL = "WebsiteUrl";
    public static final String TWITTER_ID = "TwitterID";
    public static final String TELEGRAM_ID = "TelegramID";
    public static final String WORK_ADDRESS = "WorkAddress";
    public static final String DESIGNATION = "Designation";
    public static final String ABOUT_US = "About Us";
    public static final String INSTAGRAM_ID = "InstagramID";
    public static final String IMAGE = "image";
    public static final String PROFILE_PREF = "ProfilePref";
    public static final String M_DP_URI = "mDPUri";


    CircleImageView profilePic;
    TextView personUsername;
    EditText mName, mMobile, mEmailId, sFacebookID, sLinkedIn, sWhatsappNo, sWebsiteUrl, sTwitterID, sTelegeramID, sIntagramID;
    EditText bWorkAdd, bDesignation, bAboutUs;
    String dataName, dataMobile, dataEmail;
    String dataFacebookId, dataLinkedIn, dataWhatsappNo, dataWebsiteUrl, dataTwitterId, dataTelegramId, dataInstagramId;
    String dataWorkAdd, dataDesignation, dataAboutUs;
    Button saveButton;


    Uri picUri;
    String mypicUri, myname;
    boolean parameterForSavingInfo = false;

    //Preference files
    SharedPreferences profilePreference;
    SharedPreferences.Editor editor;


    FirebaseAuth mAuth;
    FirebaseFirestore database;

    StorageTask uploadTask;
    StorageReference storageProfilePicsRef;

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View itemView;

    ProgressDialog progressDialog;
    ProgressDialog pd;
    AlertDialog.Builder alertDialog;

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
        database = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile Pic");


        ((BusinessManagement) getActivity()).bottomNavigationView.setVisibility(View.GONE);

        profilePreference = this.getActivity().getSharedPreferences(PROFILE_PREF, Context.MODE_PRIVATE);
        editor = profilePreference.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        itemView = inflater.inflate(R.layout.fragment_profile_details, container, false);

        profilePic = itemView.findViewById(R.id.profile_image);
        saveButton = itemView.findViewById(R.id.savebtn);
        initializeEditTextFields();


        setDialogs();
        getSavedUserInfo();
        profilePic.setOnClickListener(v -> {
            //Step 1 implementation is used over here
            CropImage.activity().setAspectRatio(1, 1).start(getContext(), ProfileDetails.this);
        });
        saveButton.setOnClickListener(v -> savedToStorageDatabase());
        // Inflate the layout for this fragment
        setUpToolbar(itemView);
        return itemView;
    }

    private void setDialogs() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Saving Profile");
        progressDialog.setMessage("Please wait... uploading your profile data");

        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Done");
        alertDialog.setMessage("Profile data saved.");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.profile_details_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
            toolbar.setNavigationOnClickListener(v -> {
                ((BusinessManagement) getActivity()).bottomNavigationView.setSelectedItemId(R.id.action_home);
                ((BusinessManagement) getActivity()).bottomNavigationView.setVisibility(View.VISIBLE);
            });
        }
    }

    private void initializeEditTextFields() {

        personUsername = itemView.findViewById(R.id.pUsername);

        //Personal Info
        mName = itemView.findViewById(R.id.m_name);
        mMobile = itemView.findViewById(R.id.m_moblie);
        mEmailId = itemView.findViewById(R.id.m_emailID);

        //Social Info
        sFacebookID = itemView.findViewById(R.id.s_facebookId);
        sLinkedIn = itemView.findViewById(R.id.s_linkedId);
        sWhatsappNo = itemView.findViewById(R.id.s_whatsappNo);
        sWebsiteUrl = itemView.findViewById(R.id.s_websiteUrl);
        sTwitterID = itemView.findViewById(R.id.s_twitterID);
        sTelegeramID = itemView.findViewById(R.id.s_telegramID);
        sIntagramID = itemView.findViewById(R.id.s_instagramID);

        //Businness Info
        bWorkAdd = itemView.findViewById(R.id.b_workAdd);
        bDesignation = itemView.findViewById(R.id.b_designation);
        bAboutUs = itemView.findViewById(R.id.b_aboutUs);

    }

    private void getSavedUserInfo() {

        pd = new ProgressDialog(getActivity());
        pd.setTitle("Getting Info");
        pd.setMessage("Acquiring user credentials info...");
        loadFromCloudFirebase(pd);

    }

    private void loadFromCloudFirebase(ProgressDialog progressDialog) {
        String savdPicUri = profilePreference.getString(M_DP_URI, null);
        String textname = profilePreference.getString(M_DP_URI, null);
        /*if (savdPicUri != null) {
            Uri value = Uri.parse(savdPicUri);
            profilePic.setImageURI(value);
            personUsername.setText(textname);
        }*/
        database.collection("User").document(mAuth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {

                if (snapshot.exists() && snapshot != null) {
                    progressDialog.show();
                    if (snapshot.contains(IMAGE)) {
                        String image = snapshot.get(IMAGE).toString();
                        Picasso.get().load(image).into(profilePic);
                        progressDialog.dismiss();
                    }

                    //  Personal Info
                    if (snapshot.contains(NAME)) {
                        parameterForSavingInfo = true;
                        String savedName = snapshot.get(NAME).toString();
                        personUsername.setText(savedName);
                        mName.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(MOBILE)) {
                        String savedName = snapshot.get(MOBILE).toString();
                        mMobile.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(EMAIL)) {
                        String savedName = snapshot.get(EMAIL).toString();
                        mEmailId.setText(savedName);
                        progressDialog.dismiss();
                    }

                    //  Social Info
                    if (snapshot.contains(FACEBOOK_ID)) {
                        String savedName = snapshot.get(FACEBOOK_ID).toString();
                        sFacebookID.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(LINKED_IN_ID)) {
                        String savedName = snapshot.get(LINKED_IN_ID).toString();
                        sLinkedIn.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(WHATSAPP_NO)) {
                        String savedName = snapshot.get(WHATSAPP_NO).toString();
                        sWhatsappNo.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(WEBSITE_URL)) {
                        String savedName = snapshot.get(WEBSITE_URL).toString();
                        sWebsiteUrl.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(TWITTER_ID)) {
                        String savedName = snapshot.get(TWITTER_ID).toString();
                        sTwitterID.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(TELEGRAM_ID)) {
                        String savedName = snapshot.get(TELEGRAM_ID).toString();
                        sTelegeramID.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(INSTAGRAM_ID)) {
                        String savedName = snapshot.get(INSTAGRAM_ID).toString();
                        sIntagramID.setText(savedName);
                        progressDialog.dismiss();
                    }

                    //  Business Info
                    if (snapshot.contains(WORK_ADDRESS)) {
                        String savedName = snapshot.get(WORK_ADDRESS).toString();
                        bWorkAdd.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(DESIGNATION)) {
                        String savedName = snapshot.get(DESIGNATION).toString();
                        bDesignation.setText(savedName);
                        progressDialog.dismiss();
                    }
                    if (snapshot.contains(ABOUT_US)) {
                        String savedName = snapshot.get(ABOUT_US).toString();
                        bAboutUs.setText(savedName);
                        progressDialog.dismiss();
                    }

                }
            }
        });

    }

    private void savedToStorageDatabase() {

        progressDialog.show();

        //Extracting user inputs
        retrieveInputs();

        if (dataName.length() > 0 || parameterForSavingInfo) {
            final StorageReference fileRef = storageProfilePicsRef.child(mAuth.getCurrentUser().getUid() + ".jpg");
            HashMap<String, Object> userNAME = new HashMap<>();

            //  Personal Info
            if (dataName.length() > 0) {
                userNAME.put(NAME, dataName);
                personUsername.setText(dataName);
            }
            if (dataMobile.length() > 0) {
                userNAME.put(MOBILE, dataMobile);
                mMobile.setText(dataMobile);

            }
            if (dataEmail.length() > 0) {
                userNAME.put(EMAIL, dataEmail);
                mEmailId.setText(dataEmail);
            }

            //  Social Info
            if (dataFacebookId.length() > 0) {
                userNAME.put(FACEBOOK_ID, dataFacebookId);
                sFacebookID.setText(dataFacebookId);

            }
            if (dataLinkedIn.length() > 0) {
                userNAME.put(LINKED_IN_ID, dataLinkedIn);
                sLinkedIn.setText(dataLinkedIn);


            }
            if (dataWhatsappNo.length() > 0) {
                userNAME.put(WHATSAPP_NO, dataWhatsappNo);
                sWhatsappNo.setText(dataWhatsappNo);


            }
            if (dataWebsiteUrl.length() > 0) {
                userNAME.put(WEBSITE_URL, dataWebsiteUrl);
                sWebsiteUrl.setText(dataWebsiteUrl);


            }
            if (dataTwitterId.length() > 0) {
                userNAME.put(TWITTER_ID, dataTwitterId);
                sTwitterID.setText(dataTwitterId);
            }
            if (dataTelegramId.length() > 0) {
                userNAME.put(TELEGRAM_ID, dataTelegramId);
                sTelegeramID.setText(dataTelegramId);
            }
            if (dataInstagramId.length() > 0) {
                userNAME.put(INSTAGRAM_ID, dataInstagramId);
                sIntagramID.setText(dataInstagramId);
            }

            // Business Info
            if (dataWorkAdd.length() > 0) {
                userNAME.put(WORK_ADDRESS, dataWorkAdd);
                bWorkAdd.setText(dataWorkAdd);
            }
            if (dataDesignation.length() > 0) {
                userNAME.put(DESIGNATION, dataDesignation);
                bDesignation.setText(dataDesignation);


            }
            if (dataAboutUs.length() > 0) {
                userNAME.put(ABOUT_US, dataAboutUs);
                bAboutUs.setText(dataAboutUs);

            }


            if (userNAME.size() > 0) {
                database.collection("User").document(mAuth.getCurrentUser().getUid()).set(userNAME, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        alertDialog.show();
                    }
                });

            }


            if (picUri != null) {
                progressDialog.show();
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
                            uniqueUserData.put(IMAGE, mypicUri);

                            database.collection("User").document(mAuth.getCurrentUser().getUid()).set(uniqueUserData, SetOptions.merge());
                            editor.commit();
                            progressDialog.dismiss();

                        }
                    }
                });
            }
            if (dataName.length() == 0) {
                progressDialog.dismiss();
            }


        } else {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Enter your name first...", Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveInputs() {
        dataName = mName.getText().toString();
        dataMobile = mMobile.getText().toString();
        dataEmail = mEmailId.getText().toString();

        dataFacebookId = sFacebookID.getText().toString();
        dataLinkedIn = sLinkedIn.getText().toString();
        dataWhatsappNo = sWhatsappNo.getText().toString();
        dataWebsiteUrl = sWebsiteUrl.getText().toString();
        dataTwitterId = sTwitterID.getText().toString();
        dataTelegramId = sTelegeramID.getText().toString();
        dataInstagramId = sIntagramID.getText().toString();


        dataWorkAdd = bWorkAdd.getText().toString();
        dataDesignation = bDesignation.getText().toString();
        dataAboutUs = bAboutUs.getText().toString();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            picUri = result.getUri();
            profilePic.setImageURI(picUri);
            editor.putString(M_DP_URI, picUri.toString());


        } else {
            Toast.makeText(getActivity(), "Error: Try again...", Toast.LENGTH_SHORT).show();
        }
    }


}