package com.cyllide.app.v1.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class NewsPageActivity extends AppCompatActivity {
    private String  articleURL;
    private TextView newsTitle, newsContent, newsSummary, newsDatePublished;
    private SimpleDraweeView coverImage;
    private ImageView backBtn;
    private RequestQueue requestQueue;
    private Map<String, String> articleDataMap = new ArrayMap<>();
    private String requestURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_news_page);

        backBtn = findViewById(R.id.newsitembackbtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewsPageActivity.this, ArticlesMainActivity.class));
                finish();
            }
        });

        newsTitle = findViewById(R.id.newsitemtitle);
        newsContent = findViewById(R.id.newsitemcontent);
        newsDatePublished = findViewById(R.id.newsitemdate);
        newsSummary = findViewById(R.id.newsitemsummary);
        coverImage = findViewById(R.id.newsitemcover);


        newsTitle.setText(getIntent().getStringExtra("newsTitle"));
        newsDatePublished.setText("ET Now | Updated: "+getIntent().getStringExtra("newsDatePub"));
        newsSummary.setText(getIntent().getStringExtra("newsSummary"));
        coverImage.setImageURI(getIntent().getStringExtra("newsCoverImage"));
        articleURL = getIntent().getStringExtra("newsLink");
        fetchArticleData(articleURL);

    }

    void fetchArticleData(String url){
        requestQueue = Volley.newRequestQueue(this);
        requestURL = getResources().getString(R.string.newsApiBaseURL)+"/article";
        articleDataMap.put("link", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    newsContent.setText(new JSONObject(response).getString("data"));
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
          public Map<String, String> getHeaders(){return articleDataMap;}
        };
        requestQueue.add(stringRequest);
    }
}