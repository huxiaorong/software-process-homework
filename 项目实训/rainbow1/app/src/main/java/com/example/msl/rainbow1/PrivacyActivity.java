package com.example.msl.rainbow1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class PrivacyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        WebView webView = findViewById(R.id.wv_privacy);
        webView.loadUrl("file:///android_asset/privacy.html");
    }
}
