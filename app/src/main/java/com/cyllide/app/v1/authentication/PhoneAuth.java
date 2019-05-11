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
import com.cyllide.app.v1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PhoneAuth extends AppCompatActivity {

    MaterialButton materialButton;
    TextInputEditText phone, sc_name;
    String input_scName, input_phoneNo;
    RequestQueue validityQueue;
    Map<String,String> validityMap = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        materialButton = findViewById(R.id.btn_send_otp);
        phone = findViewById(R.id.input_phoneNo);
        sc_name = findViewById(R.id.input_scName);

        sc_name.addTextChangedListener(new TextWatcher() {
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

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_phoneNo = String.valueOf(phone.getText());
                input_scName = String.valueOf(sc_name.getText());

                boolean isPhoneValid = checkPhoneNumberValidity(input_phoneNo);
                if(isPhoneValid){
                    final Map<String, String> mHeaders = new ArrayMap<String, String>();
                    mHeaders.put("phone", input_phoneNo);
                    mHeaders.put("username", input_scName);
                    try {
                        RequestQueue requestQueue;
                        requestQueue = Volley.newRequestQueue(getBaseContext());
                        String URL = getResources().getString(R.string.apiBaseURL)+"auth/otp/send";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                boolean firstUser = false;
                                Log.d("PhoneAuth",response);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("message");
                                    if(success.equals("MessageSendingSuccessful")){
                                        firstUser = jsonObject.getBoolean("firstTimeUser");
                                        Toast.makeText(PhoneAuth.this,"Message Sending Successful",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(PhoneAuth.this,OTPVerification.class);
                                        intent.putExtra("phone",input_phoneNo);
                                        intent.putExtra("firstuser",firstUser);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        if(jsonObject.getString("message").equals("InvalidUsername")){
                                            Toast.makeText(PhoneAuth.this,"The username entered does not match the one registered with the phone number",Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(PhoneAuth.this,"Message Sending Failed",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                                catch (JSONException e) {

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
                else{
                    Toast.makeText(PhoneAuth.this,"InvalidPhoneNumber",Toast.LENGTH_LONG).show();
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
            sc_name.setError("Username must be alpha numeric");
            return;
        }

        validityMap.put("phone",phone.getText().toString());
        validityMap.put("username",username);
        validityQueue = Volley.newRequestQueue(PhoneAuth.this);
        String url = getResources().getString(R.string.apiBaseURL)+"username/validity";
        StringRequest validityRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String status = new JSONObject(response).getString("status");
                    if(status.equals("taken")){
                        sc_name.setError("username already taken");
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

    public boolean checkPhoneNumberValidity(String s)
    {
        if(s.length()!=10 && s.length() != 13)
        {
            return false;
        }
        if(s.length()==13)
        {
            String ss = s.substring(0,3);
            if(!(ss.equals("+91")))
                return false;
            input_phoneNo = s.substring(3);
        }
        else{
            for(int i = 0;i < s.length(); i++)
            {
                if(!(Character.isDigit(s.charAt(i))))
                {
                    return false;
                }
            }
        }
        return true;
    }
}
