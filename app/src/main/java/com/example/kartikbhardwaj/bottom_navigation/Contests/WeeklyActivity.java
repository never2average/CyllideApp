//package com.example.kartikbhardwaj.bottom_navigation.Contests;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
//import com.example.kartikbhardwaj.bottom_navigation.Profile_Activity;
//import com.example.kartikbhardwaj.bottom_navigation.R;
//import com.google.android.material.tabs.TabLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.viewpager.widget.PagerAdapter;
//import androidx.viewpager.widget.ViewPager;
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class WeeklyActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_weekly);
//
//
//        TabLayout tabLayout = findViewById(R.id.tab_layout);
//        ImageView imgView=findViewById(R.id.contestbackbutton);
//
//
//        tabLayout.addTab(tabLayout.newTab().setText("Weekly"));
//        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));
//
//        final ViewPager viewPager = findViewById(R.id.view_pager);
//        final PagerAdapter adapter = new WeeklyPagerAdapter
//                (getSupportFragmentManager(),
//                        tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//
//        viewPager.addOnPageChangeListener(new
//                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//           @Override
//           public void onTabSelected(TabLayout.Tab tab) {
//               viewPager.setCurrentItem(tab.getPosition());
//           }
//
//           @Override
//           public void onTabUnselected(TabLayout.Tab tab) {
//
//           }
//
//           @Override
//           public void onTabReselected(TabLayout.Tab tab) {
//
//           }
//
//
//
//        });
//
//        imgView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(WeeklyActivity.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//    }
//}
//
