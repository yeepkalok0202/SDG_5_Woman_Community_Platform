package com.example.wia2007mad.AllModules;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wia2007mad.R;

public class webViewPage extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewpage);

        // Initialize the WebView
        webView = findViewById(R.id.webViewPage);

        // Enable JavaScript (if needed)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Handle navigation events within the WebView
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Show the loading dialog when the page starts loading
                progressDialog = ProgressDialog.show(webViewPage.this, "Loading", "Please wait...");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // Dismiss the loading dialog when the page finishes loading
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });

        // Handle progress updates for the WebView


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
            finish();
            super.onBackPressed();
        }
    }
}