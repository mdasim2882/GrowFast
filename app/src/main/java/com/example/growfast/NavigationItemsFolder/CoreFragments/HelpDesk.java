package com.example.growfast.NavigationItemsFolder.CoreFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.growfast.HelperMethods.HelpEntry;
import com.example.growfast.InterfacesUsed.LoadMyHelpDeskData;
import com.example.growfast.NavigationItemsFolder.BusinessManagement;
import com.example.growfast.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpDesk#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpDesk extends Fragment implements LoadMyHelpDeskData {

    TextView privacyPolicyText, helpContacts, supportEmails, mDescriptions, referAndEarnLink;
    VideoView startVideo;
    MediaController mediaController;

    LoadMyHelpDeskData loadMyHelpDeskData;
    ProgressBar progressBar;
    AlertDialog.Builder alertDialog;


    CollectionReference loadMyHelpDeskRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HelpDesk() {
        // Required empty public constructor
    }
    public static HelpDesk newInstance(String param1, String param2) {
        HelpDesk fragment = new HelpDesk();
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
        setHasOptionsMenu(true);

        ((BusinessManagement) getActivity()).bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help_desk, container, false);
        initializeLayoutElements(view);


        setUpToolbar(view);
        startHelpDeskLoading();


        return view;
    }

    private void startHelpDeskLoading() {
        loadMyHelpDeskRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<HelpEntry> products = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                        HelpEntry product = bannerSnapshot.toObject(HelpEntry.class);
                        products.add(product);
                    }
                    try {
                        loadMyHelpDeskData.onHelpDeskLoadSuccess(products);
                    } catch (Exception e) {
                        //TODO: Integrate a no internet connection dialog here
                        alertDialog = new AlertDialog.Builder(getActivity());
                        alertDialog.setTitle("Error:");
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                        alertDialog.show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadMyHelpDeskData.onHelpDeskFailure(e.getMessage());
            }
        });
    }

    private void initializeLayoutElements(View view) {
        loadMyHelpDeskRef = FirebaseFirestore.getInstance().collection("Help Desk");
        loadMyHelpDeskData = this;


        privacyPolicyText = view.findViewById(R.id.privacyPolicyText);
        helpContacts = view.findViewById(R.id.helpcontacts);
        supportEmails = view.findViewById(R.id.supportEmail);
        mDescriptions = view.findViewById(R.id.descriptiondesk);
        referAndEarnLink = view.findViewById(R.id.referandearn_linkText);
        progressBar = view.findViewById(R.id.videoprogressbar);

        privacyPolicyText.setMovementMethod(LinkMovementMethod.getInstance());


        startVideo = view.findViewById(R.id.videoHelpDesk);
        mediaController = new MediaController(getContext());


    }


    private boolean loadFromFragment(Fragment fragment) {
        if (fragment != null) {
            ((BusinessManagement) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.my_container, fragment)
                    .commit();
            BusinessManagement.STATUS_FRAGMENT = 1;
            ((BusinessManagement) getActivity()).bottomNavigationView.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.helpdesk_greets_details_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
            toolbar.setNavigationOnClickListener(v -> {
                ((BusinessManagement) getActivity()).bottomNavigationView.setSelectedItemId(R.id.action_home);
                Fragment fragment = new Home();
                loadFromFragment(fragment);
                BusinessManagement.STATUS_FRAGMENT = 0;

            });
        }
    }


    @Override
    public void onHelpDeskLoadSuccess(List<HelpEntry> text) {
        String uriHelp = text.get(0).getVideoHelpLink();
        String phoneNo1 = text.get(0).getPhoneNo1();
        String phoneNo2 = text.get(0).getPhoneNo2();
        String emailHelp = text.get(0).getSupportEmail();
        String description = text.get(0).getDescriptionHelp();

        String paymentLink = text.get(0).getPaymentLink();
        String referralLink = text.get(0).getReferAndEarnLink();
        String refundPolicy = text.get(0).getRefundPolicyLink();
        String privacyPolicy = text.get(0).getPrivacyPolicyLink();

        // Load Video Here
        Uri uri = Uri.parse(uriHelp);
        startVideo.setVideoURI(uri);
        startVideo.setMediaController(mediaController);
        mediaController.setAnchorView(startVideo);

        if (uri != null) {
            startVideo.setOnPreparedListener(mp -> {
                mp.setLooping(false);
                progressBar.setVisibility(View.GONE);
                startVideo.start();
            });
        }


        //Load and change numbers here
        if (phoneNo1.length() > 0 && phoneNo2.length() > 0) {
            helpContacts.setText(phoneNo1 + " / " + phoneNo2);
        }


        //Load Support Email Id Here
        if (emailHelp.length() > 0)
            supportEmails.setText(emailHelp);

        // Load description for Offers
        if (description.length() > 0) {
            mDescriptions.setText(description);
        }


        //TODO: Load and perform Payment Actions here


        //TODO:Load and set Refer and Earn link setup here
        if (referralLink.length() > 0) {
            referAndEarnLink.setOnClickListener(v -> gotoLink(referralLink));
        }


        //TODO: Start a webview Intent to Privacy Policy and Refund Policy here


    }

    private void gotoLink(String url) {
        //But it is still not working in pdf

        if (url != null) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }

    @Override
    public void onHelpDeskFailure(String message) {
        Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.help_desk_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.refreshiconclick) {
            // Call this function in toolbar menu
            progressBar.setVisibility(View.VISIBLE);
            startHelpDeskLoading();
        }
        return super.onOptionsItemSelected(item);
    }
}