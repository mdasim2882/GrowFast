package com.example.growfast.NavigationItemsFolder.CoreFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.growfast.InterfacesUsed.BannerLoadListener;
import com.example.growfast.NavigationItemsFolder.BusinessManagement;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.DigitalCardsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.VideosCardsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.WebCardsActivity;
import com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.WhatsappStatusVideoActivity;
import com.example.growfast.NavigationItemsFolder.Settings;
import com.example.growfast.R;
import com.example.growfast.Services.Banners;
import com.example.growfast.Services.HomeSliderAdapter;
import com.example.growfast.Services.PicassoImageLoadingService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;


public class Home extends Fragment implements BannerLoadListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GridLayout mainGrid;

    private String mParam1;
    private String mParam2;

    //Slider Materials
    BannerLoadListener bannerLoadListener;
    Slider bannerSlider;
    CollectionReference bannerRef;
    private AlertDialog.Builder alertDialog;

    public Home() {
        bannerRef = FirebaseFirestore.getInstance().collection("Banners");
    }


    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainGrid = (GridLayout) view.findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);
        // Inflate the layout for this fragment
        setUpToolbar(view);
        setUpAlertDialog();

        try {
            bannerSlider = view.findViewById(R.id.layout_banner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Load banner using service
        Slider.init(new PicassoImageLoadingService());
        bannerLoadListener = this;


        loadBanner();
        return view;
    }

    private void loadBanner() {


        bannerRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Banners> banners = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot bannerSnapshot : task.getResult()) {
                        Banners banner = bannerSnapshot.toObject(Banners.class);
                        banners.add(banner);
                    }
                    try {
                        bannerLoadListener.onBannerLoadSuccess(banners);
                    } catch (Exception e) {
                        alertDialog.show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                bannerLoadListener.onBannerLoadFailure(e.getMessage());
            }
        });
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    executeMethodforCardIndex(finalI);
                }
            });
        }
    }

    private void executeMethodforCardIndex(int finalI) {
        switch (finalI) {
            case 0: {
                Intent i = new Intent(getActivity(), DigitalCardsActivity.class);
                startActivity(i);
                break;
            }
            case 1: {
                Intent i = new Intent(getActivity(), WebCardsActivity.class);
                startActivity(i);

                break;
            }
            case 2: {
                Intent i = new Intent(getActivity(), GreetingsActivity.class);
                startActivity(i);
                break;
            }
            case 3: {
                Intent i = new Intent(getActivity(), WhatsappStatusVideoActivity.class);
                startActivity(i);
                break;
            }
            case 4: {
                Intent i = new Intent(getActivity(), VideosCardsActivity.class);
                i.putExtra("from", "uiPro");
                i.putExtra("collectionName", "UiPro Creativity Videos");
                startActivity(i);
                break;
            }
            case 5: {
                Intent i = new Intent(getActivity(), VideosCardsActivity.class);
                i.putExtra("from", "detailedVideos");
                i.putExtra("collectionName", "In-Detailed Long Videos");
                startActivity(i);
                break;
            }
            case 6: {
                Intent i = new Intent(getActivity(), VideosCardsActivity.class);
                i.putExtra("from", "copyPaste");
                i.putExtra("collectionName", "Copy Paste Videos");
                startActivity(i);
                break;
            }
            case 7: {
                Intent i = new Intent(getActivity(), VideosCardsActivity.class);
                i.putExtra("from", "agentIntroVideos");
                i.putExtra("collectionName", "Agent Intro Videos");
                startActivity(i);
                break;
            }
            case 8: {
                Intent i = new Intent(getActivity(), VideosCardsActivity.class);
                i.putExtra("from", "greetingsVideos");
                i.putExtra("collectionName", "Agent Intro Videos");
                startActivity(i);
                break;

            }
            case 9: {
                Intent i = new Intent(getActivity(), VideosCardsActivity.class);
                i.putExtra("from", "ownVideos");
                i.putExtra("collectionName", "Own Videos");
                startActivity(i);
                break;
            }
            case 10: {
                Fragment fragment = new HelpDesk();
                loadFromFragment(fragment);
                break;
            }


        }
    }

    private boolean loadFromFragment(Fragment fragment) {
        if (fragment != null) {
            ((BusinessManagement) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.my_container, fragment)
                    .commit();
            BusinessManagement.STATUS_FRAGMENT = 1;
            return true;
        }
        return false;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.business_management_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }


    private void setUpAlertDialog() {
        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Error:");
        alertDialog.setMessage("No Internet Connection");
        alertDialog.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settingiconclick) {
            Intent i = new Intent(getActivity(), Settings.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.business_management_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public void onBannerLoadSuccess(List<Banners> banners) {
        bannerSlider.setAdapter(new HomeSliderAdapter(banners));
        bannerSlider.setInterval(3000);
    }

    @Override
    public void onBannerLoadFailure(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

}