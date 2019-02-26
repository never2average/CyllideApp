package com.example.kartikbhardwaj.bottom_navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PhoneAuth extends AppCompatActivity {

    MaterialButton materialButton;
    TextInputEditText phone, sc_name;
    String input_scName, input_phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        materialButton = findViewById(R.id.btn_send_otp);
        phone = findViewById(R.id.input_phoneNo);
        sc_name = findViewById(R.id.input_scName);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_phoneNo = String.valueOf(phone.getText());
                input_scName = String.valueOf(sc_name.getText());
                Log.d("",input_phoneNo);
            }
        });
    }
}
