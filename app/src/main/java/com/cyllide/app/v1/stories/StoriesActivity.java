package com.cyllide.app.v1.stories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoriesActivity extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView imageButton;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);


        TabLayout tabLayout =
                (TabLayout) findViewById(R.id.tab_layout);
        back=findViewById(R.id.newsbackbutton);
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.addTab(tabLayout.newTab().setText("Stories"));

        tabLayout.setVisibility(View.GONE);

        final customViewPager viewPager =
                 findViewById(R.id.view_pager);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StoriesActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}

