package com.digitaladvisor.growfast.NavigationItemsFolder.GridsMenuActivityClasses.GreetingsCardsActivities;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digitaladvisor.growfast.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class WebsitePreviewActivity extends AppCompatActivity {
    public final String REF_WEBSITE_LINK = "websiteLink";
    private final String BASE_URL = "http://digitaladvisor.co.in/";
    public final String LOGIN_STATS = "loginStats";
    public static final String TYPE_WEBSITE = "type";

    SharedPreferences sharedPreferences;
    WebView webView;
    TextView websiteLink;
    private ProgressBar pb;
    String getTypeWebsite;
    private String website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_preview);
        initializeViews();

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(website);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                Log.e("progress", "" + progress);
                if (progress == 100) { //...page is fully loaded.
                    // TODO - Add whatever code you need here based on web page load completion...
                    pb.setVisibility(View.GONE);
                }
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private void initializeViews() {
        getTypeWebsite = getIntent().getStringExtra(TYPE_WEBSITE);
        webView = findViewById(R.id.webview);
        websiteLink = findViewById(R.id.created_website);
        sharedPreferences = getSharedPreferences(LOGIN_STATS, Context.MODE_PRIVATE);
        pb = findViewById(R.id.progressPage);

        website = BASE_URL + FirebaseAuth.getInstance().getCurrentUser().getUid() + getTypeWebsite;
        websiteLink.setText(website);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(REF_WEBSITE_LINK, website);
        editor.commit();
    }

    public void copyWebsiteButton(View view) {
        ClipboardManager clipboard = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);
        clipboard.setText(websiteLink.getText().toString());
        Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show();
    }

    public void shareViaWhatsApp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, websiteLink.getText().toString());
        try {
            Objects.requireNonNull(this).startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.whatsapp")));
        }
    }

    public void sharetoOthersPreview(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, websiteLink.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void shareViaWhatsappLogoPreview(View view) {
        shareViaWhatsApp();
    }
}