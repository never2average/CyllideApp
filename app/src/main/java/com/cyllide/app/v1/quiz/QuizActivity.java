package com.cyllide.app.v1.quiz;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Map;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Vibrator;
import com.github.nkzawa.socketio.client.IO;



public class QuizActivity extends AppCompatActivity {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://chat.socket.io");
        } catch (URISyntaxException e) {}
    }

    public static boolean hasRevive = false;
    public static int numberOfRevivals=0;
    private Handler handler = new Handler();
    CountDownTimer countDownTimer;
    CircularProgressBar circularProgressBar;
    ImageView closePrizePopup;
    ProgressBar pb;
    String selectedOption = "noOption";
    TextView mainQuestion, optionA, optionB, optionC, optionD, textTimer, viewersTV;
    MaterialCardView option1CV,option2CV,option3CV,option4CV;
    RequestQueue quizAnswersRequestQueue;
    RequestQueue quizAnswersPercentRequestQueue;
    RequestQueue viewRequestQueue;
    RequestQueue reviveRequestQueue;
    RequestQueue winPaytmRequestQueue;
    JSONArray jsonQuestionArray;
    Map<String,String> answerRequestHeader = new ArrayMap<>();
    Map<String,String> viewRequestHeader = new ArrayMap<>();
    Map<String,String> answerPercentRequestHeader = new ArrayMap<>();
    Map<String,String> reviveRequestHeader = new ArrayMap<>();
    Map<String,String> winPaytmRequestHeader = new ArrayMap<>();
    ProgressBar option1PB, option2PB, option3PB, option4PB;
    private int questionID = -1;
    Dialog revivalpopup;
    Dialog quizWinPopup;
    Dialog losersPopup;
    Dialog confirmExitPopup;
    ImageView quizActivityAnswerIndicator, sendUPI;
    private String quizID;
    ImageView exitQuiz;
    MediaPlayer quizMusicPlayer;
    MediaPlayer quizCorrectAnswerMusicPlayer;
    MediaPlayer quizWrongAnswerMusicPlayer;



    private ConnectionQuality mConnectionClass = ConnectionQuality.UNKNOWN;
    private ConnectionClassManager mConnectionClassManager;
    private DeviceBandwidthSampler mDeviceBandwidthSampler;
    private ConnectionChangedListener mListener;


    @Override
    public void onBackPressed() {
        exitConfirmDialog();
    }



    private void exitConfirmDialog(){
        ImageView cross = confirmExitPopup.findViewById(R.id.quiz_exit_confirm_cross);
        Button yes = confirmExitPopup.findViewById(R.id.quiz_exit_confirm_yes);
        Button no = confirmExitPopup.findViewById(R.id.quiz_exit_confirm_no);
        confirmExitPopup.show();
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmExitPopup.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmExitPopup.dismiss();
                startActivity(new Intent(QuizActivity.this, MainActivity.class));
                finish();
                questionID = -1;
                quizMusicPlayer.stop();
                if(countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                quizCorrectAnswerMusicPlayer.stop();
                quizWrongAnswerMusicPlayer.stop();


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmExitPopup.dismiss();
            }
        });

    }


    private class ConnectionChangedListener
            implements ConnectionClassManager.ConnectionClassStateChangeListener {

        @Override
        public void onBandwidthStateChange(final ConnectionQuality bandwidthState) {
            mConnectionClass = bandwidthState;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("QuizActivity",mConnectionClass.name());
                    if(mConnectionClass==ConnectionQuality.POOR){
                        Toast.makeText(getBaseContext(),"Poor connection Detected",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quizMusicPlayer= MediaPlayer.create(getApplicationContext(), R.raw.quiz_backgorund_sound);
        quizMusicPlayer.start();
        quizMusicPlayer.setLooping(true);

        quizCorrectAnswerMusicPlayer = MediaPlayer.create(getApplicationContext(),R.raw.correct_answer_sound);

        quizWrongAnswerMusicPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong_answer_sound);



        mConnectionClassManager = ConnectionClassManager.getInstance();
        mDeviceBandwidthSampler = DeviceBandwidthSampler.getInstance();
        mConnectionClassManager.register(mListener);






        setContentView(R.layout.activity_quiz);
        revivalpopup=new Dialog(this);
        revivalpopup.setContentView(R.layout.quiz_revival_xml);
        revivalpopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        confirmExitPopup = new Dialog(this);
        confirmExitPopup.setContentView(R.layout.quiz_exit_confirm_dialog);
        confirmExitPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        quizWinPopup = new Dialog(this);
        quizWinPopup.setContentView(R.layout.quiz_wining_xml);
        quizWinPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        losersPopup=new Dialog(this);
        losersPopup.setContentView(R.layout.quiz_loser_popup);
        losersPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageView = losersPopup.findViewById(R.id.close_loser_popup);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                losersPopup.dismiss();
                startActivity(new Intent(QuizActivity.this,MainActivity.class));
                finish();
            }
        });

        losersPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                startActivity(new Intent(QuizActivity.this,MainActivity.class));
                finish();
            }
        });

        mainQuestion = findViewById(R.id.questionText);
        exitQuiz = findViewById(R.id.exit_quiz);
        exitQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitConfirmDialog();

            }
        });

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

        viewersTV = findViewById(R.id.activity_quiz_viewers_text_view);
        quizActivityAnswerIndicator = findViewById(R.id.activity_quiz_indicator);




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
        pb = findViewById(R.id.progressBarToday);
        circularProgressBar = findViewById(R.id.question_time_remaining);
        textTimer = findViewById(R.id.textTimer);
        Intent intent = getIntent();
        String allQuestions = intent.getStringExtra("questions");
        quizID = intent.getStringExtra("quizID");
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
        countDownTimer = new CountDownTimer(  seconds * 1000, 1) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                pb.setProgress((int)leftTimeInMilliseconds);
                textTimer.setText(String.format("%02d", seconds/60) + ":" + String.format("%02d", seconds%60));
            }
            @Override
            public void onFinish() {
                if(textTimer.getText().equals("00:00")){
                    textTimer.setText("STOP");
                    if(questionID == 9){
                        checkAnswer(questionID);
                        finishQuiz(questionID);
                    }
                    else{
                        checkAnswer(questionID);
                    }
                }
                else{
                    textTimer.setText("2:00");
                }
            }
        }.start();

    }

    private boolean checkAnswer(final int questionID){
        mDeviceBandwidthSampler.startSampling();
//        if(selectedOption)
        String url = getResources().getString(R.string.apiBaseURL)+"quiz/submit";
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
                        mDeviceBandwidthSampler.stopSampling();
                        Log.d("QuizACTIVITY", mConnectionClassManager.getCurrentBandwidthQuality().toString());
                        quizMusicPlayer.pause();
                        quizMusicPlayer.seekTo(0);
                        quizActivityAnswerIndicator.setVisibility(View.VISIBLE);
                        textTimer.setVisibility(View.INVISIBLE);
                        JSONObject jsonResponse = new JSONObject(response);
                        Log.d("changequestion","inside response question");
                        showAnswer("");
                        if(jsonResponse.getString("data").equals("Correct")){
                            quizActivityAnswerIndicator.setImageResource(R.drawable.ic_checked);
                            quizCorrectAnswerMusicPlayer.start();
                            Log.d("questionID",Integer.toString(questionID));
                            Log.d("changequestion","calling change change question");
                            if(questionID == 9){
                                finishQuiz(questionID);
                            }

                            else{

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        quizCorrectAnswerMusicPlayer.pause();
                                        quizCorrectAnswerMusicPlayer.seekTo(0);
                                        changeQuestion();


                                    }
                                }, 6000);
                            }


                        }
                        else{
                            quizActivityAnswerIndicator.setImageResource(R.drawable.ic_cancel);
                            quizWrongAnswerMusicPlayer.start();
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                            } else {

                                v.vibrate(500);
                            }
                            if(questionID == 9){
                                losersPopup.show();
                            }
                            else {
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        showRevival();

                                    }
                                }, 2000);
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mDeviceBandwidthSampler.stopSampling();
                    Log.d("QuizACTIVITY", mConnectionClassManager.getCurrentBandwidthQuality().toString());
                    Toast.makeText(QuizActivity.this,"Poor Internet Connection, please try again later",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(QuizActivity.this, MainActivity.class));
                    finish();



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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        questionID = -1;
        quizMusicPlayer.stop();
        if(countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        quizCorrectAnswerMusicPlayer.stop();
        quizWrongAnswerMusicPlayer.stop();
    }

    private void changeQuestion(){
        questionID +=1;
        String url = getResources().getString(R.string.apiBaseURL)+"quiz/getcount";
        viewRequestQueue = Volley.newRequestQueue(QuizActivity.this);
        viewRequestHeader.put("token",AppConstants.token);

        quizMusicPlayer.start();
        JSONObject quizObject = null;
        String id;
        try {
            quizObject = jsonQuestionArray.getJSONObject(questionID);
            id = quizObject.getJSONObject("_id").getString("$oid");
            viewRequestHeader.put("questionID",id);
            viewRequestHeader.put("quizID",quizID);
            viewRequestHeader.put("orderin",Integer.toString(questionID));
        } catch (JSONException e) {
            Log.d("ViewersResponse",e.toString());
        }

        StringRequest submissionRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("submissionResponse", response);
                try {
                    int jsonResponse = new JSONObject(response).getInt("data");
                    Log.d("ViewersResponse",response);
                    viewersTV.setText(Integer.toString(jsonResponse));



                } catch (JSONException e) {
                    Log.d("ViewersResponse",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("QuizACTIVITY", mConnectionClassManager.getCurrentBandwidthQuality().toString());
                Toast.makeText(QuizActivity.this,"Poor Internet Connection, please try again later",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(QuizActivity.this, MainActivity.class));

            }
        }){
            @Override
            public Map<String,String> getHeaders(){

                return viewRequestHeader;
            }
        };

        viewRequestQueue.add(submissionRequest);



        option1CV.setClickable(true);
        option2CV.setClickable(true);
        option3CV.setClickable(true);
        option4CV.setClickable(true);
        quizActivityAnswerIndicator.setVisibility(View.INVISIBLE);
        textTimer.setVisibility(View.VISIBLE);
        selectedOption = "noOption";

        option1PB.setVisibility(View.INVISIBLE);
        option2PB.setVisibility(View.INVISIBLE);
        option3PB.setVisibility(View.INVISIBLE);
        option4PB.setVisibility(View.INVISIBLE);




        option1CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));
        option2CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));
        option3CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));
        option4CV.setBackgroundDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));



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

    RequestQueue quizMoneyRequestQueue;
    Map<String,String> quizMoneyRequestHeader = new ArrayMap<>();

    private void finishQuiz(int questionID){
        if(questionID != 9){
            Toast.makeText(this,"You  lol",Toast.LENGTH_LONG).show();
            losersPopup.show();
        }
        else{
            quizMoneyRequestQueue = Volley.newRequestQueue(this);
            quizMoneyRequestHeader.put("token",AppConstants.token);
            quizMoneyRequestHeader.put("quizID",quizID);
            String url = getResources().getString(R.string.apiBaseURL)+"quiz/reward/display";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("QuizActivityWinning",response);
                    Double prize = 0.0;
                    DecimalFormat format = new DecimalFormat("####0.0");
                    try {
                        prize = new JSONObject(response).getDouble("data");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    TextView quizMoney = quizWinPopup.findViewById(R.id.quiz_winning_prize_money);
                    TextInputEditText upiID = quizWinPopup.findViewById(R.id.upi_id);
                    final String string = upiID.getText().toString();

                    quizWinPopup.show();
                    quizMoney.setText("₹ "+format.format(prize));
                    closePrizePopup = quizWinPopup.findViewById(R.id.close_prize_popup);
                    closePrizePopup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quizWinPopup.dismiss();
                        }
                    });
                    sendUPI = quizWinPopup.findViewById(R.id.upi_id_button);
                    sendUPI.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            quizWinPopup.dismiss();
                            Toast.makeText(QuizActivity.this,"Money will be sent",Toast.LENGTH_LONG).show();
                            winnersMoney(string);
                        }
                    });

                    if(prize==0){
                        quizMoney.setVisibility(View.GONE);
                        sendUPI.setVisibility(View.GONE);
                        upiID.setVisibility(View.GONE);
                        quizWinPopup.findViewById(R.id.quiz_winning_text).setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String,String> getHeaders(){

                    return quizMoneyRequestHeader;
                }
            };
            quizMoneyRequestQueue.add(stringRequest);


        }
    }

    void winnersMoney(String upiID){
        winPaytmRequestQueue = Volley.newRequestQueue(QuizActivity.this);
        winPaytmRequestHeader.put("quizID",quizID);
        winPaytmRequestHeader.put("token",AppConstants.token);
        winPaytmRequestHeader.put("upiID",upiID);
        String url = getResources().getString(R.string.apiBaseURL)+"quiz/reward";
        StringRequest sr = new StringRequest(Request.Method.POST,url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("moneyResponse", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("QuizACTIVITY", mConnectionClassManager.getCurrentBandwidthQuality().toString());
                Toast.makeText(QuizActivity.this,"Poor Internet Connection, please try again later",Toast.LENGTH_LONG).show();
                startActivity(new Intent(QuizActivity.this, MainActivity.class));
                finish();


            }
        }){
            @Override
            public Map<String,String> getHeaders(){

                return winPaytmRequestHeader;
            }
        };

        winPaytmRequestQueue.add(sr);
    }

    private void showAnswer(String response){

        quizAnswersPercentRequestQueue = Volley.newRequestQueue(this);
        answerPercentRequestHeader.put("token",AppConstants.token);
        JSONObject quizObject = null;
        String id = "Something's wrong";
        try {
            quizObject = jsonQuestionArray.getJSONObject(questionID);
            id = quizObject.getJSONObject("_id").getString("$oid");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        answerPercentRequestHeader.put("questionID",id);
        String url = getResources().getString(R.string.apiBaseURL)+"quiz/stats";
        StringRequest answerPercentStringRequest = new StringRequest(Request.Method.GET,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("showAnswer", response);
                try {
                    JSONArray jsonAnswerResponseList = new JSONObject(response).getJSONArray("data").getJSONObject(0).getJSONArray("answerOptions");
                    int numCorrectOptionA = jsonAnswerResponseList.getJSONObject(0).getInt("numResponses");
                    int isOptionACorrect = jsonAnswerResponseList.getJSONObject(0).getInt("isCorrect");

                    int numCorrectOptionB = jsonAnswerResponseList.getJSONObject(1).getInt("numResponses");
                    int isOptionBCorrect = jsonAnswerResponseList.getJSONObject(1).getInt("isCorrect");

                    int numCorrectOptionC = jsonAnswerResponseList.getJSONObject(2).getInt("numResponses");
                    int isOptionCCorrect = jsonAnswerResponseList.getJSONObject(2).getInt("isCorrect");

                    int numCorrectOptionD = jsonAnswerResponseList.getJSONObject(3).getInt("numResponses");
                    int isOptionDCorrect = jsonAnswerResponseList.getJSONObject(3).getInt("isCorrect");

                    int totalResponses = numCorrectOptionA + numCorrectOptionB + numCorrectOptionC + numCorrectOptionD;
//                    viewersTV.setText(Integer.toString(totalResponses));

                    option1PB.setProgressDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.answer_progress_bar_wrong));
                    option2PB.setProgressDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.answer_progress_bar_wrong));
                    option3PB.setProgressDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.answer_progress_bar_wrong));
                    option4PB.setProgressDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.answer_progress_bar_wrong));



                    if(isOptionACorrect == 1){
                        option1PB.setProgressDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.answer_progress_bar_correct));
                    }
                    if(isOptionBCorrect == 1){
                        option2PB.setProgressDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.answer_progress_bar_correct));
                    }
                    if(isOptionCCorrect == 1){
                        option3PB.setProgressDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.answer_progress_bar_correct));
                    }
                    if(isOptionDCorrect == 1){
                        option4PB.setProgressDrawable(ContextCompat.getDrawable(QuizActivity.this,R.drawable.answer_progress_bar_correct));
                    }

                    Toast.makeText(QuizActivity.this,"Showing Answers",Toast.LENGTH_SHORT);
                    option1PB.setVisibility(View.VISIBLE);
                    startAnswerAnimation(option1PB,(numCorrectOptionA*100)/totalResponses,3000);
                    Log.d("percent",Integer.toString((numCorrectOptionA*100)/totalResponses));
                    option2PB.setVisibility(View.VISIBLE);
                    startAnswerAnimation(option2PB,(numCorrectOptionB*100)/totalResponses,3000);
                    option3PB.setVisibility(View.VISIBLE);
                    startAnswerAnimation(option3PB,(numCorrectOptionC*100)/totalResponses,3000);
                    option4PB.setVisibility(View.VISIBLE);
                    startAnswerAnimation(option4PB,(numCorrectOptionD*100)/totalResponses,3000);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("QuizACTIVITY", mConnectionClassManager.getCurrentBandwidthQuality().toString());
                Toast.makeText(QuizActivity.this,"Poor Internet Connection, please try again later",Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(QuizActivity.this, MainActivity.class));

            }
        }){
            @Override
            public Map<String,String> getHeaders(){

                return answerPercentRequestHeader;
            }
        };

        quizAnswersPercentRequestQueue.add(answerPercentStringRequest);






    }

    private void showRevival(){



        if(AppConstants.coins>0 && QuizActivity.numberOfRevivals<2){
            TextView coinsLeft = revivalpopup.findViewById(R.id.quiz_revival_coins_left);
            TextView revivalYes = revivalpopup.findViewById(R.id.text_view_yes);
            TextView revivalNo = revivalpopup.findViewById(R.id.text_view_no);
            CircularProgressBar revivalProgressBar = revivalpopup.findViewById(R.id.revival_progress_bar);
            ProgressBar pb = revivalpopup.findViewById(R.id.progressBarTodayRevival);
            coinsLeft.setText(Integer.toString(AppConstants.coins));

            revivalYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Yes",Toast.LENGTH_SHORT).show();
                    QuizActivity.hasRevive = true;
                    revivalpopup.dismiss();

                }
            });

            revivalNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuizActivity.hasRevive = false;
                    revivalpopup.dismiss();
                    losersPopup.show();
                }
            });

            revivalpopup.show();
            startRevivalTimer(3,pb);
            revivalProgressBar.setProgress(100);
            revivalProgressBar.setProgressWithAnimation(0,6000);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    revivalpopup.dismiss();
                    if(QuizActivity.hasRevive == true){
                        QuizActivity.hasRevive = false;
                        AppConstants.coins -= 1;
                        QuizActivity.numberOfRevivals +=1;
                        SharedPreferences.Editor editor = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE).edit();
                        editor.putInt("coins", AppConstants.coins);
                        editor.apply();
                        JSONObject quizObject = null;
                        try {
                            quizObject = jsonQuestionArray.getJSONObject(questionID);
                            String id = quizObject.getJSONObject("_id").getString("$oid");
                            reviveRequestHeader.put("questionID",id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        reviveRequestQueue = Volley.newRequestQueue(QuizActivity.this);
                        reviveRequestHeader.put("token",AppConstants.token);
                        String url = getResources().getString(R.string.apiBaseURL)+"quiz/revive";
                        StringRequest sr = new StringRequest(Request.Method.POST,url,  new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                quizWrongAnswerMusicPlayer.pause();
                                quizWrongAnswerMusicPlayer.seekTo(0);

                                changeQuestion();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("QuizACTIVITY", mConnectionClassManager.getCurrentBandwidthQuality().toString());
                                Toast.makeText(QuizActivity.this,"Poor Internet Connection, please try again later",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(QuizActivity.this, MainActivity.class));
                                finish();

                            }
                        }){
                            @Override
                            public Map<String,String> getHeaders(){
                                return reviveRequestHeader;
                            }
                        };
                        quizAnswersRequestQueue.add(sr);
                    }
                    else{

                        losersPopup.show();

                    }
                }
            }, 3000);
        }
        else{
            losersPopup.show();
        }
    }


    private void startRevivalTimer(final int seconds, final ProgressBar pb) {
        countDownTimer = new CountDownTimer(  seconds * 1000, 1) {
            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                pb.setProgress((int)leftTimeInMilliseconds);
//                textTimer.setText(String.format("%02d", seconds/60) + ":" + String.format("%02d", seconds%60));
            }
            @Override
            public void onFinish() {
//
            }
        }.start();

    }



    private void startAnswerAnimation(ProgressBar mProgressBar, int percent, int duration){
        Log.d("HEAR","PROGRESS BAR");
        mProgressBar.setVisibility(View.VISIBLE);

        mProgressBar.setMax(100000);
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mProgressBar,"progress",0,percent*1000);
        progressAnimator.setDuration(duration);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();

    }


}