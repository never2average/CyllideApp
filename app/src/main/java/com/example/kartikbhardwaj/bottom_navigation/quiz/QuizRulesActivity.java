package com.example.kartikbhardwaj.bottom_navigation.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.AdvancedEncryptionStandard;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class QuizRulesActivity extends AppCompatActivity {

    Button startQuizButton;
    Dialog revivePopup;
    Calendar startTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_rules);


        startQuizButton=findViewById(R.id.startQuizButton);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION", 0);
        String token = sharedPreferences.getString("token", "Not found!");
        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("token", token);
        try {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(this);
            String URL = "http://api.cyllide.com/api/client/quiz/get/latest";

            final StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.e("RealityCheck",response);
                    Log.e("RealityCheck","Inside onResponse");

//My method

                    try {
                        byte[] msg = hex2byte(response.getBytes());
                        String secretKeyString="##gdvhcbxkwjlei23**ukefdvhbxjscm";
                        byte[] result;
                        result = decrypt(secretKeyString, msg);
                        Log.e("RealityCheck",result.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //======================================================
//                    byte[] encryptionKey = "##gdvhcbxkwjlei23**ukefdvhbxjscm".getBytes(StandardCharsets.UTF_8);
//                    Log.e("RealityCheck", String.valueOf(encryptionKey.length));
//                    AdvancedEncryptionStandard advancedEncryptionStandard = new AdvancedEncryptionStandard(encryptionKey);
//                    try {
//                        byte[] decryptedText = advancedEncryptionStandard.decrypt(response.getBytes(StandardCharsets.UTF_8));
//                        Log.e("RealityCheck",new String(decryptedText));
//                    } catch (Exception e) {
//                        Log.e("RealityCheck","exception", e);
//
//                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            })
            {
                @Override
                public Map<String, String> getHeaders() {
                    return mHeaders;
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse nr) {
                    int n = nr.statusCode;
                    Log.d("Res Code",""+n);
                    return super.parseNetworkResponse(nr);
                }

            };

            requestQueue.add(stringRequest);
            Log.e("RealityCheck","Request sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CountDownTimer(startTime.getTimeInMillis()- Calendar.getInstance().getTimeInMillis(),1000){
            @Override
            public void onTick(long millisUntilFinished) {
                //converting millis to sec,min,hour
                String hour=String.valueOf(millisUntilFinished/(1000*3600));
                String minute=String.valueOf((millisUntilFinished/(1000*60))%60);
                String second=String.valueOf(((millisUntilFinished/1000)%60)%60);
                SimpleDateFormat df =new SimpleDateFormat("MM:SS");
                // formatted the time to string
                if(Integer.parseInt(hour)>24){
                    df = new SimpleDateFormat("DD:HH");
                }
                else if(Integer.parseInt(hour)<24 && Integer.parseInt(hour)>=1){
                    df = new SimpleDateFormat("HH:MM");
                }

                String time=df.format(millisUntilFinished);
                //timer.setText(hour+" :"+minute+" : "+second);
                startQuizButton.setText(time);
            }

            @Override
            public void onFinish() {
                startQuizButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        revivePopup= new Dialog(QuizRulesActivity.this
                        );
                        revivePopup.setContentView(R.layout.quiz_revive_popup);
                        revivePopup.show();





                    }

//
// }
                });



            }
        }.start();
    }


    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("hello");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;

    }
    public static byte[] decrypt(String secretKeyString, byte[] encryptedMsg)

            throws Exception {
        Key key = generateKey(secretKeyString);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decValue = c.doFinal(encryptedMsg);
        return decValue;

    }

    private static Key generateKey(String secretKeyString) throws Exception {

        Key key = new SecretKeySpec(secretKeyString.getBytes(), "AES");

        return key;

    }

}


