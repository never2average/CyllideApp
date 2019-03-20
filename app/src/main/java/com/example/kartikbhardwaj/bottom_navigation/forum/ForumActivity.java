package com.example.kartikbhardwaj.bottom_navigation.forum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.kartikbhardwaj.bottom_navigation.MainApplication;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.example.kartikbhardwaj.bottom_navigation.forum.askquestion.AskQuestion;
import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListAdapter;
import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ForumActivity extends AppCompatActivity {

    QuestionListAdapter questionListAdapter;
    RecyclerView forumRV;
    FloatingActionButton askQuestion;
    private RequestQueue questionRequestQueue;
    private Map<String, String> requestHeaders = new ArrayMap<String, String>();
    JSONObject questionObject;
    List<QuestionListModel> questionList, filterList;
    FloatingSearchView searchQuestions;
    LabelToggle toggleButtonNewest,toggleButtonMostViewed;
    boolean sortMode = false;
    private Realm realmInstance;

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
        filterList = questionList;
        forumRV.setAdapter(questionListAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        Log.d("ForumActivity", "Initializing Realm");
        realmInstance = Realm.getDefaultInstance();
        askQuestion = findViewById(R.id.askquestion);
        forumRV=findViewById(R.id.topquesrecycler);
        forumRV.setLayoutManager(new LinearLayoutManager(this));
        toggleButtonNewest = findViewById(R.id.toggle_tab_newest);
        toggleButtonMostViewed = findViewById(R.id.toggle_tab_most_viewed);
        //getQuestions();
        readCachedQuestions();
        toggleButtonNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filterList==null){
                    return;
                }
                Collections.sort(filterList, new CustomComparatorDate());
                questionListAdapter = new QuestionListAdapter(filterList);
                forumRV.setAdapter(questionListAdapter);
                }
        });

        toggleButtonMostViewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filterList==null){
                    return;
                }

                Collections.sort(filterList,new CustomComparatorMostViewed());
                questionListAdapter = new QuestionListAdapter(filterList);
                forumRV.setAdapter(questionListAdapter);
            }
        });

        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questioningIntent = new Intent(ForumActivity.this, AskQuestion.class);
                startActivity(questioningIntent);
            }
        });
        searchQuestions = findViewById(R.id.search_questions);
        searchQuestions.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (questionList!=null && questionList.size() != 0) {
                    if (newQuery.equals("")) {
                        Log.d("newQuery",newQuery);
                        questionListAdapter = new QuestionListAdapter(questionList);
                        forumRV.setAdapter(questionListAdapter);
                    } else {
                        applyFilter(newQuery, questionList);
                    }
                }
            }
        });


    }

    private void applyFilter(String newQuery, List<QuestionListModel> questionList) {
        filterList = new ArrayList<>();
        for(QuestionListModel questionListModel: questionList){
            if(questionListModel.getQuestionText().toUpperCase().contains(newQuery.toUpperCase())){
                filterList.add(questionListModel);
            }
        }
        if(sortMode){
            toggleButtonNewest.performClick();
        }
        else{
            toggleButtonMostViewed.performClick();
        }
        questionListAdapter = new QuestionListAdapter(filterList);
        forumRV.setAdapter(questionListAdapter);
    }

    private void readCachedQuestions(){
        RealmResults<QuestionListModel> questionListRealm = realmInstance.where(QuestionListModel.class)
                .findAll();
        //update questions in background thread
        MainApplication.setUpQuestionUpdateWorker();
        //set RV
        questionList = questionListRealm;
        questionListAdapter = new QuestionListAdapter(questionList);
        filterList = questionList;
        forumRV.setAdapter(questionListAdapter);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ForumActivity", "Closing realm instance");
        realmInstance.close();
    }
}
