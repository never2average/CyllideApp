package com.cyllide.app.v1;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.cyllide.app.v1.authentication.PhoneAuth;
import com.cyllide.app.v1.background.services.GetLatestQuizIDService;
import com.cyllide.app.v1.faq_view.FAQActivity;
import com.cyllide.app.v1.intro.IntroActivity;
import com.cyllide.app.v1.notification.NotificationActivity;


import com.cyllide.app.v1.portfolio.MyPortfolio;
import com.github.clans.fab.FloatingActionMenu;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements InternetConnectivityListener {


    Toolbar toolbar;
    ImageView logo;
    CircleImageView profilepic;
    ImageView notificationButton;
    com.github.clans.fab.FloatingActionButton referrals, faq, feedback, help;
    InternetAvailabilityChecker internetAvailabilityChecker;
    public static String COMPLETED_TUTORIAL_PREF_NAME = "tutorialcompleted";
    public final static int MY_PERMISSION_REQUEST_CODE = 200;

    NotificationManager notificationManager;
    RemoteViews contentView;

    boolean doubleBackToExitPressedOnce = false;


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    private void setApplicationConstants(){
        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE);
        AppConstants.token = sharedPreferences.getString("token", null);
        if(AppConstants.token==null){
            Intent authIntent = new Intent(MainActivity.this, PhoneAuth.class);
            startActivity(authIntent);
            finish();
        }
        else{
            AppConstants.coins = sharedPreferences.getInt("coins", 0);
            AppConstants.referral = sharedPreferences.getString("referralCode","ERROR");
            SharedPreferences sharedPreferences2 =
                    PreferenceManager.getDefaultSharedPreferences(this);
            if (!sharedPreferences2.getBoolean(
                    COMPLETED_TUTORIAL_PREF_NAME, false)) {
                startActivity(new Intent(MainActivity.this, IntroActivity.class));
            }
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                     AppConstants.cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                     AppConstants.readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                }
                break;
        }
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        setApplicationConstants();

        InternetAvailabilityChecker.init(this);
        internetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        internetAvailabilityChecker.addInternetConnectivityListener(this);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("Permissions","Not granted");

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                },
                        MY_PERMISSION_REQUEST_CODE);

                Log.d("Permissions","NOt granted");

            }
        }



        notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);


        logo=findViewById(R.id.logo);
        notificationButton=findViewById(R.id.notificationicon);
        profilepic=findViewById(R.id.profilePic);
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
                contentView = new RemoteViews(getPackageName(), R.layout.push_notification_layout);
                contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
                contentView.setTextViewText(R.id.title, "Hello!");
                contentView.setTextViewText(R.id.text, "This is cyllide's first notification");

                Notification.Builder mBuilder = new Notification.Builder(v.getContext())
                        .setSmallIcon(R.drawable.arrow)
                        .setContent(contentView);

                Notification notification = mBuilder.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notification.defaults |= Notification.DEFAULT_SOUND;
                notification.defaults |= Notification.DEFAULT_VIBRATE;
                notificationManager.notify(1, notification);
                fabMenu.close(true);
                Intent faqIntent=new Intent(MainActivity.this, FAQActivity.class);
                startActivity(faqIntent);
                finish();
            }
       });


       feedback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               fabMenu.close(true);
               Intent feedbackIntent = new Intent(MainActivity.this,FeedbackActivity.class);
               startActivity(feedbackIntent);
               finish();
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
                finish();

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
