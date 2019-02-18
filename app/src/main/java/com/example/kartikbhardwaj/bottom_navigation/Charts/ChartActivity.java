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

//        TabLayout tabLayout =
//                (TabLayout) findViewById(R.id.tab_layout_chart);
//        tabLayout.addTab(tabLayout.newTab().setText("Summary"));
//        tabLayout.addTab(tabLayout.newTab().setText("Income Statement"));
//        tabLayout.addTab(tabLayout.newTab().setText("Balance Sheet"));
//
//
//        final ViewPager viewPager =
//                (ViewPager) findViewById(R.id.view_pager_chart);
//        final PagerAdapter adapter = new ChartPagerAdapter
//                (getSupportFragmentManager(),
//                        tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//
//        viewPager.addOnPageChangeListener(new
//                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//
//        });
}
}
