package com.example.wia2007mad.AllModules;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wia2007mad.R;

public class webViewPage extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewpage); // Set your layout XML file

        // Initialize the WebView
        webView = findViewById(R.id.webViewPage);

        // Enable JavaScript (if needed)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Handle navigation events within the WebView
        webView.setWebViewClient(new WebViewClient());

        // Retrieve the URL from the Intent's extras
        String url = getIntent().getStringExtra("url");

        // Load the specified URL in the WebView
        assert url != null;
        webView.loadUrl(url);
    }

    // Handle the back button to navigate within the WebView
    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
