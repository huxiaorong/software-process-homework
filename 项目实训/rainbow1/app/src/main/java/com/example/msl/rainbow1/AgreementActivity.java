package com.example.msl.rainbow1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class AgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        WebView webView = findViewById(R.id.wv_agreement);
        webView.loadUrl("file:///android_asset/agreement.html");
    }
}
