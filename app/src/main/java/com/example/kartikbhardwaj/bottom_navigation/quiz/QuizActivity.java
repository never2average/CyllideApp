package com.example.kartikbhardwaj.bottom_navigation.quiz;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.AppConstants;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class QuizActivity extends AppCompatActivity {

    ProgressBar remainingTime;
    TextView progressText;
    private Handler handler = new Handler();
    int progressStatus = 0;
    CountDownTimer countDownTimer;
    CircularProgressBar circularProgressBar;
    String selectedOption = "noOption";
    TextView mainQuestion, optionA, optionB, optionC, optionD, textTimer;
    MaterialCardView option1CV,option2CV,option3CV,option4CV;
    RequestQueue quizAnswersRequestQueue;
    JSONArray jsonQuestionArray;
    Map<String,String> answerRequestHeader = new ArrayMap<>();
    ProgressBar option1PB, option2PB, option3PB, option4PB;
    private int questionID = -1;

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

        option1PB = findViewById(R.id.activity_quiz_option_1_progress_bar);
        option2PB = findViewById(R.id.activity_quiz_option_2_progress_bar);
        option3PB = findViewById(R.id.activity_quiz_option_3_progress_bar);
        option4PB = findViewById(R.id.activity_quiz_option_4_progress_bar);




        option1CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = optionA.getText().toString();
                option1CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_selected_option));
                optionA.setTextColor(ContextCompat.getColor(QuizActivity.this,R.color.white));
                option2CV.setClickable(false);
                option3CV.setClickable(false);
                option4CV.setClickable(false);
                option1CV.setPressed(true);
            }
        });
        option2CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = optionB.getText().toString();
                option2CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_selected_option));
                optionB.setTextColor(ContextCompat.getColor(QuizActivity.this,R.color.white));
                option1CV.setClickable(false);
                option3CV.setClickable(false);
                option4CV.setClickable(false);
                option2CV.setPressed(true);
            }
        });
        option3CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = optionC.getText().toString();
                option3CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_selected_option));
                optionC.setTextColor(ContextCompat.getColor(QuizActivity.this,R.color.white));
                option2CV.setClickable(false);
                option1CV.setClickable(false);
                option4CV.setClickable(false);
                option3CV.setPressed(true);
            }
        });
        option4CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedOption = optionD.getText().toString();
                option4CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_selected_option));
                optionD.setTextColor(ContextCompat.getColor(QuizActivity.this,R.color.white));
                option1CV.setClickable(false);
                option2CV.setClickable(false);
                option3CV.setClickable(false);
                option4CV.setPressed(true);
            }
        });





        circularProgressBar = findViewById(R.id.question_time_remaining);
        textTimer = findViewById(R.id.textTimer);
        Intent intent = getIntent();
        String allQuestions = intent.getStringExtra("questions");
        Log.d("Questions",allQuestions);
        try {
            JSONObject responseObject = new JSONObject(allQuestions);
            jsonQuestionArray = responseObject.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        changeQuestion();




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
                    showAnswer("");
                    textTimer.setText("STOP");
                    if(questionID == 9){
                        finishQuiz(questionID);
                    }
                    else{
                        checkAnswer(questionID);


                    }




                }
                else{
                    textTimer.setText("2:00");
                   // barTimer.setProgress(60*seconds);
                }
            }
        }.start();

    }

    private boolean checkAnswer(final int questionID){
//        if(selectedOption)
        String url = "http://api.cyllide.com/api/client/quiz/submit";
        try {
            JSONObject quizObject = jsonQuestionArray.getJSONObject(questionID);
            String id = quizObject.getJSONObject("_id").getString("$oid");
            answerRequestHeader.put("token", AppConstants.token);
            answerRequestHeader.put("optionValue",selectedOption);
            answerRequestHeader.put ("questionID",id);
            quizAnswersRequestQueue = Volley.newRequestQueue(QuizActivity.this);
            StringRequest submissionRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("submissionResponse", response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        Log.d("changequestion","inside response question");
                        if(jsonResponse.getString("data").equals("Correct")){
                            Log.d("changequestion","calling change change question");
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    changeQuestion();

                                }
                            }, 4000);


                        }
                        else{
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    showRevival();

                                }
                            }, 2000);

                        }

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

                  return answerRequestHeader;
              }
            };

            quizAnswersRequestQueue.add(submissionRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void changeQuestion(){
        Log.d("changequestion","inside change question");
        questionID +=1;



        option1CV.setClickable(true);
        option2CV.setClickable(true);
        option3CV.setClickable(true);
        option4CV.setClickable(true);

        option1PB.setVisibility(View.INVISIBLE);
        option2PB.setVisibility(View.INVISIBLE);
        option3PB.setVisibility(View.INVISIBLE);
        option4PB.setVisibility(View.INVISIBLE);


        Log.d("Is it changing?",option2CV.getCardBackgroundColor().toString());


        option1CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));
        option2CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));
        option3CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));
        option4CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));


        Log.d("Is it changing?",option2CV.getCardBackgroundColor().toString());

        optionA.setTextColor(ContextCompat.getColor(QuizActivity.this,R.color.colorPrimary));
        optionB.setTextColor(ContextCompat.getColor(QuizActivity.this,R.color.colorPrimary));
        optionC.setTextColor(ContextCompat.getColor(QuizActivity.this,R.color.colorPrimary));
        optionD.setTextColor(ContextCompat.getColor(QuizActivity.this,R.color.colorPrimary));



        JSONObject jsonObject = null;
        try {
            circularProgressBar.setProgress(0);
            jsonObject = jsonQuestionArray.getJSONObject(questionID);
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


    }

    private void finishQuiz(int questionID){
        if(questionID != 9){
            Toast.makeText(this,"You  lol",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"You Win lol",Toast.LENGTH_LONG).show();
        }
    }

    private void showAnswer(String response){
        Toast.makeText(this,"Showing Answers",Toast.LENGTH_SHORT);
        option1PB.setVisibility(View.VISIBLE);
        startAnswerAnimation(option1PB,60);
        option2PB.setVisibility(View.VISIBLE);
        startAnswerAnimation(option2PB,20);
        option3PB.setVisibility(View.VISIBLE);
        startAnswerAnimation(option3PB,70);
        option4PB.setVisibility(View.VISIBLE);
        startAnswerAnimation(option4PB,90);




    }

    private void showRevival(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                showRevival();

            }
        }, 2000);
        SharedPreferences prefs = getSharedPreferences("COINS", MODE_PRIVATE);
        String restoredText = prefs.getString("coinsRemaining", "0");
        if (restoredText.equals("0")) {
            //exitQuizActivity

            DialogFragment dialogFragment = new DialogFragment();
            return;
        }
        else{

        }
        Toast.makeText(QuizActivity.this,"Revival popup",Toast.LENGTH_SHORT).show();

    }


    private void startAnswerAnimation(ProgressBar mProgressBar, int percent){
        mProgressBar.setMax(100000);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mProgressBar,"progress",0,percent*1000);
        progressAnimator.setDuration(3000);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();

    }


}
