package com.cyllide.app.beta.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cyllide.app.beta.PortfolioGamePagerAdapter;
import com.cyllide.app.beta.R;
import com.google.android.material.tabs.TabLayout;


public class PortfolioGameHomeActivity extends AppCompatActivity {

   TabLayout tabLayout;
   NonSwipeableViewPager viewPager;
   ImageView backbutton;
   ImageView game, leaderboard, positions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfolio_game_home_activity);
        tabLayout = findViewById(R.id.portfoliogametabs);
        viewPager = findViewById(R.id.view_pager);
        backbutton = findViewById(R.id.portfolio_game_back_button);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        PortfolioGamePagerAdapter pageAdapter = new PortfolioGamePagerAdapter(getSupportFragmentManager(), PortfolioGameHomeActivity.this);
        game = findViewById(R.id.pgi_home_activity_1);
        positions = findViewById(R.id.pgi_home_activity_2);
        leaderboard = findViewById(R.id.pgi_home_activity_3);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               viewPager.setCurrentItem(tab.getPosition());

           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {
               viewPager.setCurrentItem(tab.getPosition());

           }
       });
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        showAppTour();



    }



}