package com.example.kartikbhardwaj.bottom_navigation.stories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewsPageActivity extends AppCompatActivity {
    private String url, mongoID;
    private WebView mWebview;
    long startTime=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_webview_screen);
        mWebview = findViewById(R.id.fullpage_webview);
        url=getIntent().getStringExtra("newsurl");
        mongoID = getIntent().getStringExtra("mongoid");
        mWebview.loadUrl(url);
//        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION", 0);
//        final String token = sharedPreferences.getString("token", "Not found!");
//        Log.e("RealityCheck",token);
//        final Map<String, String> mHeaders = new HashMap<String, String>();
//        mHeaders.put("token",token);
//        mHeaders.put("url",url);
//
//
//        try {
//            RequestQueue requestQueue;
//            requestQueue = Volley.newRequestQueue(getBaseContext());
//            String URL = "http://api.cyllide.com/api/news/get";
//
//
//
//            final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//
//                @Override
//                public void onResponse(String response) {
//
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        newsContentTv.setText(jsonObject.getString("message"));
//
//                        Log.e("RealityCheck",response);
//                        Log.e("RealityCheck","Inside onResponse");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("VOLLEY", error.toString());
//                }
//            })
//
//
//            {
//                @Override
//                public Map<String, String> getHeaders () {
//                    return mHeaders;
//                }
//
//
//
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse nr) {
//                    int n = nr.statusCode;
//                    Log.d("Res Code",""+n);
//                    return super.parseNetworkResponse(nr);
//                }
//
//            };
//
//            requestQueue.add(stringRequest);
//            Log.e("RealityCheck","Request sent");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //Log start time
        startTime = System.currentTimeMillis();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //Get time spent in foreground
        long readTime = System.currentTimeMillis() - startTime;
        //Send request to server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        //TODO: Remove hardcoded token
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8";
        mHeaders.put("token", token);
        mHeaders.put("timeRead",String.valueOf(readTime));
        mHeaders.put("contentID", mongoID);
        final String url = "http://api.cyllide.com/api/client/stories/update";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("NewsPageActivity", "Got response: "+ response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("NewsPageActivity", "Time logging error");
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }
        };
        queue.add(request);
    }

}

        //Volley Code Goes Here
