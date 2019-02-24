package com.example.kartikbhardwaj.bottom_navigation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolio;
import com.example.kartikbhardwaj.bottom_navigation.howitworks.HowItWorksFragment;
import com.example.kartikbhardwaj.bottom_navigation.notification.NotificationActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity{

    Toolbar toolbar;
    public static BottomNavigationView bottomNavigationView;
    CircleImageView imageButton;
    ImageView notificationImage;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        bottomNavigationView =findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
      //  bottomNavigationView.setSelectedItemId(R.id.home);
        logo=findViewById(R.id.logo);
        SharedPreferences.Editor editor = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE).edit();
        editor.putString("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiQnVyam9zZSIsImV4cCI6MTU4MjU1NzQzNH0.M9K5ZcW515hWwBe3gNHdVB6AhQRpubfuQFn7xvrpLNg");
        editor.apply();
//        startService(new Intent(this, QuizID.class));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        loadfragment(new HomeFragment());

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
//        notificationImage=findViewById(R.id.notification);
//        imageButton=findViewById(R.id.user_image);


        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SplashScreen.class);
                startActivity(intent);

            }
        });
//
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent profileIntent =new Intent(MainActivity.this,Profile_Activity.class);
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,imageButton,ViewCompat.getTransitionName(imageButton));
//                startActivity(profileIntent,options.toBundle());
//            }
//        });
//        notificationImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent notificationIntent =new Intent(MainActivity.this,NotificationActivity.class);
//                startActivity(notificationIntent);
//            }
//        });
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

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        Fragment fragment = null;
//
//        switch (menuItem.getItemId()) {
//            case R.id.referrals:
//                fragment = new ReferralFragment();
//                break;
//
//            case R.id.home:
//                fragment = new HomeFragment();
//                break;
//
//            case R.id.stats:
//                fragment = new StatsFragment();
//                break;
//
//            case R.id.howitworks:
//                fragment=new HowItWorksFragment();
//                break;
//        }
//
//        return loadfragment(fragment);
//    }

    public void switchToPortfolioActivity(View view){
        Intent portfolioIntent =new Intent(MainActivity.this,MyPortfolio.class);
        startActivity(portfolioIntent);
    }

//
//    public void loadertest(View view)
//    {
//        Intent intent = new Intent(MainActivity.this,SplashScreen.class);
//        startActivity(intent);
//    }
}
