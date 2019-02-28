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
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kartikbhardwaj.bottom_navigation.HomeFragment;
import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
import com.example.kartikbhardwaj.bottom_navigation.PasswordChangeStatus;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.example.kartikbhardwaj.bottom_navigation.StatsFragment;
import com.google.android.material.navigation.NavigationView;


public class PortfolioActivity extends AppCompatActivity {

    String stockName;
    String newStockName;
    String  buttonStatus;
    String rvStatus;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        stockName = getIntent().getStringExtra("stock_name");
        newStockName=getIntent().getStringExtra("newStockName");
        WebView webView = (WebView) findViewById(R.id.web_view_chart_portfolio);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/tt.html");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);




//        AvailableStocksFragment fragment=new AvailableStocksFragment();
//        FragmentManager fragmentManager=getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.container1,fragment);
//        fragmentTransaction.commit();
//        }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.stockanalysis:
//
//                AvailableStocksFragment fragment1=new AvailableStocksFragment();
//                FragmentManager fragmentManager=getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container1,fragment1);
//                fragmentTransaction.commit();
//                return true;
//
//            case R.id.portfoliopositions:
//
//                PortfolioPositionsFragment fragment=new PortfolioPositionsFragment();
//                FragmentManager fragmentManager1=getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
//                fragmentTransaction1.replace(R.id.container1,fragment);
//                fragmentTransaction1.commit();
//                item.setChecked(true);
//                return true;
//
//            case R.id.orderspending:
//
//                PendingOrdersFragment fragment3=new PendingOrdersFragment();
//                FragmentManager fragmentManager3=getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
//                fragmentTransaction3.replace(R.id.container1,fragment3);
//                fragmentTransaction3.commit();
//                item.setChecked(true);
//                return true;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }


}
