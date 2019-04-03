package com.cyllide.app.v1.background.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
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

public class QuizAlarmManager extends BroadcastReceiver {

    public void getQuizIDService(final Context context){
        final String[] quizID = new String[1];
        final Long[] quizStartTime = new Long[1];
        final Map<String,String> mHeaders = new ArrayMap<>();
        mHeaders.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");

        try {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(context);
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
                        SharedPreferences.Editor editor = context.getSharedPreferences("LATESTQUIZ", Context.MODE_PRIVATE).edit();
                        editor.putString("id",Long.toString(quizStartTime[0]));
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
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "PowerManager:");
        wl.acquire();
        getQuizIDService(context);

        // Put here YOUR code.
        Toast.makeText(context, "Fetching Latest Quiz Time", Toast.LENGTH_LONG).show(); // For example

        wl.release();
    }

    public void setAlarm(Context context)
    {
        Toast.makeText(context,"In SetAlarm",Toast.LENGTH_SHORT);
        AlarmManager am =(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, QuizAlarmManager.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 100 * 60, pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, QuizAlarmManager.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }


}
