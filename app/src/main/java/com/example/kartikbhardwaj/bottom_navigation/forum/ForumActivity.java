package com.example.kartikbhardwaj.bottom_navigation.forum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.example.kartikbhardwaj.bottom_navigation.forum.askquestion.AskQuestion;
import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListAdapter;
import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ForumActivity extends AppCompatActivity {

    QuestionListAdapter questionListAdapter;
    RecyclerView forumRV;
    FloatingActionButton askQuestion;
    private RequestQueue questionRequestQueue;
    private Map<String, String> requestHeaders = new ArrayMap<String, String>();
    JSONObject questionObject;
    List<QuestionListModel> questionList;
    FloatingSearchView searchQuestions;

    private void displayQuestions(JSONArray responseData){
        questionList = new ArrayList<>();
        for (int i = 0; i < responseData.length(); i++) {
            try {
                questionObject=responseData.getJSONObject(i);
                questionList.add(new QuestionListModel(questionObject.getString("queryBody"),questionObject.getJSONObject("_id"),questionObject.getInt("queryNumViews"),questionObject.getJSONObject("queryLastUpdateTime").getLong("$date"), questionObject.getJSONArray("queryTags")));
            } catch (JSONException e) {
                Log.d("JSON Error",e.toString());
            }


        }
        questionListAdapter = new QuestionListAdapter(questionList);
        forumRV.setAdapter(questionListAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        askQuestion = findViewById(R.id.askquestion);
        forumRV=findViewById(R.id.topquesrecycler);
        forumRV.setLayoutManager(new LinearLayoutManager(this));
        getQuestions();

        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questioningIntent = new Intent(ForumActivity.this, AskQuestion.class);
                startActivity(questioningIntent);
            }
        });
        searchQuestions = 


    }
    private void getQuestions(){
        questionRequestQueue = Volley.newRequestQueue(this);
        requestHeaders.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        String requestEndpoint = "http://api.cyllide.com/api/client/query/display";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestEndpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray responseData = new JSONObject(response).getJSONArray("message");
                    displayQuestions(responseData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Question Error",error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return requestHeaders;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        questionRequestQueue.add(stringRequest);
    }
}
