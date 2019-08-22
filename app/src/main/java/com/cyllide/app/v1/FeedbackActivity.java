package com.cyllide.app.v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    MaterialButton feedBackBtn;
    TextInputEditText feedbackEdittext;
    RequestQueue requestQueue;
    Map<String,String> feedbackMaps = new ArrayMap<>();
    ImageView closeFeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedBackBtn = findViewById(R.id.send_feedback);
        feedbackEdittext = findViewById(R.id.feedback_text);
        feedBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(feedbackEdittext.getText().toString().equals("")){
                    return;
                }
                sendEmailVolley();
            }
        });
        closeFeedBack = findViewById(R.id.close_feedback);
        closeFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    void sendEmailVolley() {
        findViewById(R.id.linearlayout).setVisibility(View.GONE);
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
        requestQueue = Volley.newRequestQueue(FeedbackActivity.this);
        String url = getResources().getString(R.string.apiBaseURL)+"sendfeedback";
        feedbackMaps.put("token",AppConstants.token);
        feedbackMaps.put("text",feedbackEdittext.getText().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                findViewById(R.id.loading).setVisibility(View.GONE);
                findViewById(R.id.linearlayout).setVisibility(View.VISIBLE);
                feedBackBtn.setClickable(false);
                Toast.makeText(FeedbackActivity.this,"Thanks a lot for your support",Toast.LENGTH_LONG).show();
                feedbackEdittext.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                findViewById(R.id.loading).setVisibility(View.GONE);
                findViewById(R.id.linearlayout).setVisibility(View.VISIBLE);
                Toast.makeText(FeedbackActivity.this,"The feedback could not be submitted successfully",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return feedbackMaps;
            }
        };
        requestQueue.add(stringRequest);
    }
}
