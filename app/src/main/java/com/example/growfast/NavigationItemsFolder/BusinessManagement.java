package com.example.growfast.NavigationItemsFolder;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.growfast.NavigationItemsFolder.CoreFragments.Cart;
import com.example.growfast.NavigationItemsFolder.CoreFragments.HelpDesk;
import com.example.growfast.NavigationItemsFolder.CoreFragments.Home;
import com.example.growfast.NavigationItemsFolder.CoreFragments.ProfileDetails;
import com.example.growfast.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BusinessManagement extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        setBottomNavigationMenu();
    }

    private void setBottomNavigationMenu() {
        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            Fragment fragment = null;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.action_home:
                        fragment = new Home();
                        break;

                    case R.id.action_offers:
                        fragment = new ProfileDetails();
                        break;
                    case R.id.action_history:
                        fragment = new Cart();
                        break;
                    case R.id.action_favourites:
                        fragment = new HelpDesk();
                        break;

                }
                return loadFromFragment(fragment);

            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home);
    }

    private boolean loadFromFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.my_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}