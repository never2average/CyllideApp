package com.example.kartikbhardwaj.bottom_navigation.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.AdvancedEncryptionStandard;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.crypto.Cipher;

public class QuizRulesActivity extends AppCompatActivity {

    Button startQuizButton;
    Calendar startTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_rules);


        startQuizButton=findViewById(R.id.startQuizButton);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION", 0);
        String token = sharedPreferences.getString("token", "Not found!");
        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("token", token);
        try {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(this);
            String URL = "http://api.cyllide.com/api/client/quiz/get/latest";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    byte[] encryptionKey = "##gdvhcbxkwjlei23**ukefdvhbxjscmn".getBytes(StandardCharsets.UTF_8);
                    AdvancedEncryptionStandard advancedEncryptionStandard = new AdvancedEncryptionStandard(encryptionKey);
                    byte[] decryptedText = new byte[0];
                    try {
                        decryptedText = advancedEncryptionStandard.decrypt(response.getBytes(StandardCharsets.UTF_8));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("",new String(decryptedText));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() {
                    return mHeaders;
                }
            };

            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CountDownTimer(startTime.getTimeInMillis()- Calendar.getInstance().getTimeInMillis(),1000){
            @Override
            public void onTick(long millisUntilFinished) {
                //converting millis to sec,min,hour
                String hour=String.valueOf(millisUntilFinished/(1000*3600));
                String minute=String.valueOf((millisUntilFinished/(1000*60))%60);
                String second=String.valueOf(((millisUntilFinished/1000)%60)%60);
                SimpleDateFormat df =new SimpleDateFormat("MM:SS");
                // formatted the time to string
                if(Integer.parseInt(hour)>24){
                    df = new SimpleDateFormat("DD:HH");
                }
                else if(Integer.parseInt(hour)<24 && Integer.parseInt(hour)>=1){
                    df = new SimpleDateFormat("HH:MM");
                }

                String time=df.format(millisUntilFinished);
                //timer.setText(hour+" :"+minute+" : "+second);
                startQuizButton.setText(time);
            }

            @Override
            public void onFinish() {
                startQuizButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                    }

//
// }
                });



            }
        }.start();
    }
}
