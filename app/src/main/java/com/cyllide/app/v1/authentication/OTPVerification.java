package com.cyllide.app.v1.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.google.android.material.button.MaterialButton;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class OTPVerification extends AppCompatActivity {
    OtpView otpView;
    MaterialButton verifyBtn;
    RequestQueue requestQueue;
    String phoneNo,enteredOTP;
    Map<String,String> verificationMap = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNo = getIntent().getStringExtra("phone");
        setContentView(R.layout.activity_otpverification);
        verifyBtn = findViewById(R.id.validate_otp_button);
        otpView = findViewById(R.id.otp_view);
        otpView.requestFocus();
        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                enteredOTP = otp;
            }
        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyMyOTP();
            }
        });
    }

    void verifyMyOTP() {
        verificationMap.put("phone",phoneNo);
        verificationMap.put("otp",enteredOTP);
        requestQueue = Volley.newRequestQueue(OTPVerification.this);
        String url = getResources().getString(R.string.apiBaseURL)+"auth/otp/verify";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    SharedPreferences.Editor editor = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE).edit();
                    editor.putString("token", jsonObject.getString("token"));
                    editor.apply();
                    Intent intent = new Intent(OTPVerification.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mybad",error.toString());
            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return verificationMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
