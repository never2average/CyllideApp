package com.cyllide.app.v1.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.cyllide.app.v1.R;

public class NewsPageActivity extends AppCompatActivity {
    private String url;
    private WebView mWebview;
    long startTime=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_screen);
        mWebview = findViewById(R.id.fullpage_webview);
        url=getIntent().getStringExtra("newsurl");
        mWebview.loadUrl(url);
    }
}