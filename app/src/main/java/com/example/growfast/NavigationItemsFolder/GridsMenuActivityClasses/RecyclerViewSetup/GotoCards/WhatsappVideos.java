package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.growfast.R;

public class WhatsappVideos extends AppCompatActivity {

    VideoView startVideo;
    MediaController mediaController;
    //    ProgressDialog buffDialog;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatsapp_videos);

        startVideo = findViewById(R.id.videoWp);
        progressBar = findViewById(R.id.statusprogressbar);
        setUpToolbar();
        mediaController = new MediaController(this);
//        buffDialog = new ProgressDialog(this);
//        buffDialog.setMessage("Buffering video...");
//        buffDialog.setCanceledOnTouchOutside(false);
//        buffDialog.show();


        boolean valid = getIntent().getBooleanExtra("statusVideo", false);


        if (valid) {
            String urlVideo = getIntent().getStringExtra("videoUrl");
            Uri uri = Uri.parse(urlVideo);
            startVideo.setVideoURI(uri);
            startVideo.setMediaController(mediaController);

            startVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);
                    mediaController.setAnchorView(startVideo);
                    mp.setLooping(true);
                    startVideo.start();

                }
            });
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.wpvideos_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}