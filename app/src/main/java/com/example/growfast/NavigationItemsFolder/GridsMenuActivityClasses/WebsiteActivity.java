package com.example.growfast.NavigationItemsFolder.GridsMenuActivityClasses;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.growfast.R;

public class WebsiteActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        webView = (WebView) findViewById(R.id.webview);
//        ProgressBar pb=findViewById(R.id.progressPage);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://bestfashionpoint.com/");
/*//        webView.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                Log.e("progress", ""+progress);
//                if (progress == 100) { //...page is fully loaded.
//                    // TODO - Add whatever code you need here based on web page load completion...
//                    pb.setVisibility(View.GONE);
//                }
//            }
//        });*/
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}