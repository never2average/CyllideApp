package com.example.kartikbhardwaj.bottom_navigation.Charts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.tabs.TabLayout;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        WebView webView = (WebView) findViewById(R.id.web_view_chart);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/tt.html");
    }
}
