package com.example.kartikbhardwaj.bottom_navigation.phone_authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

public class OTPVerification extends AppCompatActivity {
    private OtpView otpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        otpView = findViewById(R.id.otp_view);
        otpView.requestFocus();
        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                Log.d("onOtpCompleted=>", otp);
            }
        });
    }
}
