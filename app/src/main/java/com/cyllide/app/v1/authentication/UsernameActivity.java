package com.cyllide.app.v1.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class UsernameActivity extends AppCompatActivity {

    RequestQueue validityQueue;
    Map<String,String> validityMap = new ArrayMap<>();
    TextInputEditText usernameEditText,  referralCodeEditText;
    MaterialButton registerButton;
    Map<String, String> signUpMap = new ArrayMap<>();
    RequestQueue signUpQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        usernameEditText = findViewById(R.id.input_scName_signup);
        referralCodeEditText = findViewById(R.id.input_referral_signup);
        registerButton = findViewById(R.id.signup_btn);

        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkUsernameValidity(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(checkPhoneNoValidity(phoneNumberEditText.getText().toString()) && checkUsernameValidity(usernameEditText.getText().toString())) {
//                    signUp();
//                }
                Intent intent =new Intent(UsernameActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }



//    boolean checkPhoneNoValidity(final String username) {
//        int l = username.length();
//        for (int i = 0; i < l; i++) {
//            char c = username.charAt(i);
//            if (c >= '0' && c <= '9') {
//                continue;
//            }
//            phoneNumberEditText.setError("Invalid Phone Number");
//            registerButton.setEnabled(false);
//            return false;
//        }
//        if(l != 10) {
//            phoneNumberEditText.setError("Invalid Phone Number");
//            registerButton.setEnabled(false);
//
//
//            return false;
//        }
//        else {
//            return true;
//        }
//
//    }


    boolean checkUsernameValidity(final String username) {
        int l = username.length();
        for(int i = 0; i<l;i++){
            char c = username.charAt(i);
            if( c>='A' && c<='Z' || c>='a' && c<='z' || c>='0' && c<='9'){
                continue;
            }
            usernameEditText.setError("Username must be alpha numeric");
            registerButton.setEnabled(false);

            return false;
        }

        validityMap.put("username",username);
        validityQueue = Volley.newRequestQueue(UsernameActivity.this);
        String url = getResources().getString(R.string.apiBaseURL)+"username/validity";

    return true;
    //TODO FIGURE THIS SHIT OUT
    }

}
