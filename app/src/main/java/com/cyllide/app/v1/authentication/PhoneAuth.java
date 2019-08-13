package com.cyllide.app.v1.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PhoneAuth extends AppCompatActivity {

    TextInputEditText phoneNumberEditText;
    MaterialButton registerButton;
    Map<String, String> signUpMap = new ArrayMap<>();
    RequestQueue signUpQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        phoneNumberEditText = findViewById(R.id.input_phone_auth);
        registerButton = findViewById(R.id.signup_btn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPhoneNoValidity(phoneNumberEditText.getText().toString())) {
                    signUp();
                }

            }
        });
        registerButton.setEnabled(false);
    }


    void signUp(){
        signUpQueue = Volley.newRequestQueue(getBaseContext());
        signUpMap.put("phone", phoneNumberEditText.getText().toString());
        String url = getString(R.string.apiBaseURL)+"auth/otp/send";
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("message").equals("NewUser")){
                        AppConstants.tempUsername="redirect";
                        Intent intent = new Intent(PhoneAuth.this, OTPVerification.class);
                        intent.putExtra("phone", phoneNumberEditText.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                    else{
                        if(jsonObject.getString("message").equals("MessageSendingSuccessful")){
                            AppConstants.tempUsername="no-redirect";
                            Intent intent = new Intent(PhoneAuth.this, OTPVerification.class);
                            intent.putExtra("phone", phoneNumberEditText.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(getBaseContext(), "Message Sending ",Toast.LENGTH_LONG).show();
                        }
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

    boolean checkPhoneNoValidity(final String username) {
        int l = username.length();
        for (int i = 0; i < l; i++) {
            char c = username.charAt(i);
            if (c >= '0' && c <= '9') {
                continue;
            }
            phoneNumberEditText.setError("Invalid Phone Number");
            registerButton.setEnabled(false);
            return false;
        }
        if(l != 10) {
            phoneNumberEditText.setError("Invalid Phone Number");
            registerButton.setEnabled(false);


            return false;
        }
        else {
            return true;
        }

    }


}
