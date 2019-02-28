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
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kartikbhardwaj.bottom_navigation.HomeFragment;
import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
import com.example.kartikbhardwaj.bottom_navigation.OrderHistoryFragment;
import com.example.kartikbhardwaj.bottom_navigation.PasswordChangeStatus;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.example.kartikbhardwaj.bottom_navigation.StatsFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;


public class PortfolioActivity extends AppCompatActivity {

    MaterialCardView stockAnalysis, orderHistory, portfolioPositions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        WebView webView = (WebView) findViewById(R.id.web_view_chart_portfolio);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/tt.html");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        stockAnalysis = findViewById(R.id.stockchooser);
        stockAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AvailableStocksFragment fragment1=new AvailableStocksFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container1,fragment1);
                fragmentTransaction.commit();
            }
        });

        orderHistory = findViewById(R.id.order_history);
        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderHistoryFragment fragment1 = new OrderHistoryFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container1, fragment1);
                fragmentTransaction.commit();
            }
        });

        portfolioPositions = findViewById(R.id.portfolio_positions);
        portfolioPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PortfolioPositionsFragment fragment1 = new PortfolioPositionsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container1, fragment1);
                fragmentTransaction.commit();
            }
        });
    }
}
