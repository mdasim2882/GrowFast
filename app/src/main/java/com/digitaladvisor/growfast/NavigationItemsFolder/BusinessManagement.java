package com.digitaladvisor.growfast.NavigationItemsFolder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.digitaladvisor.growfast.NavigationItemsFolder.CoreFragments.HelpDesk;
import com.digitaladvisor.growfast.NavigationItemsFolder.CoreFragments.Home;
import com.digitaladvisor.growfast.NavigationItemsFolder.CoreFragments.ProfileDetails;
import com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.CartItemsActivity;
import com.digitaladvisor.growfast.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BusinessManagement extends AppCompatActivity {
    public static final String COME_FROM = "comeFrom";
    public static final String PRODUCT_NAME = "productName";
    public static final String PRODUCT_PRICE = "productPrice";
    public BottomNavigationView bottomNavigationView;
    public static int STATUS_FRAGMENT = 0;
    public int price;
    public String productName;
    public String getFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        setBottomNavigationMenu();

    }

    private void setBottomNavigationMenu() {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_home:
                        fragment = new Home();
                        break;

                    case R.id.action_profile:
                        fragment = new ProfileDetails();
                        break;
                    case R.id.action_cart:
                        Intent i = new Intent(BusinessManagement.this, CartItemsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.action_favourites_menu:
                        fragment = new HelpDesk();
                        break;

                }
                return loadFromFragment(fragment);

            }
        });

        getFrom = getIntent().getStringExtra(COME_FROM);
        productName = getIntent().getStringExtra(PRODUCT_NAME);
        price = getIntent().getIntExtra(PRODUCT_PRICE, 0);
        if (getFrom == null) {
            bottomNavigationView.setSelectedItemId(R.id.action_home);
        } else if (getFrom != null && getFrom.equals("digiPurchase")) {
            bottomNavigationView.setSelectedItemId(R.id.action_cart);
        }

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                //TA -- DA
            }
        });

    }

    private boolean loadFromFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.my_container, fragment)
                    .commit();
            if (getFrom == null) {
                STATUS_FRAGMENT = 1;
            } else {
                STATUS_FRAGMENT = 0;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        Log.d("press", "onBackPressed: STATUS_PRESSED- > " + STATUS_FRAGMENT);
        Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();

        if (STATUS_FRAGMENT > 0) {
            Fragment fragment = new Home();
            loadFromFragment(fragment);
            bottomNavigationView.setSelectedItemId(R.id.action_home);
            STATUS_FRAGMENT = 0;
            bottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }


}