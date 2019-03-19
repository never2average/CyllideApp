package com.example.kartikbhardwaj.bottom_navigation.forum.askquestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Map;

public class AskQuestion extends AppCompatActivity {

    MaterialButton btn1, btn2, btn3, btn4, askQuestionBtn;
    TextInputEditText questionText;
    private RequestQueue askQuestionQueue;
    private Map<String, String> requestHeaders = new ArrayMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        btn1=findViewById(R.id.btntag1);
        btn2=findViewById(R.id.btntag2);
        btn3=findViewById(R.id.btntag3);
        btn4=findViewById(R.id.btntag4);
        askQuestionBtn = findViewById(R.id.proceedtoask);
        questionText = findViewById(R.id.question_text);


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

        askQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionText.getText().toString().length()>15){
                    askQuestionVolley();
                }
            }
        });
    }

    private void askQuestionVolley() {
        askQuestionQueue = Volley.newRequestQueue(this);
        requestHeaders.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        requestHeaders.put("body",questionText.getText().toString());
        JsonArray tags = new JsonArray();
        if(btn1.getTag().toString()=="pressed"){
            tags.add(btn1.getText().toString());
        }
        if(btn2.getTag().toString()=="pressed"){
            tags.add(btn2.getText().toString());
        }
        if(btn3.getTag().toString()=="pressed"){
            tags.add(btn3.getText().toString());
        }
        if(btn4.getTag().toString()=="pressed"){
            tags.add(btn4.getText().toString());
        }

        requestHeaders.put("tags",tags.toString());

        String requestEndpoint = "http://api.cyllide.com/api/client/query/add";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestEndpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AskError",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AskError",error.toString());

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return requestHeaders;
            }
        };
        askQuestionQueue.add(stringRequest);

    }
}
