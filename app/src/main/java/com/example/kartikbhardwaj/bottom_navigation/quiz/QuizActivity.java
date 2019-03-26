package com.example.kartikbhardwaj.bottom_navigation.quiz;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class QuizActivity extends AppCompatActivity {

    ProgressBar remainingTime;
    TextView progressText;
    private Handler handler = new Handler();
    int progressStatus = 0;
    TextView mainQuestion, optionA, optionB, optionC, optionD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mainQuestion = findViewById(R.id.questionText);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        Intent intent = getIntent();
        String allQuestions = intent.getStringExtra("questions");
        Log.d("Questions",allQuestions);
        try {
            JSONArray jsonQuestionArray = new JSONObject(allQuestions).getJSONArray("data");
            JSONObject jsonObject = jsonQuestionArray.getJSONObject(0);
            mainQuestion.setText(jsonObject.getString("theQuestion"));
            JSONArray answerArray = jsonObject.getJSONArray("answerOptions");
            optionA.setText(answerArray.getJSONObject(0).getString("value"));
            optionB.setText(answerArray.getJSONObject(1).getString("value"));
            optionC.setText(answerArray.getJSONObject(2).getString("value"));
            optionD.setText(answerArray.getJSONObject(3).getString("value"));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        CircularProgressBar circularProgressBar = (CircularProgressBar)findViewById(R.id.question_time_remaining);
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        circularProgressBar.setProgressBarWidth(10);
        circularProgressBar.setBackgroundProgressBarWidth(10);
        int animationDuration = 10000; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(100, animationDuration); // Default duration = 1500ms


    }
}
