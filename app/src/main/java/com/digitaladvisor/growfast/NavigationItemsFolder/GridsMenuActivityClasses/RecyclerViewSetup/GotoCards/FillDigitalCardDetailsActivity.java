package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.RecyclerViewSetup.GotoCards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.digitaladvisor.growfast.R;

public class FillDigitalCardDetailsActivity extends AppCompatActivity {
    public static final String CARD_WIDTH = "cardWidth";
    public static final String CARD_HEIGHT = "cardHeight";

    public static final String PHONE_NO = "PHONE_NO";
    public static final String YOUTUBE_CHANNEL = "YOUTUBE_CHANNEL";
    public static final String EMAIL_ID = "EMAIL_ID";
    public static final String WEBSITE_URL = "WEBSITE_URL";
    public static final String WHATSAPP_NO = "WHATSAPP_NO";
    public static final String FACEBOOK_ID = "FACEBOOK_ID";
    public static final String LINKEDIN_ID = "LINKEDIN_ID";


    EditText mMobile, mEmailId, sFacebookID, sLinkedIn, sWhatsappNo, sWebsiteUrl, sYoutube;
    String dataFacebookId, dataLinkedIn, dataWhatsappNo, dataWebsiteUrl;
    private String dataMobile;
    private String dataEmailId;
    private String dataYoutube;
    private float cardActualWidth;
    private float cardActualHeight;

    private String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_digital_card_details);
        setUpToolbar();
        intializeDetailsEDT();
    }

    private void intializeDetailsEDT() {
        mMobile = findViewById(R.id.pno);
        mEmailId = findViewById(R.id.eid);
        sWebsiteUrl = findViewById(R.id.weburl);
        sWhatsappNo = findViewById(R.id.wpappno);
        sFacebookID = findViewById(R.id.fbid);
        sLinkedIn = findViewById(R.id.lnkdid);
        sYoutube = findViewById(R.id.ytbid);
    }

    public void retrieveInfoEDT() {
        dataMobile = mMobile.getText().toString();
        dataEmailId = mEmailId.getText().toString();
        dataWebsiteUrl = sWebsiteUrl.getText().toString();
        dataWhatsappNo = sWhatsappNo.getText().toString();
        dataFacebookId = sFacebookID.getText().toString();
        dataLinkedIn = sLinkedIn.getText().toString();
        dataYoutube = sYoutube.getText().toString();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.fillCards_details_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void createCard(View view) {
        retrieveInfoEDT();
        Intent output = new Intent();
        if (dataMobile.length() > 0) {
            output.putExtra(PHONE_NO, dataMobile);
        }
        if (dataEmailId.length() > 0) {
            output.putExtra(EMAIL_ID, dataEmailId);
        }
        if (dataWebsiteUrl.length() > 0) {
            output.putExtra(WEBSITE_URL, dataWebsiteUrl);
        }
        if (dataWhatsappNo.length() > 0) {
            output.putExtra(WHATSAPP_NO, dataWhatsappNo);
        }
        if (dataFacebookId.length() > 0) {
            output.putExtra(FACEBOOK_ID, dataFacebookId);
        }
        if (dataLinkedIn.length() > 0) {
            output.putExtra(LINKEDIN_ID, dataLinkedIn);
        }
        if (dataYoutube.length() > 0) {
            output.putExtra(YOUTUBE_CHANNEL, dataYoutube);
        }

        setResult(Activity.RESULT_OK, output);
        finish();
    }

    private void getIntentData() {
        cardActualWidth = getIntent().getFloatExtra(CARD_WIDTH, 0f);
        cardActualHeight = getIntent().getFloatExtra(CARD_HEIGHT, 0f);

    }


}