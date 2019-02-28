package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.example.kartikbhardwaj.bottom_navigation.OrderHistoryFragment;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;
import com.nex3z.togglebuttongroup.button.LabelToggle;


public class PortfolioActivity extends AppCompatActivity {

    MaterialCardView stockAnalysis, orderHistory, portfolioPositions;
    FrameLayout fl;
    LabelToggle oneDay, oneWeek, oneMonth, oneYear, sixMonths;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        final WebView webView = (WebView) findViewById(R.id.web_view_chart_portfolio);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/oneday.html");

        oneDay = findViewById(R.id.nifty_one_day);
        oneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/oneday.html");
            }
        });

        oneWeek = findViewById(R.id.nifty_one_wk);
        oneWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/oneweek.html");
            }
        });

        oneMonth = findViewById(R.id.nifty_one_mon);
        oneMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/onemonth.html");
            }
        });

        sixMonths = findViewById(R.id.nifty_six_mon);
        sixMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/sixmonths.html");
            }
        });

        oneYear = findViewById(R.id.nifty_one_yr);
        oneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/oneyear.html");
            }
        });


        fl = findViewById(R.id.portfolio_container);


        stockAnalysis = findViewById(R.id.stockchooser);
        stockAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                AvailableStocksFragment fragment1=new AvailableStocksFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.portfolio_container,fragment1);
                fragmentTransaction.commit();
            }
        });

        orderHistory = findViewById(R.id.order_history);
        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                OrderHistoryFragment fragment1 = new OrderHistoryFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.portfolio_container, fragment1);
                fragmentTransaction.commit();
            }
        });

        portfolioPositions = findViewById(R.id.portfolio_positions);
        portfolioPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fl.removeAllViews();
                PortfolioPositionsFragment fragment1 = new PortfolioPositionsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.portfolio_container, fragment1);
                fragmentTransaction.commit();
            }
        });
    }
}
