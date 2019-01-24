package com.example.kartikbhardwaj.bottom_navigation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.kartikbhardwaj.bottom_navigation.notification.NotificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    CircleImageView imageButton;
    ImageView notificationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

        loadfragment(new HomeFragment());

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        notificationImage=findViewById(R.id.notification);
        imageButton=findViewById(R.id.user_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileIntent =new Intent(MainActivity.this,Profile_Activity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,imageButton,ViewCompat.getTransitionName(imageButton));
                startActivity(profileIntent,options.toBundle());
            }
        });
        notificationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notificationIntent =new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(notificationIntent);
            }
        });


    }

        public boolean loadfragment(Fragment fragment) {

            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }
            return false;


        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.pic:
                Intent profileIntent=new Intent(this,Profile_Activity.class);
                startActivity(profileIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.referrals:
                fragment = new ReferralFragment();
                break;

            case R.id.home:
                fragment = new HomeFragment();
                break;

            case R.id.stats:
                fragment = new StatsFragment();
                break;

            case R.id.purchase:
                fragment=new GoldFragment();
                break;

            case R.id.howitworks:
                fragment=new SlideShowFragment();
                break;
        }

        return loadfragment(fragment);
    }
}
