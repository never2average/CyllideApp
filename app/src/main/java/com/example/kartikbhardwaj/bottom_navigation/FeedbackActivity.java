package com.example.kartikbhardwaj.bottom_navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedBackBtn = findViewById(R.id.send_feedback);
        feedbackEdittext = findViewById(R.id.feedback_text);
        feedBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailVolley();
            }
        });
    }

    void sendEmailVolley() {
        requestQueue = Volley.newRequestQueue(FeedbackActivity.this);
        String url = getResources().getString(R.string.apiBaseURL)+"sendfeedback";
        feedbackMaps.put("token",AppConstants.token);
        feedbackMaps.put("text",feedbackEdittext.getText().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                feedBackBtn.setClickable(false);
                Toast.makeText(FeedbackActivity.this,"Feedback Submitted Successfully",Toast.LENGTH_LONG).show();
                feedbackEdittext.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
