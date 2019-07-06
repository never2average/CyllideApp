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
import com.cyllide.app.v1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class UsernameActivity extends AppCompatActivity {

    RequestQueue validityQueue;
    Map<String,String> validityMap = new ArrayMap<>();
    TextInputEditText usernameEditText, phoneNumberEditText, referralCodeEditText;
    MaterialButton registerButton;
    TextView loginRedirect;
    Map<String, String> signUpMap = new ArrayMap<>();
    RequestQueue signUpQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);
        usernameEditText = findViewById(R.id.input_scName_signup);
        phoneNumberEditText = findViewById(R.id.input_phoneNo_signup);
        referralCodeEditText = findViewById(R.id.input_referral_signup);
        registerButton = findViewById(R.id.signup_btn);
        loginRedirect = findViewById(R.id.login_redirect);
        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsernameActivity.this, PhoneAuth.class);
                startActivity(intent);
                finish();
            }
        });
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
                signUp();
            }
        });
    }


    void signUp(){
        signUpQueue = Volley.newRequestQueue(getBaseContext());
        signUpMap.put("phone", phoneNumberEditText.getText().toString());
        signUpMap.put("username", usernameEditText.getText().toString());
        signUpMap.put("referral", referralCodeEditText.getText().toString());
        String url = getString(R.string.apiBaseURL)+"auth/otp/send/new";
        StringRequest signUpRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    if(message.equals("MessageSendingSuccessful")){
                        Intent intent = new Intent(UsernameActivity.this, OTPVerification.class);
                        intent.putExtra("firstuser",true);
                        intent.putExtra("phone", phoneNumberEditText.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getBaseContext(),"Failed to send SMS",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
          @Override
          public Map<String, String> getHeaders(){
              return signUpMap;
          }
        };
        signUpQueue.add(signUpRequest);
    }


    void checkUsernameValidity(final String username) {
        int l = username.length();
        for(int i = 0; i<l;i++){
            char c = username.charAt(i);
            if( c>='A' && c<='Z' || c>='a' && c<='z' || c>='0' && c<='9'){
                continue;
            }
            usernameEditText.setError("Username must be alpha numeric");
            return;
        }

        validityMap.put("username",username);
        validityQueue = Volley.newRequestQueue(UsernameActivity.this);
        String url = getResources().getString(R.string.apiBaseURL)+"username/validity";
        StringRequest validityRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String status = new JSONObject(response).getString("status");
                    if(status.equals("taken")){
                        usernameEditText.setError("username already taken");
                    }
                    else{
                        usernameEditText.setError(null);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return validityMap;
            }
        };
        validityQueue.add(validityRequest);
    }
}
