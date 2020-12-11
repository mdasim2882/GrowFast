package com.example.growfast.NavigationItemsFolder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.growfast.R;


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
    public static class MyPrefenceFragment extends PreferenceFragment {

        private String LOGOUT_FILE;
        private String ISLOGIN = "islogin";

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_preferences);

        }
    }

}