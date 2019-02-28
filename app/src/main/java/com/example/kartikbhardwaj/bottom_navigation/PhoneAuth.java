package com.example.kartikbhardwaj.bottom_navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
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

                Log.d("OTPAUTH",input_phoneNo);
//                readSMS();
                boolean isPhoneValid = checkvalid(input_phoneNo);
                if(isPhoneValid){
                    final Map<String, String> mHeaders = new ArrayMap<String, String>();
                    mHeaders.put("phone", input_phoneNo);
                    mHeaders.put("username", input_scName);


                    try {
                        RequestQueue requestQueue;
                        requestQueue = Volley.newRequestQueue(getBaseContext());
                        String URL = "http://api.cyllide.com/api/client/auth/otp/send";




                        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                Log.e("RealityCheck",response);
                                Log.e("RealityCheck","Inside onResponse");

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



                            @Override
                            protected Response<String> parseNetworkResponse(NetworkResponse nr) {
                                int n = nr.statusCode;
                                Log.d("Res Code",""+n);
                                return super.parseNetworkResponse(nr);
                            }




                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username", input_phoneNo.trim());
                                params.put("phone", input_scName.trim());
                                return params;
                            }

                        };

                        requestQueue.add(stringRequest);
                        Log.e("RealityCheck","Request sent");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //Volley Code Goes Here
                }
                else{
                    Toast.makeText(PhoneAuth.this,"InvalidPhoneNumber",Toast.LENGTH_LONG).show();
                }
            }
        });

        SmsRetrieverClient client = SmsRetriever.getClient(this /* context */);

// Starts SmsRetriever, which waits for ONE matching SMS message until timeout
// (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
// action SmsRetriever#SMS_RETRIEVED_ACTION.
        Task<Void> task = client.startSmsRetriever();

// Listen for success/failure of the start Task. If in a background thread, this
// can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Successfully started retriever, expect broadcast intent
                // ...
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to start retriever, inspect Exception for more details
                // ...
            }
        });
    }


    protected void readSMS() {
        StringBuilder smsBuilder = new StringBuilder();
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_ALL = "content://sms/";
        try {
            Uri uri = Uri.parse(SMS_URI_INBOX);
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            Cursor cur = getContentResolver().query(uri, projection, "address='+123456789'", null, "date desc");
            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");
                do {
                    String strAddress = cur.getString(index_Address);
                    int intPerson = cur.getInt(index_Person);
                    String strbody = cur.getString(index_Body);
                    Log.e("Messages", strbody);
                    long longDate = cur.getLong(index_Date);
                    int int_Type = cur.getInt(index_Type);

                    smsBuilder.append("[ ");
                    smsBuilder.append(strAddress + ", ");
                    smsBuilder.append(intPerson + ", ");
                    smsBuilder.append(strbody + ", ");
                    smsBuilder.append(longDate + ", ");
                    smsBuilder.append(int_Type);
                    smsBuilder.append(" ]\n\n");
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    Log.e("Messages", "Closed");
                    cur = null;
                }
            } else {
                Log.e("Messages", "Not Found");
                smsBuilder.append("no result!");
            } // end if
        } catch (Exception e) {
            Log.e("Messages", "ERRROORRR", e);

        }
    }

    public boolean checkvalid(String s)
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
