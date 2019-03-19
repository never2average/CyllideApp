package com.example.kartikbhardwaj.bottom_navigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.kartikbhardwaj.bottom_navigation.howitworks.HowItWorksFragment;
import com.example.kartikbhardwaj.bottom_navigation.notification.NotificationActivity;
import com.example.kartikbhardwaj.bottom_navigation.HomeFragment;
import com.example.kartikbhardwaj.bottom_navigation.StatsFragment;


import com.example.kartikbhardwaj.bottom_navigation.stories.StoriesActivity;


import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolio;
import com.example.kartikbhardwaj.bottom_navigation.howitworks.HowItWorksFragment;
import com.example.kartikbhardwaj.bottom_navigation.notification.NotificationActivity;
import com.github.clans.fab.FloatingActionMenu;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity{


    Toolbar toolbar;
    ImageView logo;
     com.github.clans.fab.FloatingActionButton notification;
     com.github.clans.fab.FloatingActionButton referrals;
     com.github.clans.fab.FloatingActionButton howItWorks;
     com.github.clans.fab.FloatingActionButton profile;
     com.github.clans.fab.FloatingActionButton home;
    com.github.clans.fab.FloatingActionButton stats;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo=findViewById(R.id.logo);
        SharedPreferences.Editor editor = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE).edit();
//        editor.putString("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiQnVyam9zZSIsImV4cCI6MTU4MjU1NzQzNH0.M9K5ZcW515hWwBe3gNHdVB6AhQRpubfuQFn7xvrpLNg");
        editor.putString("token","Not found!");
        editor.apply();

// floating action button
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
//                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
//            }
//        });
        loadfragment(new HomeFragment());


         final FloatingActionMenu fabMenu;
        fabMenu = findViewById(R.id.fabMenu);
        referrals=findViewById(R.id.referrals);
        notification=findViewById(R.id.notification);
        howItWorks=findViewById(R.id.howitworks);
        home=findViewById(R.id.home);
        profile=findViewById(R.id.profile);
        stats=findViewById(R.id.stats);


        fabMenu.setClosedOnTouchOutside(true);

        home.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
                Toast.makeText(MainActivity.this,"home button clicked",Toast.LENGTH_SHORT).show();
                swaphomeFragment();
                fabMenu.close(true);


            }
        });

        notification.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);

                Intent intent=new Intent(MainActivity.this, NotificationActivity.class);

                startActivity(intent);
                fabMenu.close(true);
            }
        });

        referrals.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
                swapFragment();
                fabMenu.close(true);


            }
        });

       stats .setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
                swapstatFragment();
                fabMenu.close(true);


            }
        });

        howItWorks.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
                swaphiwFragment();
                fabMenu.close(true);


            }
        });

        profile.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
               // Intent profileIntent=new Intent(MainActivity.this,Profile_Activity.class);
               // startActivity(profileIntent);
                swapProfileFragment();

                fabMenu.close(true);


            }
        });





// here
     /*  final FloatingActionButton homeAction = (FloatingActionButton) findViewById(R.id.home);
        homeAction.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
               // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
                Toast.makeText(MainActivity.this,"home button clicked",Toast.LENGTH_SHORT).show();
                swaphomeFragment();

            }
        });

        final FloatingActionButton NotificationAction = (FloatingActionButton) findViewById(R.id.notification);
        NotificationAction.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);

                Intent intent=new Intent(MainActivity.this, NotificationActivity.class);

                startActivity(intent);
            }
        });



        final FloatingActionButton ReferralsAction = (FloatingActionButton) findViewById(R.id.referral);
        ReferralsAction.setTitle("Referrals");
        ReferralsAction.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
                swapFragment();

            }
        });



        final FloatingActionButton statsAction = (FloatingActionButton) findViewById(R.id.stats);
        statsAction.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
                swapstatFragment();

            }
        });

        final FloatingActionButton howItWorks = (FloatingActionButton) findViewById(R.id.howitworks);
        howItWorks.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                // ((FloatingActionsMenu) findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
                swaphiwFragment();

            }
        });
*/
/// till here


        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);


        // do not dellete this comment
        // animation for fab to turn into close arrow after it is clicked

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
                        ? R.drawable.bottom : R.drawable.close_icon);
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
                Intent profileIntent=new Intent(this,Profile_Activity.class);
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
            //return true;
        }
        //return false;

//        HowItWorksFragment newGamefragment = new HowItWorksFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }
    private void swapstatFragment(){
        StatsFragment fragment = new StatsFragment();


        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            //return true;
        }
//        StatsFragment newGamefragment = new StatsFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }
    private void swapFragment(){
        ReferralFragment fragment = new ReferralFragment();

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            //return true;
        }
//        ReferralFragment newGamefragment = new ReferralFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }

    private void swaphomeFragment(){

        HomeFragment fragment = new HomeFragment();

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            //return true;
        }
//        HomeFragment newGamefragment = new HomeFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }


    private void swapProfileFragment(){

        ProfileFragment fragment = new ProfileFragment();

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            //return true;
        }
//        HomeFragment newGamefragment = new HomeFragment();
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
    }


}
