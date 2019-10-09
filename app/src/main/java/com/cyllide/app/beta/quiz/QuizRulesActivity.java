package com.cyllide.app.beta.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.MainActivity;
import com.cyllide.app.beta.R;
import com.cyllide.app.beta.background.services.GetLatestQuizIDService;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;

public class QuizRulesActivity extends AppCompatActivity {

    Button startQuizButton;
    private String quizID;
    private TextView quizPrize;
    private long quizStartTime;
    ImageView backButton;
    Calendar startTime = Calendar.getInstance();
    private Map<String,String> questionHeaders = new ArrayMap<String, String>();
    RequestQueue currentTimeRequestQueue;
    RemoteViews contentView;
    CountDownTimer quizCountDownTimer;
    long currentTime = 0L;
    private String lives;

    @Override
    protected void onPause(){
        if(quizCountDownTimer!=null) {
            quizCountDownTimer.cancel();
            quizCountDownTimer=null;
        }
        super.onPause();
    }

    @Override
    protected void onStop(){
        if(quizCountDownTimer!=null) {
            quizCountDownTimer.cancel();
            quizCountDownTimer=null;
        }
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        if(quizCountDownTimer!=null) {
            quizCountDownTimer.cancel();
            quizCountDownTimer=null;
        }
        super.onDestroy();
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_rules);
        createNotificationChannel();

        Intent serviceIntent = new Intent(this, GetLatestQuizIDService.class);
        startService(serviceIntent);



        currentTimeRequestQueue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.dataApiBaseURL)+"stocks/timeserver";
        StringRequest currentTimeStringRequest = new StringRequest(Request.Method.GET,url , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    currentTime = (new JSONObject(response).getLong("unixtime"))* 1000;
                    Log.d("rules", Long.toString(currentTime));
                    Log.d("rules",Long.toString(new JSONObject(response).getLong("unixtime")));
                    createTimer();
                } catch (JSONException e) {
                    Log.d("rules", e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        });

        currentTimeRequestQueue.add(currentTimeStringRequest);




        startQuizButton=findViewById(R.id.startQuizButton);
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizSwitcher = new Intent(QuizRulesActivity.this,SocketQuizActivity.class);
                quizSwitcher.putExtra("questions","");
                quizSwitcher.putExtra("quizID",quizID);
                startActivity(quizSwitcher);
                finish();            }
        });


        quizPrize = findViewById(R.id.quiz_rules_qrize_text_view);
        backButton = findViewById(R.id.activity_quiz_rules_back_button);
        SharedPreferences sharedPreferences = getSharedPreferences("LATESTQUIZ", 0);
        quizStartTime = Long.parseLong(sharedPreferences.getString("time","0"));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }


    private void createTimer()
    {
//        if (quizStartTime != 0 && quizStartTime-currentTime < 0){
//            Snackbar snackbar = Snackbar
//                    .make(findViewById(R.id.root_layout), "No Quizzes Available", Snackbar.LENGTH_LONG);
//            snackbar.show();
//            startActivity(new Intent(QuizRulesActivity.this,MainActivity.class));
//            finish();
//        }

//
//

        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("token", AppConstants.token);
        try {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(this);
            String URL = getResources().getString(R.string.apiBaseURL)+"quiz/get/latest";

            final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        Log.d("QuizRulesActivity",currentTime+"");
                        lives = new JSONObject(response).getString("lives");
                        quizID = new JSONObject(response).getJSONObject("data").getJSONObject("_id").getString("$oid");
                        quizStartTime = new JSONObject(response).getJSONObject("data").getJSONObject("quizStartTime").getLong("$date");
                        SharedPreferences.Editor edit = getSharedPreferences("LATESTQUIZ",0).edit();
                        edit.putString("time",Long.toString(quizStartTime));
                        edit.putString("id",quizID);
                        edit.apply();
                        Log.d("TIMES","currentTime :"+currentTime+" QuizTime :"+quizStartTime);
                        quizPrize.setText("₹ "+Integer.toString(new JSONObject(response).getJSONObject("data").getInt("quizPrizeMoney")));

                        startQuizButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fetchQuestions(quizID);
                            }
                        });
                        Log.d("QuizRulesActivity1",quizStartTime-currentTime+"");
                        quizCountDownTimer =
                                new CountDownTimer(quizStartTime-currentTime,1000){
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String hour=String.valueOf(millisUntilFinished/(1000*3600));
                                        String days = String.valueOf(Integer.parseInt(hour)/24);
                                        String hours = String.valueOf(Integer.parseInt(hour)%24);
                                        String minute=String.valueOf((millisUntilFinished/(1000*60))%60);
                                        String second=String.valueOf(((millisUntilFinished/1000)%60)%60);
                                        if(Integer.parseInt(days)>0){
                                            startQuizButton.setText("Quiz Starts in "+days+" days "+hours+" hours ");
                                        }
                                        else if(Integer.parseInt(days)<=0 && Integer.parseInt(hours)>0){
                                            startQuizButton.setText("Quiz Starts in "+hours+" hours "+minute+" minutes ");
                                        }
                                        else if(Integer.parseInt(hours)<=0 && Integer.parseInt(minute)>0){
                                            startQuizButton.setText("Quiz Starts in "+minute+" minutes "+second+" seconds ");
                                        }
                                        else{
                                            startQuizButton.setText("Quiz Starts in "+second+" seconds ");
                                        }
                                        startQuizButton.setClickable(true);

                                    }

                                    @Override
                                    public void onFinish() {
                                        fetchQuestions(quizID);
                                    }
                                }.start();
                    } catch (JSONException e) {
                        Snackbar snackbar = Snackbar
                                .make(findViewById(R.id.root_layout), "No Quizzes Available", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        startActivity(new Intent(QuizRulesActivity.this,MainActivity.class));
                        finish();
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.root_layout), "No Quizzes Available", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    startActivity(new Intent(QuizRulesActivity.this,MainActivity.class));
                    finish();
                    Log.e("VOLLEY", error.toString());
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() {
                    return mHeaders;
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse nr) {
                    int n = nr.statusCode;
                    Log.d("Res Code",""+n);
                    return super.parseNetworkResponse(nr);
                }

            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "CGANNEL NAME";
            String description = "CHANNEL DESC";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("LOL", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void fetchQuestions(final String quizID){
        Intent quizSwitcher = new Intent(QuizRulesActivity.this,SocketQuizActivity.class);
        quizSwitcher.putExtra("questions","");
        quizSwitcher.putExtra("quizID",quizID);
        quizSwitcher.putExtra("hearts", Integer.parseInt(lives));
        startActivity(quizSwitcher);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();


        currentTimeRequestQueue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.dataApiBaseURL)+"stocks/timeserver";
        StringRequest currentTimeStringRequest = new StringRequest(Request.Method.GET,url , new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    currentTime = ((new JSONObject(response).getLong("unixtime")))* 1000;
                    createTimer();
                } catch (JSONException e) {
                    Log.d("QuizRulesActivity", e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        })
        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse nr) {
                int n = nr.statusCode;
                Log.d("Res Code",""+n);
                return super.parseNetworkResponse(nr);
            }

        };

        currentTimeRequestQueue.add(currentTimeStringRequest);



    }
}


