package com.cyllide.app.v1.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class OTPVerification extends AppCompatActivity {
    public static OtpView otpView;
    public static MaterialButton verifyBtn;
    public static String otpFromSMS;
    boolean firstuser;
    RequestQueue requestQueue;
    String phoneNo, enteredOTP;
    Map<String, String> verificationMap = new ArrayMap<>();
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 30*1000;
    LinearLayout resendOTPLayout;
    TextView resendOTP;

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }
    @Override
    protected void onResume() {
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                delay = delay * 2;
                resendOTPLayout.setVisibility(View.VISIBLE);
                handler.postDelayed(runnable, delay);
            }
        }, delay);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNo = getIntent().getStringExtra("phone");
        firstuser = getIntent().getBooleanExtra("firstuser", false);
        setContentView(R.layout.activity_otpverification);
        resendOTPLayout = findViewById(R.id.otp_verify_resend);
        verifyBtn = findViewById(R.id.validate_otp_button);
        otpFromSMS = null;
        otpView = findViewById(R.id.otp_view);
        otpView.requestFocus();
        resendOTP= findViewById(R.id.resend_otp_click);
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOTPLayout.setVisibility(View.INVISIBLE);
                sendOTP();
            }
        });
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

        SmsRetrieverClient client = SmsRetriever.getClient(this /* context */);

        Task<Void> task = client.startSmsRetriever();

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

    void verifyMyOTP() {
        verificationMap.put("phone", phoneNo);
        verificationMap.put("otp", enteredOTP);
        verificationMap.put("first", AppConstants.tempUsername);

        requestQueue = Volley.newRequestQueue(OTPVerification.this);
        String url = getResources().getString(R.string.apiBaseURL) + "auth/otp/verify";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equals("InvalidOTPEntered")) {
                        Toast.makeText(OTPVerification.this, "Invalid OTP Entered", Toast.LENGTH_LONG).show();
                    } else {
                        if (AppConstants.tempUsername.equals("redirect")) {
                            Intent intent = new Intent(OTPVerification.this, SignupPage.class);
                            intent.putExtra("phone", phoneNo);
                            startActivity(intent);
                            finish();
                        }
                    }
                } catch (JSONException e) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        SharedPreferences.Editor editor = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE).edit();
                        editor.putString("token", jsonObject.getString("token"));
                        editor.putInt("coins", jsonObject.getInt("coins"));
                        editor.putString("referralCode", jsonObject.getString("referralCode"));
                        editor.apply();
                        Intent intent = new Intent(OTPVerification.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return verificationMap;
            }
        };
        requestQueue.add(stringRequest);
    }
    RequestQueue signUpQueue;
    Map<String,String> signUpMap = new ArrayMap<>();

    void sendOTP(){
        signUpQueue = Volley.newRequestQueue(getBaseContext());
        signUpMap.put("phone", getIntent().getStringExtra("phone"));
        String url = getString(R.string.apiBaseURL)+"auth/otp/send";
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getString("message").equals("MessageSendingSuccessful")){
                            AppConstants.tempUsername="no-redirect";
                            Snackbar.make(findViewById(R.id.root_layout),"OTP sent successfully",Snackbar.LENGTH_SHORT).show();

                        }
                        else {
                            Snackbar.make(findViewById(R.id.root_layout),"Something went wrong!",Snackbar.LENGTH_SHORT).show();
                        }

                } catch (JSONException e) {
                    Log.d("error", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.root_layout),"Something went wrong!",Snackbar.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                return signUpMap;
            }
        };
        signUpQueue.add(signUpRequest);
    }

}
