package com.example.kartikbhardwaj.bottom_navigation.quiz;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class QuizActivity extends AppCompatActivity {

    ProgressBar remainingTime;
    TextView progressText;
    private Handler handler = new Handler();
    int progressStatus = 0;
    CountDownTimer countDownTimer;
    CircularProgressBar circularProgressBar;
    ProgressBar barTimer;
    String selectedOption = "noOption";
    TextView mainQuestion, optionA, optionB, optionC, optionD, textTimer;
    CardView option1CV,option2CV,option3CV,option4CV;
    RequestQueue quizAnswersRequestQueue;
    Map<String,String> answerRequestHeader = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mainQuestion = findViewById(R.id.questionText);
        optionA = findViewById(R.id.activity_quiz_option_1_text_view);
        optionB = findViewById(R.id.activity_quiz_option_2_text_view);
        optionC = findViewById(R.id.activity_quiz_option_3_text_view);
        optionD = findViewById(R.id.activity_quiz_option_4_text_view);
        option1CV = findViewById(R.id.question_activity_option_1_card_view);
        option2CV = findViewById(R.id.question_activity_option_2_card_view);
        option3CV = findViewById(R.id.question_activity_option_3_card_view);
        option4CV = findViewById(R.id.question_activity_option_4_card_view);

        option1CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = "option1";
            }
        });
        option2CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = "option2";
            }
        });
        option3CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = "option3";
            }
        });
        option4CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = "option4";
            }
        });





       // barTimer = findViewById(R.id.barTimer);
        circularProgressBar = (CircularProgressBar)findViewById(R.id.question_time_remaining);
        textTimer = findViewById(R.id.textTimer);
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
            circularProgressBar.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
            circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            circularProgressBar.setProgressBarWidth(10);
            circularProgressBar.setBackgroundProgressBarWidth(10);
            circularProgressBar.setProgressWithAnimation(100, 20000);
            startTimer(10);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        int animationDuration = 10000; // 2500ms = 2,5s
        ; // Default duration = 1500ms


    }

    private void startTimer(final int seconds) {
        countDownTimer = new CountDownTimer(  seconds * 1000, 500) {
            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;

               // barTimer.setProgress();
                textTimer.setText(String.format("%02d", seconds/60) + ":" + String.format("%02d", seconds%60));
                // format the textview to show the easily readable format

            }
            @Override
            public void onFinish() {
                if(textTimer.getText().equals("00:00")){
                    textTimer.setText("STOP");
                    quizAnswersRequestQueue = Volley.newRequestQueue(getBaseContext());
                    answerRequestHeader.put("","")



                }
                else{
                    textTimer.setText("2:00");
                   // barTimer.setProgress(60*seconds);
                }
            }
        }.start();

    }


}
