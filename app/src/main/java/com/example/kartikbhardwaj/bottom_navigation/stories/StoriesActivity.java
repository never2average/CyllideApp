package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kartikbhardwaj.bottom_navigation.Profile_Activity;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoriesActivity extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView imageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        toolbar = findViewById(R.id.tool_bar_stories);
        setSupportActionBar(toolbar);

        imageButton=findViewById(R.id.user_image_stories);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileIntent =new Intent(StoriesActivity.this,Profile_Activity.class);
                startActivity(profileIntent);
            }
        });

        TabLayout tabLayout =
                (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("Stories"));

        final ViewPager viewPager =
                (ViewPager) findViewById(R.id.view_pager);
        final PagerAdapter adapter = new StoriesPagerAdapter
                (getSupportFragmentManager(),
                        tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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

           }

        });

    }
}

