package com.cyllide.app.v1.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PhoneAuth extends AppCompatActivity {

    MaterialButton materialButton;
    TextInputEditText phone;
    String input_scName, input_phoneNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        materialButton = findViewById(R.id.btn_send_otp);
        phone = findViewById(R.id.input_phoneNo);


        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_phoneNo = String.valueOf(phone.getText());

                boolean isPhoneValid = checkPhoneNumberValidity(input_phoneNo);
                Intent intent =new Intent(PhoneAuth.this,OTPVerification.class);
                startActivity(intent);

            }
        });
    }


    boolean checkPhoneNumberValidity(final String username) {
        int l = username.length();
        for (int i = 0; i < l; i++) {
            char c = username.charAt(i);
            if (c >= '0' && c <= '9') {
                continue;
            }
            phone.setError("Invalid Phone Number");
            return false;
        }
        if(l != 10) {
            phone.setError("Invalid Phone Number");

            return false;
        }
        else {
            return true;
        }

    }
}
