package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.digitaladvisor.growfast.NavigationItemsFolder.CoreFragments.ProfileDetails;
import com.digitaladvisor.growfast.R;
import com.digitaladvisor.growfast.Services.MyDownloadingService;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards.EditFBCoverPagesActivity.MAGGY;

public class ExoVideosWpActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private static final String TAG = "DOWNLOADER";

    private SimpleExoPlayer exoPlayer;
    private PlayerView andExoPlayerView;
    ProgressDialog mProgressDialog;
    private String urlVideo;

    private CircleImageView videoComapny;
    private EditText videoDescription, videoContactno;
    LinearLayout videoDescriptionLayout;
    RadioGroup itemGroup;
    RadioButton colorBox;
    FirebaseFirestore database;
    Uri vidoepicUri;

    boolean statusImagePicker = false;
    private boolean valid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_videos_wp);
        setUpToolbar();
        valid = getIntent().getBooleanExtra("statusVideo", false);
//        AndExoPlayerView andExoPlayerView = findViewById(R.id.andExoPlayerView);

        andExoPlayerView = findViewById(R.id.andExoPlayerView);
        intializeViews();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);


        if (valid) {

            urlVideo = getIntent().getStringExtra("videoUrl");
            Uri uri = Uri.parse(urlVideo);
//            andExoPlayerView.setSource(urlVideo);
//            Log.i("TAG", "onCreate: URL "+urlVideo+"\n URI: "+uri.toString());

            DefaultHttpDataSourceFactory defdataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(uri, defdataSourceFactory, extractorsFactory, null, null);
            andExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);


        }
    }

    private void startPlayer() {
        Log.d(TAG, "startPlayer: called with value: " + urlVideo);
        valid = getIntent().getBooleanExtra("statusVideo", false);
//        AndExoPlayerView andExoPlayerView = findViewById(R.id.andExoPlayerView);

        andExoPlayerView = findViewById(R.id.andExoPlayerView);
        intializeViews();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);


        if (valid) {

            urlVideo = getIntent().getStringExtra("videoUrl");
            Uri uri = Uri.parse(urlVideo);
//            andExoPlayerView.setSource(urlVideo);
//            Log.i("TAG", "onCreate: URL "+urlVideo+"\n URI: "+uri.toString());

            DefaultHttpDataSourceFactory defdataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(uri, defdataSourceFactory, extractorsFactory, null, null);
            andExoPlayerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);


        }

    }

    private void intializeViews() {
        // instantiate it within the onCreate method

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Downloading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);


        videoDescriptionLayout = findViewById(R.id.ll_videodesc);
        videoDescription = findViewById(R.id.videoDescription);
        videoContactno = findViewById(R.id.videoContactno);
        itemGroup = findViewById(R.id.radioGroupColorBox);
        videoComapny = findViewById(R.id.videoComantyIcon);
        int selectedId = itemGroup.getCheckedRadioButtonId();
        Log.d(TAG, "intializeViews: SELECTED_ID: " + selectedId);
//         colorBox = findViewById(selectedId);
        itemGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Log.d(TAG, "intializeViews: CHECKED_ID: " + checkedId);
            Log.d(TAG, "intializeViews: RADIO GROUP_CHECKED_ID: " + group.getCheckedRadioButtonId());

            colorBox = findViewById(checkedId);
            try {
                if (colorBox != null) {
                    String color = colorBox.getText().toString();
//                     Toast.makeText(this, color+" Color", Toast.LENGTH_SHORT).show();
                    switch (color) {
                        case "Purple":
                            videoDescriptionLayout.setBackgroundColor(Color.parseColor("#D427F1"));
                            videoDescriptionLayout.setBackgroundResource(R.color.purple);
                            break;

                        case "Green":
//                    videoDescriptionLayout.setBackgroundColor(Color.GREEN);
                            videoDescriptionLayout.setBackgroundResource(R.color.green);

                            break;
                        case "Blue":
//                    videoDescriptionLayout.setBackgroundColor(Color.BLUE);
                            videoDescriptionLayout.setBackgroundResource(R.color.blue);

                            break;
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "intializeViews: " + e.getMessage());
            }
        });
        database = FirebaseFirestore.getInstance();
        //loadIconsAndDescription();
        setListeners();
    }

    private void setListeners() {
        videoComapny.setOnClickListener(v -> {
            CropImage.activity().setAspectRatio(1, 1).start(ExoVideosWpActivity.this);
            statusImagePicker = true;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        data.putExtra("videoUrl", urlVideo);
        data.putExtra("statusVideo", valid);
        Log.d(TAG, "onActivityResult: VIDEO_URL==> " + urlVideo);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                vidoepicUri = result.getUri();
                videoComapny.setImageURI(vidoepicUri);
                Log.d(TAG, "onActivityResult: videoPicUri==> " + vidoepicUri.toString());
            }


        } else {
            Toast.makeText(this, "Error: Try again...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void loadIconsAndDescription() {
        database.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String companyPhotoUrl = task.getResult().get(ProfileDetails.M_DP_URI).toString();
                String companyDescription = task.getResult().get(ProfileDetails.ABOUT_US).toString();
                String companyContactno = task.getResult().get(ProfileDetails.MOBILE).toString();

                if (companyPhotoUrl != null) {
                    Picasso.get().load(companyPhotoUrl).into(videoComapny);
                }
                if (companyContactno != null || !companyContactno.equals("")) {
                    videoContactno.setText("Contact no. " + companyContactno);
                }
                if (companyDescription != null || !companyDescription.equals("")) {
                    videoDescription.setText(companyDescription);
                }


            } else {
                Toast.makeText(this, "Logo not updated yet", Toast.LENGTH_LONG);
            }
        });
    }

    private void stopPlayer(PlayerView pv, SimpleExoPlayer absPlayer) {
        pv.setPlayer(null);
        absPlayer.release();
        absPlayer = null;
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.exovideos_greets_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer(andExoPlayerView, exoPlayer);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onBackPressed();
        Log.d(TAG, "onKeyDown: Called");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mProgressDialog.cancel();
            mProgressDialog.dismiss();
            return true;

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!statusImagePicker) {
            recreate();
        } else {
            startPlayer();
            statusImagePicker = false;
        }
    }

    public void downloadVideoButton(View view) {
        if (isStoragePermissionGranted()) {
            mProgressDialog.show();
            Intent intent = new Intent(this, MyDownloadingService.class);
            Log.d(TAG, "downloadVideoButton: press url: " + urlVideo);
            intent.putExtra("url", urlVideo);
            intent.putExtra("receiver", new DownloadReceiver(new Handler(), this));
            startService(intent);
        }
//        download(urlVideo);
    }

    private void download(String url) {
//        String url = "url you want to download";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("Videos");
        request.setTitle("DigiVideos" + System.currentTimeMillis());
// in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "video_new.mp4");

// get download service and enqueue file
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public class DownloadReceiver extends ResultReceiver {
        Activity activity;
        private String d_receiver;
        private String TAG;
        Handler mhandler;

        public DownloadReceiver(Handler handler, Activity exoVideosWpActivity) {
            super(handler);
            mhandler = handler;
            this.activity = exoVideosWpActivity;
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            super.onReceiveResult(resultCode, resultData);
            d_receiver = "DOWNLOADER_RECEIVER";
            TAG = d_receiver;
//            Log.d(TAG, "onReceiveResult: CALLED with result code value "+resultCode);
            if (resultCode == 8344) {
                int progress = resultData.getInt("progress"); //get the progress
                // Log.d(TAG, "onReceiveResult: going in sanity check progressCount: "+progress);


                mProgressDialog.setProgress(progress);


                if (progress == 100) {
                    mProgressDialog.dismiss();
                    Toast.makeText(activity, "Download: Success", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(MAGGY, "Permission is granted");
                return true;
            } else {

                Log.v(MAGGY, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(MAGGY, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(MAGGY, "Permission: " + permissions[0] + " was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

}