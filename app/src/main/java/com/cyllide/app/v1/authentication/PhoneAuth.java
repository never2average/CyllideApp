package com.cyllide.app.v1.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PhoneAuth extends AppCompatActivity {

    TextInputEditText phoneNumberEditText;
    MaterialButton registerButton;
    Map<String, String> signUpMap = new ArrayMap<>();
    RequestQueue signUpQueue;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        phoneNumberEditText = findViewById(R.id.input_phone_auth);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.otp_loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(false);


        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPhoneNoValidity(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerButton = findViewById(R.id.signup_btn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPhoneNoValidity(phoneNumberEditText.getText().toString())) {
                    dialog.show();
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
                    dialog.dismiss();
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
//                            Toast.makeText(getBaseContext(), "Message Sending ",Toast.LENGTH_LONG).show();
                            Snackbar.make(findViewById(R.id.root_layout),"Something went wrong!",Snackbar.LENGTH_SHORT).show();


                        }
                    }
                } catch (JSONException e) {
                    Log.d("error", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
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

    boolean checkPhoneNoValidity(final String phone) {
        int l = phone.length();
        for (int i = 0; i < l; i++) {
            char c = phone.charAt(i);
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
            registerButton.setEnabled(true);
            return true;
        }

    }


}
