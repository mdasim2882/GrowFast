package com.digitaladvisor.growfast.ViewPagerAdapter.FragmentsStartUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.digitaladvisor.growfast.NavigationItemsFolder.BusinessManagement;
import com.digitaladvisor.growfast.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YourChoice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YourChoice extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static YourChoice instance;
    Button letsGoButton;

    public YourChoice() {
        // Required empty public constructor
    }

    public static YourChoice getInstance() {

        if (instance == null)
            instance = new YourChoice();
        return instance;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment YourChoice.
     */
    // TODO: Rename and change types and number of parameters
    public static YourChoice newInstance(String param1, String param2) {
        YourChoice fragment = new YourChoice();
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
        View view= inflater.inflate(R.layout.fragment_your_choice, container, false);
        letsGoButton=view.findViewById(R.id.letsGo);
        letsGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goinside();
            }
        });
        return view;
    }


    public void goinside() {
        Intent i=new Intent(getActivity(), BusinessManagement.class);
        startActivity(i);
        getActivity().finish();
    }
}