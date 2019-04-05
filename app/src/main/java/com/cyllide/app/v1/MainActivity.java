package com.cyllide.app.v1;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.cyllide.app.v1.background.services.GetLatestQuizIDService;
import com.cyllide.app.v1.faq_view.Faq_Activity;
import com.cyllide.app.v1.howitworks.HowItWorksFragment;
import com.cyllide.app.v1.notification.NotificationActivity;


import com.cyllide.app.v1.portfolio.MyPortfolio;
import com.github.clans.fab.FloatingActionMenu;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements InternetConnectivityListener {


    Toolbar toolbar;
    ImageView logo;
    CircleImageView profilepic;
    ImageView notificationButton;
    com.github.clans.fab.FloatingActionButton referrals, faq, feedback, help;
    InternetAvailabilityChecker internetAvailabilityChecker;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    private void setApplicationConstants(){
        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE);
        AppConstants.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8";
//TODO remove hardcoded string using SharedPrefrences

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo=findViewById(R.id.logo);
        notificationButton=findViewById(R.id.notificationicon);
        profilepic=findViewById(R.id.profilePic);

        setApplicationConstants();
        Intent serviceIntent = new Intent(this, GetLatestQuizIDService.class);
        startService(serviceIntent);


        loadfragment(new HomeFragment());
        InternetAvailabilityChecker.init(this);
        internetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        internetAvailabilityChecker.addInternetConnectivityListener(this);



        final FloatingActionMenu fabMenu;
        fabMenu = findViewById(R.id.fabMenu);
        referrals=findViewById(R.id.referrals);
        feedback = findViewById(R.id.feedback);
        help = findViewById(R.id.help);


        fabMenu.setClosedOnTouchOutside(true);



        referrals.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                swapFragment();
                fabMenu.close(true);
            }
        });

       help.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                Intent faqIntent=new Intent(MainActivity.this, Faq_Activity.class);
                startActivity(faqIntent);
            }
        });

       feedback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               fabMenu.close(true);
               Intent feedbackIntent = new Intent(MainActivity.this,FeedbackActivity.class);
               startActivity(feedbackIntent);
           }
       });


        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapProfileFragment();


            }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, NotificationActivity.class);

                startActivity(intent);

            }
        });


        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fabMenu.getMenuIconView().setImageResource(fabMenu.isOpened()
                        ? R.drawable.ic_plus : R.drawable.ic_close);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        fabMenu.setIconToggleAnimatorSet(set);

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
                Intent profileIntent=new Intent(this, ProfileActivity.class);
                startActivity(profileIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchToPortfolioActivity(View view){
        Intent portfolioIntent =new Intent(MainActivity.this,MyPortfolio.class);
        startActivity(portfolioIntent);
    }

    private void swaphiwFragment(){
        HowItWorksFragment fragment = new HowItWorksFragment();


        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
         }
    }

    private void swapFragment(){
        ReferralFragment fragment = new ReferralFragment();

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    private void swaphomeFragment(){

        HomeFragment fragment = new HomeFragment();

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
}


    private void swapProfileFragment(){

        ProfileFragment fragment = new ProfileFragment();

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
         }
    }


    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        ConnectionStatus.connectionstatus=isConnected;



    }
}
