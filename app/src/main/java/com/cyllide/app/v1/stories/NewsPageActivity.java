package com.cyllide.app.v1.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.cyllide.app.v1.R;

public class NewsPageActivity extends AppCompatActivity {
    private String url;
    private WebView mWebview;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_screen);
        imageView = findViewById(R.id.close_article);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsPageActivity.this,StoriesActivity.class);
                startActivity(intent);
                finish();
            }
        });
        mWebview = findViewById(R.id.fullpage_webview);
        url=getIntent().getStringExtra("newsurl");
        mWebview.loadUrl(url);
    }
}