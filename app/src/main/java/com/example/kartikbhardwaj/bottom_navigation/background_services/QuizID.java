package com.example.kartikbhardwaj.bottom_navigation.background_services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class QuizID extends IntentService {
    AutoStart alarm = new AutoStart();




    public QuizID() {
        super("QuizID");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        alarm.setAlarm(this);
        super.onStart(intent, startId);
        Log.e("Background Activity","QuizID onStart Called");
        Toast.makeText(this,"QuizIDOnStartCalled",Toast.LENGTH_LONG);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        alarm.setAlarm(this);
        Log.e("Background Activity","onStartCommand called");
        GetQuizID.executeTask(getBaseContext());
        onStart(intent,startId);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {



        }
    }


}