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
    TextInputEditText usernameEditText;
    String phoneNumber;
    MaterialButton proceedButton;
    EditText referralCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        Intent intent = getIntent();
        referralCode = findViewById(R.id.activity_otpverification_referral_input);
        phoneNumber = intent.getStringExtra("phone");
        usernameEditText = findViewById(R.id.input_scName);
        proceedButton = findViewById(R.id.btn_send_otp);
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

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Map<String, String> mHeaders = new ArrayMap<String, String>();
                mHeaders.put("phone", phoneNumber);
                if(usernameEditText.getError() != null) {
                   return;
                }
                mHeaders.put("username", usernameEditText.getText().toString());
                try {
                    RequestQueue requestQueue;
                    requestQueue = Volley.newRequestQueue(getBaseContext());
                    String URL = getResources().getString(R.string.apiBaseURL)+"auth/otp/send/new";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            boolean firstUser = true;
                            Log.d("UsernameActivity",response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("message");
                                if(success.equals("MessageSendingSuccessful")){
                                    Toast.makeText(UsernameActivity.this,"Message Sending Successful",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(UsernameActivity.this,OTPVerification.class);
                                    intent.putExtra("phone",phoneNumber);
                                    intent.putExtra("firstuser",firstUser);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    if(jsonObject.getString("message").equals("InvalidUsername")){
                                        Toast.makeText(UsernameActivity.this,"The username entered does not match the one registered with the phone number",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(UsernameActivity.this,"Message Sending Failed",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            catch (JSONException e) {
                                Log.d("UsernameActivity",e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    })
                    {
                        @Override
                        public Map<String, String> getHeaders () {
                            return mHeaders;
                        }
                    };
                    requestQueue.add(stringRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }


    void checkUsernameValidity(String username) {
        int l = username.length();
        for(int i = 0; i<l;i++){
            char c = username.charAt(i);
            if( c>='A' && c<='Z' || c>='a' && c<='z' || c>='0' && c<='9'){
                continue;
            }
            usernameEditText.setError("Username must be alpha numeric");
            return;
        }

        validityMap.put("phone",phoneNumber.toString());
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
