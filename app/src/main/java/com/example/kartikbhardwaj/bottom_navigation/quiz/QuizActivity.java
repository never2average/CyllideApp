package com.example.kartikbhardwaj.bottom_navigation.quiz;

import android.content.Intent;
import com.example.kartikbhardwaj.bottom_navigation.R;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {
    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 10000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView textViewQuestion;
   // private TextView textViewScore;
   // private TextView textViewQuestionCount;
   // private TextView textViewCategory;
   // private TextView textViewDifficulty;
    private TextView textViewCountDown;
   // private RadioGroup rbGroup;
//    private CardView ob1;
//    private CardView ob2;
//    private CardView ob3;
//    private CardView ob4;
    private int final_answer;
    private TextView ob1t;
    private TextView ob2t;
    private TextView ob3t;
    private TextView ob4t;
//    private MaterialButton buttonConfirmNext, clear;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;


    private long timeLeftInMillis;

    private ArrayList<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private ProgressBar progressBarCircle;

    private int score;
    private boolean answered;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewQuestion = findViewById(R.id.text_view_question);
        //textViewScore = findViewById(R.id.text_view_score);
        //textViewQuestionCount = findViewById(R.id.text_view_question_count);
        //textViewCategory = findViewById(R.id.text_view_category);
        //textViewDifficulty = findViewById(R.id.text_view_difficulty);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        //rbGroup = findViewById(R.id.radio_group);
//        ob1 = findViewById(R.id.option_1);
//        ob2 = findViewById(R.id.option_2);
//        ob3 = findViewById(R.id.option_3);
//        ob4 = findViewById(R.id.option_4);
        ob1t=findViewById(R.id.option_1_text);
        ob2t=findViewById(R.id.option_2_text);
        ob3t=findViewById(R.id.option_3_text);
        ob4t=findViewById(R.id.option_4_text);

        //buttonConfirmNext = findViewById(R.id.button_confirm_next);
        //clear = findViewById(R.id.clear);
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        textColorDefaultRb = textViewCountDown.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();

        Intent intent = getIntent();
        int categoryID = intent.getIntExtra(StartingScreenActivity.EXTRA_CATEGORY_ID, 0);
        String categoryName = intent.getStringExtra(StartingScreenActivity.EXTRA_CATEGORY_NAME);
        String difficulty = intent.getStringExtra(StartingScreenActivity.EXTRA_DIFFICULTY);

//        textViewCategory.setText("Category: " + categoryName);
//        textViewDifficulty.setText("Difficulty: " + difficulty);

        if (savedInstanceState == null) {
            QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
            questionList = dbHelper.getQuestions(categoryID, difficulty);
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answered) {
                startCountDown();
                setProgressBarValues();
            } else {
                updateCountDownText();
                showSolution();
            }
        }



      /**  buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (ob1.isChecked() || ob2.isChecked() || ob3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });



        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ob1.setChecked(false);
                ob2.setChecked(false);
                ob3.setChecked(false);
                ob4.setChecked(false);
            }
        });
    */
    }



    private void showNextQuestion() {
        ob1t.setTextColor(textColorDefaultRb);
        ob2t.setTextColor(textColorDefaultRb);
        ob3t.setTextColor(textColorDefaultRb);
        ob4t.setTextColor(textColorDefaultRb);
        //rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            ob1t.setText(currentQuestion.getOption1());
            ob2t.setText(currentQuestion.getOption2());
            ob3t.setText(currentQuestion.getOption3());

            questionCounter++;
            //textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            //buttonConfirmNext.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
            setProgressBarValues();
        } else {
            finishQuiz();
        }
    }


    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 60) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                showSolution();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        progressBarCircle.setProgress((int)(timeLeftInMillis));

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    public void checkAnswer(View v) {

        Log.w("Button Clicked","Button Cliked");
        if(!answered) {
            //countDownTimer.cancel();

//        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
            int answerNr=0;
            final_answer = v.getId();
            switch (v.getId()) {
                case R.id.option_1_text:
                    answerNr = 1;
                    ob1t.setBackgroundResource(R.drawable.quiz_option_selected_bg);
                    ob1t.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case R.id.option_2_text:
                    answerNr = 2;
                    ob2t.setBackgroundResource(R.drawable.quiz_option_selected_bg);
                    ob2t.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case R.id.option_3_text:
                    ob3t.setBackgroundResource(R.drawable.quiz_option_selected_bg);
                    ob3t.setTextColor(Color.parseColor("#ffffff"));
                    answerNr = 3;
                    break;
                case R.id.option_4_text:
                    ob4t.setBackgroundResource(R.drawable.quiz_option_selected_bg);
                    ob4t.setTextColor(Color.parseColor("#ffffff"));
                    answerNr = 4;
            }

            if (answerNr == currentQuestion.getAnswerNr()) {
                score++;
               // textViewScore.setText("Score: " + score);
            }

           // showSolution();
        }
        answered = true;
    }

    private void showSolution() {
//        ob1t.setBackgroundResource(R.drawable.quiz_option_incorrect);
//        ob2t.setBackgroundResource(R.drawable.quiz_option_incorrect);
//        ob3t.setBackgroundResource(R.drawable.quiz_option_incorrect);
//        ob4t.setBackgroundResource(R.drawable.quiz_option_incorrect);
        ob1t.setVisibility(View.GONE);
        ob2t.setVisibility(View.GONE);
        ob3t.setVisibility(View.GONE);
        ob4t.setVisibility(View.GONE);
        if(ob1t.getId()==final_answer){
            ob1t.setVisibility(View.VISIBLE);
            ob1t.setBackgroundResource(R.drawable.quiz_option_selected_incorrect);
        }
        if(ob2t.getId()==final_answer){
            ob2t.setVisibility(View.VISIBLE);
            ob2t.setBackgroundResource(R.drawable.quiz_option_selected_incorrect);
        }
        if(ob3t.getId()==final_answer){
            ob3t.setVisibility(View.VISIBLE);
            ob3t.setBackgroundResource(R.drawable.quiz_option_selected_incorrect);
        }
        if(ob4t.getId()==final_answer){
            ob4t.setVisibility(View.VISIBLE);
            ob4t.setBackgroundResource(R.drawable.quiz_option_selected_incorrect);
        }


        switch (currentQuestion.getAnswerNr()) {
            case 1:
//                if(final_answer == ob1t.getId())
//                {
//                    ob1t.setBackgroundResource(R.drawable.quiz_option_selected_correct);
//                    break;
//                }
                ob1t.setVisibility(View.VISIBLE);
                ob1t.setBackgroundResource(R.drawable.quiz_option_correct);
                ob1t.setTextColor(Color.parseColor("#ffffff"));
                break;
            case 2:
//                if(final_answer == ob2t.getId())
//                {
//                    ob2t.setBackgroundResource(R.drawable.quiz_option_selected_correct);
//                    break;
//                }
                ob2t.setVisibility(View.VISIBLE);
                ob2t.setBackgroundResource(R.drawable.quiz_option_correct);
                ob1t.setTextColor(Color.parseColor("#ffffff"));
                //textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
//                if(final_answer == ob3t.getId())
//                {
//                    ob3t.setBackgroundResource(R.drawable.quiz_option_selected_correct);
//                    break;
//                }
                ob3t.setVisibility(View.VISIBLE);
                ob3t.setBackgroundResource(R.drawable.quiz_option_correct);
                ob1t.setTextColor(Color.parseColor("#ffffff"));
                //textViewQuestion.setText("Answer 3 is correct");
                break;
            case 4:
//                if(final_answer == ob4t.getId())
//                {
//                    ob4t.setBackgroundResource(R.drawable.quiz_option_selected_correct);
//                    break;
//                }
                ob4t.setVisibility(View.VISIBLE);
                ob4t.setBackgroundResource(R.drawable.quiz_option_correct);
                ob1t.setTextColor(Color.parseColor("#ffffff"));
                //textViewQuestion.setText("Answer 3 is correct");
                break;
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                showNextQuestion();
            }
        }, 3000);

//        if (questionCounter < questionCountTotal) {
//            buttonConfirmNext.setText("Next");
//        } else {
//            buttonConfirmNext.setText("Finish");
//        }
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }

    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeLeftInMillis);
        progressBarCircle.setProgress((int) timeLeftInMillis);
    }
}
