package com.cyllide.app.beta.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.MainActivity;
import com.cyllide.app.beta.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class SignupPage extends AppCompatActivity {

    MaterialButton materialButton;
    TextInputEditText input_user_name, input_referral;
    String phoneNo;
    Map<String, String> signupMap = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        materialButton = findViewById(R.id.btn_update_username);
        input_user_name = findViewById(R.id.input_username);
        input_referral = findViewById(R.id.input_referral);
        phoneNo = getIntent().getStringExtra("phone");

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isUsernameValid()) {
                    changeUsername();
                }
            }
        });

        input_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean isUsernameValid() {
        String name = input_user_name.getText().toString().trim();
        if(name.length()<=5){
            input_user_name.setError("Username must be atleast 5 characters long");
            return false;
        }
        else if(Character.isDigit(name.charAt(0))){
            input_user_name.setError("Username must be start with numbers");

            return false;
        }
        else if(!name.matches("[A-Za-z0-9]+")){
            input_user_name.setError("Username must contain numbers or characters only");
            return false;
        }
        input_user_name.setError("");

        return true;

    }

    void changeUsername() {
        String url = getResources().getString(R.string.apiBaseURL)+"username";
        RequestQueue requestQueue = Volley.newRequestQueue(SignupPage.this);
        signupMap.put("referral", input_referral.getText().toString());
        signupMap.put("phone", phoneNo);
        signupMap.put("username", input_user_name.getText().toString());
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    SharedPreferences.Editor editor = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE).edit();
                    editor.putString("token", jsonObject.getString("token"));
                    editor.putInt("coins", jsonObject.getInt("coins"));
                    editor.putString("referralCode", jsonObject.getString("referralCode"));
                    editor.apply();
                    Intent intent = new Intent(SignupPage.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    Log.d("JSONErrorSignup", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return  signupMap;
            }
        };
        requestQueue.add(stringRequest);

    }
}
