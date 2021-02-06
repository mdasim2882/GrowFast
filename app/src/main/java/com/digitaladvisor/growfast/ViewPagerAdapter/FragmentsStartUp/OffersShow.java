package com.digitaladvisor.growfast.ViewPagerAdapter.FragmentsStartUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.digitaladvisor.growfast.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OffersShow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OffersShow extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

 static OffersShow instance;

    public OffersShow() {
        // Required empty public constructor
    }

    public static OffersShow getInstance() {
        if (instance == null)
            instance = new OffersShow();
        return instance;
    }


    public static OffersShow newInstance(String param1, String param2) {
        OffersShow fragment = new OffersShow();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offers_show, container, false);
    }
}