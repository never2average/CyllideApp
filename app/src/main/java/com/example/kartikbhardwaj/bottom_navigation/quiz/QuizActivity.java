package com.example.kartikbhardwaj.bottom_navigation.quiz;

import com.example.kartikbhardwaj.bottom_navigation.R;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    ProgressBar remainingTime;
    TextView progressText;
    private Handler handler = new Handler();
    int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        remainingTime = findViewById(R.id.question_time_remaining);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            remainingTime.setProgress(progressStatus);
                            Log.e("Timer",progressStatus+"/"+remainingTime.getMax());
                           // textView.setText(progressStatus+"/"+remainingTime.getMax());
                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }
}
