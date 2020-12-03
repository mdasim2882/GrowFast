package com.example.growfast.NavigationItemsFolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.growfast.MainActivity;
import com.example.growfast.R;
import com.google.firebase.auth.FirebaseAuth;


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
        getFragmentManager().beginTransaction().replace(R.id.settingsFragmLayout, new MyPrefenceFragment()).commit();
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
    public class MyPrefenceFragment extends PreferenceFragment {

        private String LOGOUT_FILE;
        private String ISLOGIN = "islogin";

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_preferences);
            Preference myPref = (Preference) findPreference("logout_button_preference");
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    //TODO: Perform logout operations here
                    performLogout();
                    Toast.makeText(getActivity(), "Logout Successfully", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

        }

        private void performLogout() {
            Log.d("LOGOUT BUTTON CLICKED", "onPreferenceClick: Before OUT:->" + FirebaseAuth.getInstance().getUid());
            FirebaseAuth.getInstance().signOut();
            LOGOUT_FILE = "loginStats";
            SharedPreferences sharedPreferences = getSharedPreferences(LOGOUT_FILE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(ISLOGIN, false);
            editor.commit();
            Settings.this.startActivity(new Intent(Settings.this, MainActivity.class));
            finish();
            Log.d("LOGOUT BUTTON CLICKED", "onPreferenceClick: After OUT:->" + FirebaseAuth.getInstance().getUid());
        }
    }

}