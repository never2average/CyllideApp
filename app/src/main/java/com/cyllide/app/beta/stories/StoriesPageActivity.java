package com.cyllide.app.beta.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Map;

public class StoriesPageActivity extends AppCompatActivity {
    private String url;
    private WebView mWebview;
    FloatingActionButton closeArticle;
    Long startTime,endTime;
    RequestQueue timeSpentRequestQueue;
    String mongoID;
    Map<String,String> timeSpentArrayMap = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_screen);
        closeArticle = findViewById(R.id.close_article);
        startTime = System.currentTimeMillis();
        Log.d("NewsPageActivity","oncreate");
        mWebview = findViewById(R.id.fullpage_webview);
        url=getIntent().getStringExtra("url");
        mongoID = getIntent().getStringExtra("mongoID");


        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebview.loadUrl(url);
        closeArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = System.currentTimeMillis();
                AppConstants.timeSpentOnStories += endTime-startTime;
                endTime=0l;
                startTime=0l;
                Log.d("StoriesPageActivity",AppConstants.timeSpentOnStories.toString());
                timeSpentRequestQueue = Volley.newRequestQueue(StoriesPageActivity.this);
                timeSpentArrayMap.put("token", AppConstants.token);
                timeSpentArrayMap.put("timeRead",Long.toString(AppConstants.timeSpentOnStories/1000));
                timeSpentArrayMap.put("contentID",mongoID);
                String requestEndpoint = getResources().getString(R.string.apiBaseURL)+"stories/update";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, requestEndpoint, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("NewsPageActivity",response);
//                        Intent intent = new Intent(StoriesPageActivity.this, ArticlesMainActivity.class);
//                        startActivity(intent);
//                        finish();
                        onBackPressed();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.d("NewsPageActivity?", error.toString());
//                        Intent intent = new Intent(StoriesPageActivity.this, ArticlesMainActivity.class);
//                        startActivity(intent);
//                        finish();
                        onBackPressed();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        return timeSpentArrayMap;
                    }


                };
                timeSpentRequestQueue.add(stringRequest);
            }
        });
    }

    @Override
    protected void onPause(){
        endTime = System.currentTimeMillis();
        AppConstants.timeSpentOnStories += endTime-startTime;
        super.onPause();

    }
    @Override
    protected void onResume(){
        startTime = System.currentTimeMillis();
        super.onResume();
    }



}