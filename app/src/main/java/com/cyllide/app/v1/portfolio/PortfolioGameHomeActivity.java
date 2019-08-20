package com.cyllide.app.v1.portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.ConnectionStatus;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.PortfolioGameCardModel;
import com.cyllide.app.v1.PortfolioGamePagerAdapter;
import com.cyllide.app.v1.R;
import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import link.fls.swipestack.SwipeStack;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;
import me.toptas.fancyshowcase.listener.OnViewInflateListener;
import pl.droidsonroids.gif.GifImageView;


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