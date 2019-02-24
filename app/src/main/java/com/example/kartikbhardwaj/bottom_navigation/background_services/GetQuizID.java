package com.example.kartikbhardwaj.bottom_navigation.background_services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class GetQuizID {
    private final static String MY_PREFS_NAME = "QUIZID";

    public void executeTask(Context context){
        getQuizID(context);
    }

    private void getQuizID(Context context){
        Toast.makeText(context,"onStartCommand called",Toast.LENGTH_LONG);
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("quiz-1", "lol");
        editor.apply();
        Log.e("Background Activity","Collected QuizID");

    }

}
