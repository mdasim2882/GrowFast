package com.digitaladvisor.growfast.ViewPagerAdapter.FragmentsStartUp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.digitaladvisor.growfast.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BeingDigital#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BeingDigital extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static BeingDigital instance;


    public BeingDigital() {
        // Required empty public constructor
    }

    public static BeingDigital getInstance() {

        if (instance == null)
            instance = new BeingDigital();
        return instance;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BeingDigital.
     */

    public static BeingDigital newInstance(String param1, String param2) {
        BeingDigital fragment = new BeingDigital();
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
        return inflater.inflate(R.layout.fragment_being_digital, container, false);
    }
}