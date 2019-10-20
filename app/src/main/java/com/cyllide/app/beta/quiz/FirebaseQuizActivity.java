package com.cyllide.app.beta.quiz;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.MainActivity;
import com.cyllide.app.beta.R;
import com.facebook.network.connectionclass.ConnectionClassManager;
import com.facebook.network.connectionclass.ConnectionQuality;
import com.facebook.network.connectionclass.DeviceBandwidthSampler;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
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
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Database;

import android.os.Vibrator;
import com.github.nkzawa.socketio.client.IO;



public class FirebaseQuizActivity extends AppCompatActivity {



    public static boolean hasRevive = false;
    public static int numberOfRevivals=0;
    private Handler handler = new Handler();
    CountDownTimer countDownTimer = null;
    CircularProgressBar circularProgressBar;
    TextView continueButtonPopup;
    ProgressBar pb;
    int correctOptionID = -1;
    String selectedOption = "noOption";
    boolean isCorrect;
    TextView mainQuestion, optionA, optionB, optionC, optionD, textTimer, viewersTV;
    MaterialCardView option1CV,option2CV,option3CV,option4CV;
    RequestQueue winPaytmRequestQueue;
    JSONArray jsonQuestionArray;
    Map<String,String> winPaytmRequestHeader = new ArrayMap<>();
    ProgressBar option1PB, option2PB, option3PB, option4PB;
    private int questionID = 0; // new value for socket
    Dialog revivalpopup;
    Dialog quizWinPopup;
    Dialog losersPopup;
    Dialog confirmExitPopup;
    ImageView quizActivityAnswerIndicator;
    private String quizID;
    String socketQuestionID;
    ImageView exitQuiz;
    MediaPlayer quizMusicPlayer;
    MediaPlayer quizCorrectAnswerMusicPlayer;
    MediaPlayer quizWrongAnswerMusicPlayer;
    DatabaseReference questionsDBRef;
    DatabaseReference playersDBRef;
    String playerQuizID = null;

    private FrameLayout waitingScreen;
    int revivalsUsed;
    int revivalsRemaining;
    boolean quizOver = false;

    boolean isTimerRunning = false;

    //TODO MAJOR FKUP: COINS AND HEARTS MEAN THE SAME THING, REFACTOR WITH PRECAUTION

    private void startMusic(){
        quizMusicPlayer.start();
        quizMusicPlayer.setLooping(true);
    }
    private void stopMusic(){

    }


//    JSONObject quizObject
    private void changeQuestion(int qno, String question, ArrayList<String> answerList){
        startMusic();
        waitingScreen.setVisibility(View.GONE);

        String id;

        option1CV.setClickable(true);
        option2CV.setClickable(true);
        option3CV.setClickable(true);
        option4CV.setClickable(true);
        quizActivityAnswerIndicator.setVisibility(View.INVISIBLE);
        textTimer.setVisibility(View.VISIBLE);
        selectedOption = "noOption";
        isCorrect = false;

        option1PB.setVisibility(View.INVISIBLE);
        option2PB.setVisibility(View.INVISIBLE);
        option3PB.setVisibility(View.INVISIBLE);
        option4PB.setVisibility(View.INVISIBLE);
        option1CV.setBackgroundDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));
        option2CV.setBackgroundDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));
        option3CV.setBackgroundDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));
        option4CV.setBackgroundDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this,R.drawable.drawable_activity_quiz_unselected_option));


        optionA.setTextColor(ContextCompat.getColor(FirebaseQuizActivity.this,R.color.colorPrimary));
        optionB.setTextColor(ContextCompat.getColor(FirebaseQuizActivity.this,R.color.colorPrimary));
        optionC.setTextColor(ContextCompat.getColor(FirebaseQuizActivity.this,R.color.colorPrimary));
        optionD.setTextColor(ContextCompat.getColor(FirebaseQuizActivity.this,R.color.colorPrimary));



            circularProgressBar.setProgress(0);
            mainQuestion.setText("Q."+qno+" "+question);
            optionA.setText(answerList.get(0));
            optionB.setText(answerList.get(1));
            optionC.setText(answerList.get(2));
            optionD.setText(answerList.get(3));
            circularProgressBar.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
            circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            circularProgressBar.setProgressBarWidth(10);
            circularProgressBar.setBackgroundProgressBarWidth(10);
            circularProgressBar.setProgressWithAnimation(100, 20000);
            startTimer(10);


    }

    @Override
    public void onResume(){
        super.onResume();
        questionsDBRef.addValueEventListener(onNewQuestionsAdded);
        playersDBRef.addValueEventListener(numberOfPlayersActive);

        //Attach all listeners

    }
    ValueEventListener onNewQuestionsAdded;
    ValueEventListener numberOfPlayersActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quizID = getIntent().getStringExtra("quizID");
        questionsDBRef = FirebaseDatabase.getInstance().getReference().child("questions");
        playersDBRef = FirebaseDatabase.getInstance().getReference().child("ActivePlayers");
        playerQuizID = playersDBRef.push().getKey();
        if(playerQuizID!= null) {
            playersDBRef.child(playerQuizID).setValue("Playing");
        }

        Log.d(
                "FIREBASE",questionsDBRef.toString()
        );
        Log.d("Value",quizID+"");
        Log.d("Value",AppConstants.token+"");
//        waitingScreen = findViewById(R.id.waiting_layout);
//        waitingScreen.setVisibility(View.VISIBLE);
//        Log.d("Value",)
        countDownTimer = null;


        numberOfPlayersActive = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long numberOfPlayers = dataSnapshot.getChildrenCount();
                viewersTV.setText(numberOfPlayers+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        onNewQuestionsAdded = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(isTimerRunning){return;}
                isTimerRunning = true;

                ArrayList<String> answerList = new ArrayList<>();
                String question = "";
                int currentQuestionNumber = -1;
                Iterable<DataSnapshot> questionList = dataSnapshot.getChildren();
                for(DataSnapshot questionObject: questionList) {
                    question = questionObject.child("theQuestion").getValue(String.class);
                    answerList = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        try {
                            if (questionObject.child("answerOptions").child(i + "").child("isCorrect").getValue(Integer.class) == 1) {
                                correctOptionID = i;
                            }
                            answerList.add(questionObject.child("answerOptions").child(i + "").child("value").getValue(String.class));
                            currentQuestionNumber = questionObject.child("appearancePosition").getValue(Integer.class);
                        }
                        catch(Exception e){
                            Log.d("PREDICTABLE EROR",e.toString());
                            }
                    }
                }
                if(question.equals("")){
                    isTimerRunning = false;
                    return;
                }
                questionID++;
                //TODO UNCOMMENT IT LATER
//                if(currentQuestionNumber == questionID) {
//                    changeQuestion(questionID, question, answerList);
//                }
//                else{
//                    //DISPLAY YOU HAVE MISSED THE QUIZ
//                }

                //TODO COmment it later
                    changeQuestion(questionID, question, answerList);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        questionsDBRef.addValueEventListener(onNewQuestionsAdded);

//        Emitter.Listener winnerMoney = new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                Log.d("QUIZCHUD","WinnerMoney at: "+ Calendar.getInstance().getTime()+"");
//                JSONObject data = new JSONObject();
//                try {
//                    data.put("token",AppConstants.username);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                questionsSocket.emit("winner_token",data);
//                Log.d("HEAAARRRR",args.toString());
//                JSONObject response;
//                try{
//                    response = (JSONObject)args[1];
//
//                }
//                catch (ArrayIndexOutOfBoundsException e){
//                    response = (JSONObject)args[0];
//                }
//                try{
//                    final JSONObject finalResponse = response;
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            try {
//                                finishQuiz(10, finalResponse.getDouble("amount"));
//                            }
//                            catch (Exception e){
//                                Log.d("Inner Try",e.toString());
//                            }
//                        }
//                    });
//                    Log.d("Value",Double.toString(response.getDouble("amount")));
////                    Looper.prepare();
//                    finishQuiz(10,response.getDouble("amount"));
//                }
//                catch(Exception e){
//                    Log.d("QuizSocket",e.toString());
//                }
//                Log.d("HEAAARR",args.toString());
//
//                Log.d("QUIZCHUD","Winnermoney Exit at: "+ Calendar.getInstance().getTime()+"");
//
//
//            }
//        };
//
//
//        Emitter.Listener numActivePlayers = new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
////                waitingScreen.setVisibility(View.GONE);
//                Log.d("QUIZCHUD","numActivePlayers at: "+ Calendar.getInstance().getTime()+"");
//
//                try {
//                    viewersTV.setText(Integer.toString(((JSONObject)args[1]).getInt("value")));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                catch (ArrayIndexOutOfBoundsException e1){
//                    try {
//                        Log.d("QuizActivity",args[0].toString());
//                        viewersTV.setText(Integer.toString(((JSONObject)args[0]).getInt("value")));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Log.d("QUIZCHUD","numActivePlayers Exit at: "+ Calendar.getInstance().getTime()+"");
//
//
//            }
//        };
//


//        Emitter.Listener answerResponseFromServer = new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                String result = "";
////                isCorrect = false;
//                Log.d("QUIZCHUD","amICorrect at: "+ Calendar.getInstance().getTime()+"");
//
//                try {
//                    result = ((JSONObject)args[1]).getString("myresp");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                catch (ArrayIndexOutOfBoundsException e1){
//                    try {
//                        Log.d("QuizActivity",args[0].toString());
//                        result = ((JSONObject)args[0]).getString("myresp");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(result.equals("Correct")){
//                    isCorrect = true;
//                }
//                Log.d("QuizActivity", result);
//                Log.d("QUIZCHUD","amICorrect Exit at: "+ Calendar.getInstance().getTime()+"");
//
//            }
//        };
//
//
//        Emitter.Listener onNewQuestion = new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                FirebaseQuizActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d("QUIZCHUD","NewQuestion at: "+ Calendar.getInstance().getTime()+"");
//
//                        Log.d("FirebaseQuizActivity",args.toString());
//                        JSONObject data;
//                        try {
//                            data = (JSONObject) args[1];
//                        }
//                        catch (ArrayIndexOutOfBoundsException e){
//                            data = (JSONObject) args[0];
//                        }
//                        Log.d("FirebaseQuizActivity",data.toString());
//                        if(!quizOver) {
////                            changeQuestion(data);
//                        }
//                        Log.d("QUIZCHUD","NewQuestion Exit at: "+ Calendar.getInstance().getTime()+"");
//
//
//                    }
//                });
//            }
//        };
//
//        Emitter.Listener onResponseFromServer = new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                Log.d("QUIZCHUD","QuestionAnswer at: "+ Calendar.getInstance().getTime()+"");
//
//                FirebaseQuizActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d("QuizSocketActivity",args.toString());
//
//                        quizMusicPlayer.pause();
//                        quizMusicPlayer.seekTo(0);
//                        quizActivityAnswerIndicator.setVisibility(View.VISIBLE);
//                        textTimer.setVisibility(View.INVISIBLE);
//                        JSONObject jsonResponse;
//                        Log.d("changequestion","inside response question");
//
//                        try {
//                            try {
//                                jsonResponse = (JSONObject) args[1];
//                            }
//                            catch(ArrayIndexOutOfBoundsException e){
//                                jsonResponse = (JSONObject) args[0];
//                            }
//                            showAnswer(jsonResponse);
//                            if(isCorrect){
////                                    isCorrect = false;
//                                quizActivityAnswerIndicator.setImageResource(R.drawable.ic_checked);
//                                quizCorrectAnswerMusicPlayer.start();
//                                Log.d("questionID",Integer.toString(questionID));
//                                Log.d("changequestion","calling change change question");
//                                if(questionID == 10) {
//                                    //check wheter last question
////                                        finishQuiz(questionID,0.0);
//                                }
//                            }
//                            else {
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        Log.d("QUIZCHUD","QuestionAnswer Exit at: "+ Calendar.getInstance().getTime()+"");
//
//
//                    }
//
//
//
//
//
//
//                });
//            }
//        };


        quizMusicPlayer= MediaPlayer.create(getApplicationContext(), R.raw.quiz_backgorund_sound);


        quizCorrectAnswerMusicPlayer = MediaPlayer.create(getApplicationContext(),R.raw.correct_answer_sound);

        quizWrongAnswerMusicPlayer = MediaPlayer.create(getApplicationContext(),R.raw.wrong_answer_sound);




        setContentView(R.layout.activity_quiz);
        waitingScreen = findViewById(R.id.waiting_layout);
        revivalpopup=new Dialog(FirebaseQuizActivity.this);
        revivalpopup.setContentView(R.layout.quiz_revival_xml);
        revivalpopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        confirmExitPopup = new Dialog(FirebaseQuizActivity.this);
        confirmExitPopup.setContentView(R.layout.quiz_exit_confirm_dialog);
        confirmExitPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        quizWinPopup = new Dialog(FirebaseQuizActivity.this);
        quizWinPopup.setContentView(R.layout.quiz_wining_xml);
        quizWinPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        losersPopup=new Dialog(FirebaseQuizActivity.this);
        losersPopup.setContentView(R.layout.quiz_loser_popup);
        losersPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView imageView = losersPopup.findViewById(R.id.close_loser_popup);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                losersPopup.dismiss();
//                startActivity(new Intent(FirebaseQuizActivity.this,MainActivity.class));
                finish();
            }
        });

        losersPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                questionID = -1;
                quizMusicPlayer.stop();
                if(countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                try {
                    quizCorrectAnswerMusicPlayer.stop();
                    quizWrongAnswerMusicPlayer.stop();
                    questionsSocket.close();
                    questionsSocket.disconnect();
//                    startActivity(new Intent(FirebaseQuizActivity.this, MainActivity.class));
                    finish();
                }
                catch(Exception e){
//                    startActivity(new Intent(FirebaseQuizActivity.this, MainActivity.class));
                    finish();
                    Log.d("QuizSocketActivity","Sum Catch Prob");
                }
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
                sendAnswer(0);
                option1CV.setBackgroundDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this,R.drawable.drawable_activity_quiz_selected_option));
                optionA.setTextColor(ContextCompat.getColor(FirebaseQuizActivity.this,R.color.white));
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
                sendAnswer(1);
                option2CV.setBackgroundDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this,R.drawable.drawable_activity_quiz_selected_option));
                optionB.setTextColor(ContextCompat.getColor(FirebaseQuizActivity.this,R.color.white));
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
                sendAnswer(2);
                option3CV.setBackgroundDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this,R.drawable.drawable_activity_quiz_selected_option));
                optionC.setTextColor(ContextCompat.getColor(FirebaseQuizActivity.this,R.color.white));
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
                sendAnswer(3);
                option4CV.setBackgroundDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this,R.drawable.drawable_activity_quiz_selected_option));
                optionD.setTextColor(ContextCompat.getColor(FirebaseQuizActivity.this,R.color.white));
                option1CV.setClickable(false);
                option2CV.setClickable(false);
                option3CV.setClickable(false);
                option4CV.setPressed(true);
            }
        });
        pb = findViewById(R.id.progressBarToday);
        circularProgressBar = findViewById(R.id.question_time_remaining);
        textTimer = findViewById(R.id.textTimer);


//        questionsSocket.connect();
//        questionsSocket.on("numPlayers",numActivePlayers);
//        questionsSocket.on("question_data_response",onNewQuestion);
//        questionsSocket.on("amicorrect", answerResponseFromServer);
//        questionsSocket.on("answer_stat_results",onResponseFromServer);
//        questionsSocket.on("quiz_winners_listener",winnerMoney);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("token",AppConstants.token);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        questionsSocket.emit("special_connect",jsonObject);
//


    }




    private Socket questionsSocket;
    {
        try {
            questionsSocket = IO.socket("https://quiz.cyllide.com");
        } catch (URISyntaxException e) {
            Log.d("QuizActivity",e.toString());
        }
    }


    @Override
    public void onBackPressed() {
//        showRevival();
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
//                startActivity(new Intent(FirebaseQuizActivity.this, MainActivity.class));
                finish();
                questionID = 0;
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



// Socketio legend
//    new questions = NewQuestionEvent
//    send questions = SendQuestionEvent
//    recieve response = RecieveQuestionEvent

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
                isTimerRunning = false;
                textTimer.setText("STOP");

                quizMusicPlayer.pause();
                        quizMusicPlayer.seekTo(0);
                        quizActivityAnswerIndicator.setVisibility(View.VISIBLE);
                        textTimer.setVisibility(View.INVISIBLE);
                        JSONObject jsonResponse;
                        Log.d("changequestion","inside response question");


//                            showAnswer(jsonResponse);
                            if(isCorrect){
//                                    isCorrect = false;
                                quizActivityAnswerIndicator.setImageResource(R.drawable.ic_checked);
                                quizCorrectAnswerMusicPlayer.start();
                                Log.d("questionID",Integer.toString(questionID));
                                Log.d("changequestion","calling change change question");
                                if(questionID == 10) {
                                    FirebaseDatabase.getInstance().getReference().child("Winners").child(AppConstants.username).setValue(AppConstants.token);
                                    quizWinPopup.show();
                                    //check wheter last question
//                                    quizWinPopup.show();
                                    Toast.makeText(FirebaseQuizActivity.this,"You won, write code now",Toast.LENGTH_SHORT).show();
                                    }
                            }

                if(!isCorrect){
                    quizActivityAnswerIndicator.setImageResource(R.drawable.ic_cancel);
                    quizWrongAnswerMusicPlayer.start();
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {

                        v.vibrate(500);
                    }
                    if (questionID >= 9) {
                        losersPopup.show();
                    } else {
                        if(!isRevivalShowing)
                            showRevival();
                    }
                }
                countDownTimer = null;

            }}.start();

    }

    private boolean sendAnswer(final int selectedOptionID){
        option1CV.setClickable(false);
        option2CV.setClickable(false);
        option3CV.setClickable(false);
        option4CV.setClickable(false);
        if(selectedOptionID == -1){
            //SOMETHING is WRONG RETURN
        }
        if(selectedOptionID == correctOptionID){
            isCorrect = true;
        }
        else{
            isCorrect = false;
        }
        return true;
            }


    @Override
    protected void onDestroy() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",AppConstants.token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        questionsSocket.emit("special_disconnect",jsonObject);

        super.onDestroy();
        questionID = -1;
        quizMusicPlayer.stop();
        if(countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        quizCorrectAnswerMusicPlayer.stop();
        quizWrongAnswerMusicPlayer.stop();
        questionsSocket.close();
    }
    @Override
    protected void onPause(){
        super.onPause();

        //TODO Remove all listeners
        questionsDBRef.removeEventListener(onNewQuestionsAdded);
        if(playerQuizID != null) {
            playersDBRef.child(playerQuizID).setValue(null);
        }
        playersDBRef.removeEventListener(numberOfPlayersActive);

        questionID = -1;
        quizMusicPlayer.stop();
        if(countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        quizCorrectAnswerMusicPlayer.stop();
        quizWrongAnswerMusicPlayer.stop();
        super.onDestroy();

    }



    RequestQueue quizMoneyRequestQueue;
    Map<String,String> quizMoneyRequestHeader = new ArrayMap<>();

    private void finishQuiz(int questionID,Double response){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token",AppConstants.token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        quizOver = true;
        questionsSocket.emit("special_disconnect",jsonObject);

        if(questionID != 10){
            Toast.makeText(this,"You  lol",Toast.LENGTH_LONG).show();
            questionsSocket.close();
            quizOver = true;
            losersPopup.show();
        }
        else{



            Double prize = 0.0;
            DecimalFormat format = new DecimalFormat("####0.0");
            try {
                prize = response;
            } catch (Exception e) {
                e.printStackTrace();
            }
            TextView quizMoney = quizWinPopup.findViewById(R.id.quiz_winning_prize_money);
            quizWinPopup.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            ((TextView)quizWinPopup.findViewById(R.id.quiz_win_name)).setText("Congratulations!!! "+AppConstants.username);

            quizWinPopup.show();
            quizMoney.setText("₹ "+format.format(prize));
            continueButtonPopup=quizWinPopup.findViewById(R.id.continue_txtview);
            continueButtonPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quizWinPopup.dismiss();
//                    startActivity(new Intent(FirebaseQuizActivity.this,MainActivity.class));
                    finish();
                }
            });

            quizWinPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    finish();
                }
            });






            quizMoneyRequestQueue = Volley.newRequestQueue(this);
            quizMoneyRequestHeader.put("token",AppConstants.token);
            quizMoneyRequestHeader.put("quizID",quizID);
            String url = getResources().getString(R.string.apiBaseURL)+"quiz/reward/display";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.d("QuizActivityWinning",response);

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
        Log.d("value",upiID);
        winPaytmRequestQueue = Volley.newRequestQueue(FirebaseQuizActivity.this);
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

                Toast.makeText(FirebaseQuizActivity.this,"Poor Internet Connection, please try again later",Toast.LENGTH_LONG).show();
//                startActivity(new Intent(FirebaseQuizActivity.this, MainActivity.class));
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

    private void showAnswer(JSONObject response){
        try {
            Log.d("showAnswer", response.toString());

//            JSONArray jsonAnswerResponseList = new JSONObject(response).getJSONArray("data").getJSONObject(0).getJSONArray("answerOptions");
            JSONArray jsonAnswerResponseList = response.getJSONArray("optionsData");
            int numCorrectOptionA = jsonAnswerResponseList.getJSONObject(0).getInt("numResponses");
            int isOptionACorrect = jsonAnswerResponseList.getJSONObject(0).getInt("isCorrect");

            int numCorrectOptionB = jsonAnswerResponseList.getJSONObject(1).getInt("numResponses");
            int isOptionBCorrect = jsonAnswerResponseList.getJSONObject(1).getInt("isCorrect");

            int numCorrectOptionC = jsonAnswerResponseList.getJSONObject(2).getInt("numResponses");
            int isOptionCCorrect = jsonAnswerResponseList.getJSONObject(2).getInt("isCorrect");

            int numCorrectOptionD = jsonAnswerResponseList.getJSONObject(3).getInt("numResponses");
            int isOptionDCorrect = jsonAnswerResponseList.getJSONObject(3).getInt("isCorrect");

//            int totalResponses = numCorrectOptionA + numCorrectOptionB + numCorrectOptionC + numCorrectOptionD;
            int totalResponses = response.getInt("totalresponses");
            option1PB.setProgressDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this, R.drawable.answer_progress_bar_wrong));
            option2PB.setProgressDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this, R.drawable.answer_progress_bar_wrong));
            option3PB.setProgressDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this, R.drawable.answer_progress_bar_wrong));
            option4PB.setProgressDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this, R.drawable.answer_progress_bar_wrong));


            if (isOptionACorrect == 1) {
                option1PB.setProgressDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this, R.drawable.answer_progress_bar_correct));
            }
            if (isOptionBCorrect == 1) {
                option2PB.setProgressDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this, R.drawable.answer_progress_bar_correct));
            }
            if (isOptionCCorrect == 1) {
                option3PB.setProgressDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this, R.drawable.answer_progress_bar_correct));
            }
            if (isOptionDCorrect == 1) {
                option4PB.setProgressDrawable(ContextCompat.getDrawable(FirebaseQuizActivity.this, R.drawable.answer_progress_bar_correct));
            }

            Toast.makeText(FirebaseQuizActivity.this, "Showing Answers", Toast.LENGTH_SHORT).show();
            option1PB.setVisibility(View.VISIBLE);
            startAnswerAnimation(option1PB, (numCorrectOptionA * 400) / totalResponses, 3000);
            Log.d("percent", Integer.toString((numCorrectOptionA * 400) / totalResponses));
            option2PB.setVisibility(View.VISIBLE);
            startAnswerAnimation(option2PB, (numCorrectOptionB * 400) / totalResponses, 3000);
            option3PB.setVisibility(View.VISIBLE);
            startAnswerAnimation(option3PB, (numCorrectOptionC * 400) / totalResponses, 3000);
            option4PB.setVisibility(View.VISIBLE);
            startAnswerAnimation(option4PB, (numCorrectOptionD * 400) / totalResponses, 3000);
        }
        catch (JSONException e){
            Log.d("FirebaseQuizActivity",e.toString());
        }







    }
    public static boolean isRevivalShowing = false;
    private void showRevival(){
        isRevivalShowing = true;
        Log.d("HEARTSSS", getIntent().getIntExtra("hearts",0)+" "+numberOfRevivals);
        AppConstants.hearts = getIntent().getIntExtra("hearts",0);



        if(AppConstants.hearts > 0 && numberOfRevivals<2){
            Log.d("HEARTSSS", getIntent().getIntExtra("hearts",0)+" "+numberOfRevivals);
            revivalpopup=new Dialog(FirebaseQuizActivity.this);
            revivalpopup.setContentView(R.layout.quiz_revival_xml);
            revivalpopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            isRevivalShowing = false;


            TextView coinsLeft = revivalpopup.findViewById(R.id.quiz_revival_coins_left);
            TextView revivalYes = revivalpopup.findViewById(R.id.text_view_yes);
            TextView revivalNo = revivalpopup.findViewById(R.id.text_view_no);
            CircularProgressBar revivalProgressBar = revivalpopup.findViewById(R.id.revival_progress_bar);
            ProgressBar pb = revivalpopup.findViewById(R.id.progressBarTodayRevival);
            coinsLeft.setText(Integer.toString(AppConstants.hearts));

            revivalYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Yes",Toast.LENGTH_SHORT).show();
                    QuizActivity.hasRevive = true;
                    numberOfRevivals++;
                    revivalpopup.dismiss();


                }
            });

            revivalNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuizActivity.hasRevive = false;
                    revivalpopup.dismiss();
                    quizOver = true;

                    questionsSocket.disconnect();
                    questionsSocket.close();
                    questionID = -1;
                    quizMusicPlayer.stop();
                    if(countDownTimer != null) {
                        countDownTimer.cancel();
                        countDownTimer = null;
                    }
                    quizCorrectAnswerMusicPlayer.stop();
                    quizWrongAnswerMusicPlayer.stop();
                    questionsSocket.close();
                    losersPopup.show();
                    questionsDBRef.removeEventListener(onNewQuestionsAdded);
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
                        AppConstants.hearts -= 1;
                        QuizActivity.numberOfRevivals +=1;
                        SharedPreferences.Editor editor = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE).edit();
                        editor.putInt("coins", AppConstants.hearts);
                        editor.apply();
                        JSONObject quizObject = null;
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("hearts",AppConstants.hearts);
                            jsonObject.put("username",AppConstants.username+"_"+numberOfRevivals);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //todo enter event name;
                        questionsSocket.emit("hearts_updater",jsonObject);
                    }
                    else{
                        quizOver = true;
                        questionsSocket.close();

                        losersPopup.show();

                    }
                }
            }, 3000);
        }
        else{
            Log.d("HEARTSSSLOST", getIntent().getIntExtra("hearts",0)+" "+numberOfRevivals);
            losersPopup=new Dialog(FirebaseQuizActivity.this);
            losersPopup.setContentView(R.layout.quiz_loser_popup);
            losersPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView imageView = losersPopup.findViewById(R.id.close_loser_popup);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    losersPopup.dismiss();
//                    startActivity(new Intent(FirebaseQuizActivity.this,MainActivity.class));
                    finish();
                }
            });

            losersPopup.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    questionID = -1;
                    quizMusicPlayer.stop();
                    if(countDownTimer != null) {
                        countDownTimer.cancel();
                        countDownTimer = null;
                    }
                    try {
                        quizCorrectAnswerMusicPlayer.stop();
                        quizWrongAnswerMusicPlayer.stop();
                        questionsSocket.close();
                        questionsSocket.disconnect();
//                        startActivity(new Intent(FirebaseQuizActivity.this, MainActivity.class));
//                        finish();
                        finish();
                    }
                    catch(Exception e){
//                        startActivity(new Intent(FirebaseQuizActivity.this, MainActivity.class));
                        finish();
                        Log.d("QuizSocketActivity","SUm Catch Prob");
                    }
                }
            });
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