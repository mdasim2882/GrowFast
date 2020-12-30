package com.example.growfast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.growfast.Authentication.PhoneAuthActivity;
import com.example.growfast.NavigationItemsFolder.BusinessManagement;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "NotifyOreo";
    public final String ISLOGIN = "islogin";
    public final String LOGIN_STATS = "loginStats";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(task -> {
                    String msg = "Enabled";
                    if (!task.isSuccessful()) {
                        msg = "Disabled";
                    }
                    Log.d("SETTER SUB: ", msg);
                    //Toast.makeText(th, msg, Toast.LENGTH_SHORT).show();
                });

        createNotificationChannel();

        try {
            //Shared preferences is used to save login session after authentication on the same device
            sharedPreferences = getSharedPreferences(LOGIN_STATS, Context.MODE_PRIVATE);
            Boolean statusLogin = sharedPreferences.getBoolean(ISLOGIN, false);
            if (statusLogin) {
                //
                startWelcomePageHere();
            }
        } catch (Exception e) {

        }


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = "NotifyOreo";
//            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
//            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void startWelcomePageHere() {
        Intent i = new Intent(this, BusinessManagement.class);
        startActivity(i);
        finish();
    }

    public void starthere(View view) {
        Intent intent = new Intent(this, PhoneAuthActivity.class);
        startActivity(intent);
        finish();
    }
}