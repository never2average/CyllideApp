package com.cyllide.app.v1.background.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.annotation.Nullable;

public class GetLatestQuizIDService extends Service {

    QuizAlarmManager alarmManager = new QuizAlarmManager();



    public void getQuizIDService(){
        final String[] quizID = new String[1];
        final Long[] quizStartTime = new Long[1];
        final Map<String,String> mHeaders = new ArrayMap<>();
        mHeaders.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        Toast.makeText(this,"In getQuizIdSrevice",Toast.LENGTH_SHORT);

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
                        quizID[0] = new JSONObject(response).getJSONObject("data").getJSONObject("_id").getString("$oid");
                        quizStartTime[0] = new JSONObject(response).getJSONObject("data").getJSONObject("quizStartTime").getLong("$date");
                        Log.d("Response", quizID[0]);
                        Log.d("Response", Long.toString(quizStartTime[0]));
                        Log.d("Timer",Long.toString(quizStartTime[0] -System.currentTimeMillis()));
                        SharedPreferences.Editor editor = getSharedPreferences("LATESTQUIZ", MODE_PRIVATE).edit();
                        editor.putString("id",quizID[0]);
                        editor.putString("time",Long.toString(quizStartTime[0]));
                        editor.apply();

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

    }

    @Override
    public void onCreate() {
        Log.d("RealityCkeck","OnCreate");
        super.onCreate();
        getQuizIDService();

    }

    /** The service is starting, due to a call to startService() */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("RealityCkeck","OnStart");
        Toast.makeText(this,"In ONStart",Toast.LENGTH_SHORT);
        getQuizIDService();
        alarmManager.setAlarm(this);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("RealityCkeck","OnBind");
        getQuizIDService();
        return null;
    }


}
