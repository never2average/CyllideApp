package com.cyllide.app.v1;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cyllide.app.v1.authentication.PhoneAuth;
import com.cyllide.app.v1.authentication.UsernameActivity;
import com.cyllide.app.v1.background.services.AppSignatureHelper;
import com.cyllide.app.v1.background.services.GetLatestQuizIDService;
import com.cyllide.app.v1.faq_view.FAQActivity;
import com.cyllide.app.v1.notification.NotificationActivity;


import com.cyllide.app.v1.portfolio.VersionControlActivity;
import com.github.clans.fab.FloatingActionMenu;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements InternetConnectivityListener {



    com.github.clans.fab.FloatingActionButton referrals, feedback, help;
    InternetAvailabilityChecker internetAvailabilityChecker;
    public static String COMPLETED_TUTORIAL_PREF_NAME = "tutorialcompleted";
    public final static int MY_PERMISSION_REQUEST_CODE = 200;

    NotificationManager notificationManager;
    RemoteViews contentView;
    Map<String,String> homepageDataHeaders = new ArrayMap<>();
    RequestQueue homepageQueue;
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


    private boolean setApplicationConstants(){
        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE);
        AppConstants.token = sharedPreferences.getString("token", null);
        if(AppConstants.token==null){
            Intent authIntent = new Intent(MainActivity.this, UsernameActivity.class);
            startActivity(authIntent);
            finish();
            return false;
        }
        else{
            AppConstants.coins = sharedPreferences.getInt("coins", 0);
            AppConstants.referral = sharedPreferences.getString("referralCode","ERROR");
            SharedPreferences sharedPreferences2 =
                    PreferenceManager.getDefaultSharedPreferences(this);
            if (!sharedPreferences2.getBoolean(
                    COMPLETED_TUTORIAL_PREF_NAME, false)) {
               // startActivity(new Intent(MainActivity.this, IntroActivity.class));
            }

        }
        return true;
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


    RequestQueue vcRequestQueue;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        try{
        getSupportActionBar().hide();}
        catch (Exception e){
            Log.e("ERRIRR",e.toString());
        }


        if(!setApplicationConstants()){
            return;
        };

        Context context;
        homepageDataHeaders.put("token", AppConstants.token);
        Log.d("ERROR","INSIDE ONCREATE");
        vcRequestQueue = Volley.newRequestQueue(MainActivity.this);
//        String url =getBaseContext().getResources().getString(R.string.apiBaseURL)+"forced/update";
        String url = getResources().getString(R.string.apiBaseURL)+"info/homepage";
        Log.e("TOKKKEN",AppConstants.token);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = (new JSONObject(response)).getJSONObject("data");
                    Log.d("Summary",jsonObject.toString());
                    int versionCode = BuildConfig.VERSION_CODE;
                    String versionName = BuildConfig.VERSION_NAME;
                    Log.d("RESPONSE",response);

                    int minVersionCOde = jsonObject.getInt("version");
                    String playURL = jsonObject.getString("playurl");
                    AppConstants.username = jsonObject.getString("username");
                    AppConstants.profilePic = jsonObject.getString("profilePicURL");

                    if(versionCode>minVersionCOde){
                        setTheme(R.style.AppTheme_NoActionBar);

                        setUpActivity();
//                        fetchDataVolley();

                    }
                    else{
                        setTheme(R.style.AppTheme_NoActionBar);
                        finish();
                        Intent intent = new Intent(MainActivity.this, VersionControlActivity.class);
                        intent.putExtra("playurl",playURL);
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    Log.e("ERROR",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR",error.toString());

            }
        }){
            @Override
            public Map<String,String> getHeaders(){

                return homepageDataHeaders;            }
        };
        vcRequestQueue.add(stringRequest);



    }

    void setUpActivity(){

        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
//        setApplicationConstants();
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

        AppSignatureHelper appSignature = new AppSignatureHelper(this);
//        Log.d("HASH",appSignature.getAppSignatures().toString());


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



    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        ConnectionStatus.connectionstatus=isConnected;
    }

    void fetchDataVolley() {
        String url = getResources().getString(R.string.apiBaseURL)+"info/homepage";
        homepageQueue = Volley.newRequestQueue(MainActivity.this);
        homepageDataHeaders.put("token", AppConstants.token);
        StringRequest homepageRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("HomeFragment", response);
                    JSONObject jsonObject = new JSONObject(response).getJSONObject("data");
//                    String profileURL = jsonObject.getString("profilePicURL");
                    loadfragment(new HomeFragment());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return homepageDataHeaders;
            }
        };
        homepageQueue.add(homepageRequest);
    }

}

//TODO Integrate bulk API with cards
//TODO Integrate it with portfolio positions
//TODO test with 2 portfolios LEADERBOARD
//TODO CHECK POINTS
//TODO change workflow and ui of authentication
