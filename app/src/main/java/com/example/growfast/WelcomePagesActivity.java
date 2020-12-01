package com.example.growfast;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.growfast.ViewPagerAdapter.ZoomOutPageTransformer;
import com.example.growfast.ViewPagerAdapter.mViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


public class WelcomePagesActivity extends AppCompatActivity {

    ViewPager viewPager;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_pages);

        // Add a view pager for better UI/UX
        setMyViewPager();
        String uid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, "UID: " + uid);
    }

    private void setMyViewPager() {
        viewPager=findViewById(R.id.startview_pager);
        viewPager.setAdapter(new mViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position)
            {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

    }

    public void setCurrentItem(int item, boolean smoothscroller) {
        viewPager.setCurrentItem(item, smoothscroller);
    }


}