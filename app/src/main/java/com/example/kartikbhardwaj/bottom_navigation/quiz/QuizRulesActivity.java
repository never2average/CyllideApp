package com.example.kartikbhardwaj.bottom_navigation.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.AdvancedEncryptionStandard;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.example.kartikbhardwaj.bottom_navigation.backgroundservices.GetLatestQuizIDService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class QuizRulesActivity extends AppCompatActivity {

    Button startQuizButton;
    private String quizID;
    private long quizStartTime;
    Dialog revivePopup;
    Calendar startTime = Calendar.getInstance();
    private Map<String,String> questionHeaders = new ArrayMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_rules);

        Intent serviceIntent = new Intent(this, GetLatestQuizIDService.class);
        startService(serviceIntent);


        startQuizButton=findViewById(R.id.startQuizButton);
        SharedPreferences sharedPreferences = getSharedPreferences("LATESTQUIZ", 0);
        //TODO Remove hardcoded token



        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
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
                        Log.d("Response", quizID);
                        Log.d("Response", Long.toString(quizStartTime));
                        Log.d("Timer",Long.toString(quizStartTime-System.currentTimeMillis()));
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
                                startQuizButton.setText("Quiz Starts in "+days+" days "+hours+" hours.");
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

                            }

                            @Override
                            public void onFinish() {
                                startQuizButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent quizSwitcher = new Intent(QuizRulesActivity.this,QuizActivity.class);
                                        startActivity(quizSwitcher);
                                    }
                                });
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

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchQuestions(quizID);
            }
        });

    }

    private void fetchQuestions(String quizID){
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        String URL = "http://api.cyllide.com/api/client/quiz/get";
        questionHeaders.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        questionHeaders.put("quizID",quizID);
        StringRequest questionRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent quizSwitcher = new Intent(QuizRulesActivity.this,QuizActivity.class);
                quizSwitcher.putExtra("questions",response);
                startActivity(quizSwitcher);
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


