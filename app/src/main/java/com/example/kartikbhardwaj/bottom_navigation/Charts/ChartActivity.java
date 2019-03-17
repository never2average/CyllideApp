package com.example.kartikbhardwaj.bottom_navigation.Charts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.tabs.TabLayout;


public class ChartActivity extends AppCompatActivity {

    Button oneDay,fiveDay,oneMonth,sixMonth,oneYear;
    WebView webView;
    String ticker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ticker = intent.getStringExtra("ticker");
        setContentView(R.layout.activity_chart);
        oneDay = findViewById(R.id.chart_button_one_day);
        fiveDay = findViewById(R.id.chart_button_five_days);
        oneMonth = findViewById(R.id.chart_button_one_month);
        sixMonth = findViewById(R.id.chart_button_six_months);
        oneYear = findViewById(R.id.chart_button_one_year);
        webView = (WebView) findViewById(R.id.web_view_chart);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/tt.html");
        webView.getSettings().setDomStorageEnabled(true);



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
    public void setJavaScriptInterface(String frequency){
        webView.addJavascriptInterface(new JavaScriptChartInterface(this,ticker,frequency), "Android");
        webView.loadUrl("file:///android_asset/tt.html");
        webView.loadUrl("https://data.cyllide.com");

    }
    public void onFrequencyClick(View view){
        oneDay.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light_max));
        fiveDay.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light_max));
        oneMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light_max));
        oneYear.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light_max));
        sixMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light_max));

        switch (view.getId()) {
            case R.id.chart_button_one_day:
                oneDay.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light));
                setJavaScriptInterface("1 day");
                break;
            case R.id.chart_button_five_days:
                fiveDay.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light));
                setJavaScriptInterface("5 days");
                break;
            case R.id.chart_button_one_month:
                oneMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light));
                setJavaScriptInterface("1 month");
                break;
            case R.id.chart_button_six_months:
                sixMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light));
                setJavaScriptInterface("6 months");
                break;
            case R.id.chart_button_one_year:
                oneYear.setBackgroundColor(ContextCompat.getColor(this,R.color.primary_light));
                setJavaScriptInterface("1 year");
                break;
        }

    }
}
