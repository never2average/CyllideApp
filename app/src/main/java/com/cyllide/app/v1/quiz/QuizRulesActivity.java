package com.cyllide.app.v1.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.cyllide.app.v1.background.services.GetLatestQuizIDService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
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
    RemoteViews contentView;
    CountDownTimer quizCountDownTimer;

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

//        contentView = new RemoteViews(getPackageName(), R.layout.push_notification_layout);
//        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
//        contentView.setTextViewText(R.id.title, "Polish your Finance-Whiz");
//        contentView.setTextViewText(R.id.text, "Quiz starts in 5 minutes");

        String CHANNEL_ID = "LOL";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_logo)
                .setContentTitle("Polish your finance-whiz")
                .setContentText("Quiz starts in 5 minutes")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1000, builder.build());


        startQuizButton=findViewById(R.id.startQuizButton);
        quizPrize = findViewById(R.id.quiz_rules_qrize_text_view);
        backButton = findViewById(R.id.activity_quiz_rules_back_button);
        SharedPreferences sharedPreferences = getSharedPreferences("LATESTQUIZ", 0);
        quizStartTime = Long.parseLong(sharedPreferences.getString("time","0"));
        if (quizStartTime != 0 && quizStartTime-System.currentTimeMillis() < 0){
            Toast.makeText(QuizRulesActivity.this,"No Quizes Available", Toast.LENGTH_LONG).show();
            startActivity(new Intent(QuizRulesActivity.this,MainActivity.class));
            finish();
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizRulesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("token", AppConstants.token);
        try {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(this);
            String URL = "http://api.cyllide.com/api/client/quiz/get/latest";

            final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.e("RealityCheck",response);
                    Log.e("RealityCheck","Inside onResponse");
                    try {
                        quizID = new JSONObject(response).getJSONObject("data").getJSONObject("_id").getString("$oid");
                        quizStartTime = new JSONObject(response).getJSONObject("data").getJSONObject("quizStartTime").getLong("$date");
                         SharedPreferences.Editor edit = getSharedPreferences("LATESTQUIZ",0).edit();
                        edit.putString("time",Long.toString(quizStartTime));
                        edit.putString("id",quizID);
                        edit.apply();
                        quizPrize.setText("₹ "+Integer.toString(new JSONObject(response).getJSONObject("data").getInt("quizPrizeMoney")));

                        Log.d("Response", quizID);
                        Log.d("Response", Long.toString(quizStartTime));
                        Log.d("Timer",Long.toString(quizStartTime-System.currentTimeMillis()));
                        quizCountDownTimer =
                        new CountDownTimer(quizStartTime-System.currentTimeMillis(),1000){
                            @Override
                            public void onTick(long millisUntilFinished) {
                                String hour=String.valueOf(millisUntilFinished/(1000*3600));
                                String days = String.valueOf(Integer.parseInt(hour)/24);
                                String hours = String.valueOf(Integer.parseInt(hour)%24);
                                String minute=String.valueOf((millisUntilFinished/(1000*60))%60);
                                String second=String.valueOf(((millisUntilFinished/1000)%60)%60);
                                SimpleDateFormat df =new SimpleDateFormat("MM:SS");
                                if(Integer.parseInt(hour)>24){
//                                    df = new SimpleDateFormat("DD:HH");
                                }
                                else if(Integer.parseInt(hour)<24 && Integer.parseInt(hour)>=1){
//                                    df = new SimpleDateFormat("HH hours : MM minutes");
                                }

                                String time=df.format(millisUntilFinished);
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
                        e.printStackTrace();
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

//        startQuizButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fetchQuestions(quizID);
//            }
//        });

    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
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
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        String URL = "http://api.cyllide.com/api/client/quiz/get";
        questionHeaders.put("token", AppConstants.token);
        questionHeaders.put("quizID",quizID);
        StringRequest questionRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent quizSwitcher = new Intent(QuizRulesActivity.this,QuizActivity.class);
                quizSwitcher.putExtra("questions",response);
                quizSwitcher.putExtra("quizID",quizID);
                startActivity(quizSwitcher);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return questionHeaders;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse nr) {
                int n = nr.statusCode;
                Log.d("Res Code",""+n);
                return super.parseNetworkResponse(nr);
            }
        };
        requestQueue.add(questionRequest);
    }

}


