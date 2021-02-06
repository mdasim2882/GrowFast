package com.digitaladvisor.growfast.NavigationItemsFolder.CoreFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.digitaladvisor.growfast.NavigationItemsFolder.BusinessManagement;
import com.digitaladvisor.growfast.R;

import static com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.Adapters.DigitalCardRecyclerViewAdapter.CART_BADGE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Cart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cart extends Fragment {

    TextView productname, prductprice, priceSubtotal, feeTransaction, priceTotalOrder;
    Button placeOrderButton;
    LinearLayout upper, lower;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public LocalBroadcastManager localBMFragments;
    private boolean change = false;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("bdcst", "setupBadge: Received");
//            Runnable runnable = () -> {
//                    setupBadge();
////                    Log.d("bdcst", "onCreateView: Something went wrong");
//            };
//            new Handler().postDelayed(runnable, 3500);

        }
    };
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent;
            setupBadge();
        }
    };

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

    private double setFeePercent(int pricetobeLoaded) {
        return (double) Math.round((0.18) * pricetobeLoaded * 100) / 100;

    }

    private void setupBadge() {
        Log.d("bdcst", "setupBadge: Received");

        lower.setVisibility(View.VISIBLE);
        upper.setVisibility(View.VISIBLE);

        productname.setText(" " + ((BusinessManagement) getActivity()).productName);
        int priceCost = ((BusinessManagement) getActivity()).price;
        prductprice.setText("Rs " + priceCost);
        priceSubtotal.setText("Rs " + priceCost);

        feeTransaction.setText("Rs " + setFeePercent(priceCost));

        double totalSum = setFeePercent(priceCost) + priceCost;
        priceTotalOrder.setText("Rs " + totalSum);

        localBMFragments.unregisterReceiver(receiver);
    }

    public Cart() {
        // Required empty public constructor
    }


    public static Cart newInstance(String param1, String param2) {
        Cart fragment = new Cart();
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

//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
//                new IntentFilter("carter"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("bcd", "onCreateView: Called");
        localBMFragments = LocalBroadcastManager.getInstance(getActivity());
        localBMFragments.registerReceiver(receiver, new IntentFilter(CART_BADGE));
        View itemView = inflater.inflate(R.layout.fragment_cart, container, false);
        initialTextFields(itemView);
        setUpToolbar(itemView);

        return itemView;
    }


    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.cart_management_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(true);
            toolbar.setNavigationOnClickListener(v -> {


                String val = ((BusinessManagement) getActivity()).getFrom;
                if (val == null) {
                    ((BusinessManagement) getActivity()).bottomNavigationView.setSelectedItemId(R.id.action_home);
                    Fragment fragment = new Home();
                    loadFromFragment(fragment);
                    BusinessManagement.STATUS_FRAGMENT = 0;
                } else {
                    ((BusinessManagement) getActivity()).finish();
                }

//                Fragment fragment = new Home();
//                loadFromFragment(fragment);
//                BusinessManagement.STATUS_FRAGMENT = 0;

            });
        }
    }

    private void initialTextFields(View itemView) {
        productname = itemView.findViewById(R.id.pdname);
        prductprice = itemView.findViewById(R.id.pd_price);
        priceSubtotal = itemView.findViewById(R.id.subtotal_price);
        feeTransaction = itemView.findViewById(R.id.transaction_fee);
        priceTotalOrder = itemView.findViewById(R.id.totalPriceOrder);

        upper = itemView.findViewById(R.id.order_details_box);
        lower = itemView.findViewById(R.id.cartTotal_details_box);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//      localBMFragments.unregisterReceiver(receiver);

    }
}