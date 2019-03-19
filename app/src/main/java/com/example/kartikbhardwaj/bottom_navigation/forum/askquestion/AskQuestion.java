package com.example.kartikbhardwaj.bottom_navigation.forum.askquestion;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.button.MaterialButton;

public class AskQuestion extends AppCompatActivity {

    MaterialButton btn1, btn2, btn3, btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        btn1=findViewById(R.id.btntag1);
        btn2=findViewById(R.id.btntag2);
        btn3=findViewById(R.id.btntag3);
        btn4=findViewById(R.id.btntag4);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn1.getTag().equals("pressed"))
                {
                    btn1.setStrokeColorResource(R.color.colorPrimary);
                    btn1.setBackgroundColor(getColor(R.color.white));
                    btn1.setTextColor(getColor(R.color.colorPrimary));
                    btn1.setTag("released");
                }
                else
                {
                    btn1.setStrokeColorResource(R.color.white);
                    btn1.setBackgroundColor(getColor(R.color.colorPrimary));
                    btn1.setTextColor(getColor(R.color.white));
                    btn1.setTag("pressed");
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn2.getTag().equals("pressed"))
                {
                    btn2.setStrokeColorResource(R.color.colorPrimary);
                    btn2.setBackgroundColor(getColor(R.color.white));
                    btn2.setTextColor(getColor(R.color.colorPrimary));
                    btn2.setTag("released");
                }
                else
                {
                    btn2.setStrokeColorResource(R.color.white);
                    btn2.setBackgroundColor(getColor(R.color.colorPrimary));
                    btn2.setTextColor(getColor(R.color.white));
                    btn2.setTag("pressed");
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn3.getTag().equals("pressed"))
                {
                    btn3.setStrokeColorResource(R.color.colorPrimary);
                    btn3.setBackgroundColor(getColor(R.color.white));
                    btn3.setTextColor(getColor(R.color.colorPrimary));
                    btn3.setTag("released");
                }
                else
                {
                    btn3.setStrokeColorResource(R.color.white);
                    btn3.setBackgroundColor(getColor(R.color.colorPrimary));
                    btn3.setTextColor(getColor(R.color.white));
                    btn3.setTag("pressed");
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn4.getTag().equals("pressed"))
                {
                    btn4.setStrokeColorResource(R.color.colorPrimary);
                    btn4.setBackgroundColor(getColor(R.color.white));
                    btn4.setTextColor(getColor(R.color.colorPrimary));
                    btn4.setTag("released");
                }
                else
                {
                    btn4.setStrokeColorResource(R.color.white);
                    btn4.setBackgroundColor(getColor(R.color.colorPrimary));
                    btn4.setTextColor(getColor(R.color.white));
                    btn4.setTag("pressed");
                }
            }
        });
    }
}
