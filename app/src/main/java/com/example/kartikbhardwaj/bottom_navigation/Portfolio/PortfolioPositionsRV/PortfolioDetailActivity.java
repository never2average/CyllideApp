package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStocksFragment;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.OrderHistoryFragment;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsFragment;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;

public class PortfolioDetailActivity extends AppCompatActivity {

    FrameLayout fl;
    MaterialCardView stockAnalysisButton, orderHistoryButton, portfolioPositionsButton;


    public void setNewFragment(Fragment fragment){

        fl.removeAllViews();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.portfolio_container,fragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_detail);

        fl = findViewById(R.id.portfolio_container);
        stockAnalysisButton = findViewById(R.id.stockchooser);
        orderHistoryButton = findViewById(R.id.order_history);
        portfolioPositionsButton = findViewById(R.id.portfolio_positions);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        if(id.equals("stockAnalysis")){

            AvailableStocksFragment fragment1=new AvailableStocksFragment();
            setNewFragment(fragment1);

        }
        else if(id.equals("orderHistory")){

            OrderHistoryFragment fragment1 = new OrderHistoryFragment();
            setNewFragment(fragment1);

        }
        else if(id.equals("portfolioPositions")){

            PortfolioPositionsFragment fragment1 = new PortfolioPositionsFragment();
            setNewFragment(fragment1);

        }

        stockAnalysisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AvailableStocksFragment fragment1=new AvailableStocksFragment();
                setNewFragment(fragment1);

            }
        });

        orderHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OrderHistoryFragment fragment1 = new OrderHistoryFragment();
                setNewFragment(fragment1);

            }
        });

        portfolioPositionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PortfolioPositionsFragment fragment1 = new PortfolioPositionsFragment();
                setNewFragment(fragment1);

            }
        });

        }
    }

