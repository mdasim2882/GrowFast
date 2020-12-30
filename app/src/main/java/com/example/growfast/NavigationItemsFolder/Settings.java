package com.example.growfast.NavigationItemsFolder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.growfast.R;
import com.google.firebase.messaging.FirebaseMessaging;


public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setUpToolbar();
        settingfragmentforSetting();
        //TODO: Use SharedPreferences to getDefault file and change SETTINGS using keys defined in setting_preferences.xml

    }

    private void settingfragmentforSetting() {
        getFragmentManager().beginTransaction().replace(R.id.settingsFragmLayout, new MyPrefenceFragment(this)).commit();
    }


    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.settings_app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @SuppressLint("ValidFragment")
    public static class MyPrefenceFragment extends PreferenceFragment {
        public SwitchPreference testPref;
        private String LOGOUT_FILE;
        private String ISLOGIN = "islogin";
        Activity activity;

        public MyPrefenceFragment(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_preferences);
            SharedPreferences sharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            testPref = (SwitchPreference) findPreference("notifications_status"); //Preference Key

            sharedPrefs.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> {
                if (key.equals("notifications_status")) {
                    boolean test = sharedPreferences.getBoolean("notifications_status", false);
                    //Do whatever you want here. This is an example.
                    if (test) {
//                        testPref.setSummary("Enabled");
                        Log.d("SETTER", "onRegister: Enabled");
//                        testPref.setSummary("Enabled");
                        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                                .addOnCompleteListener(task -> {
                                    String msg = "Enabled";
                                    if (!task.isSuccessful()) {
                                        msg = "Disabled";
                                    }
                                    Log.d("SETTER SUB: ", msg);
                                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                                });
                    } else {
//                        testPref.setSummary("Disabled");
                        Log.d("SETTER", "onRegister: Disabled");
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("weather").addOnCompleteListener(task -> {
                            String msg = "Disabled";
                            if (!task.isSuccessful()) {
                                msg = "Enabled";
                            }
                            Log.d("SETTER SUB: ", msg);
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                        });
                    }
                }
            });

        }

        @Override
        public void onResume() {
            super.onResume();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            boolean test = preferences.getBoolean("notifications_status", false);

            if (test) {
//                testPref.setSummary("Enabled");
                Log.d("SETTER", "onResume: Enabled");
            } else {
//                testPref.setSummary("Disabled");
                Log.d("SETTER", "onResume: Disabled");

            }
        }
    }
}