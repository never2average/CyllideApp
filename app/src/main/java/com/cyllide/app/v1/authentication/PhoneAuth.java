package com.cyllide.app.v1.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.Intent;
import android.os.Bundle;
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

                boolean isPhoneValid = checkValid(input_phoneNo);
                if(isPhoneValid){
                    final Map<String, String> mHeaders = new ArrayMap<String, String>();
                    mHeaders.put("phone", input_phoneNo);
                    mHeaders.put("username", input_scName);
                    Intent intent = new Intent(getBaseContext(),OTPVerification.class);
                    startActivity(intent);
                    try {
                        RequestQueue requestQueue;
                        requestQueue = Volley.newRequestQueue(getBaseContext());
                        String URL = "http://api.cyllide.com/api/client/auth/otp/send";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                // TODO Verify the response first.
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                }
                                catch (JSONException e) {
                                    Log.e("RealityCheck","Error",e);
                                }
                                Intent intent = new Intent(getBaseContext(),OTPVerification.class);
                                intent.putExtra("phone",input_phoneNo);
                                startActivity(intent);

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

    public boolean checkValid(String s)
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
