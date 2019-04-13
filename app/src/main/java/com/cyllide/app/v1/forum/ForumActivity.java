package com.cyllide.app.v1.forum;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.cyllide.app.v1.forum.askquestion.AskQuestion;
import com.cyllide.app.v1.forum.questionlist.QuestionListAdapter;
import com.cyllide.app.v1.forum.questionlist.QuestionListModel;
import com.google.android.material.button.MaterialButton;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ForumActivity extends AppCompatActivity {

    QuestionListAdapter questionListAdapter;
    RecyclerView forumRV;
    MaterialButton askQuestion;
    private RequestQueue questionRequestQueue;
    private Map<String, String> requestHeaders = new ArrayMap<String, String>();
    JSONObject questionObject;
    List<QuestionListModel> questionList, filterList;
    FloatingSearchView searchQuestions;
    MultiSelectToggleGroup tags ;
    ImageView backButton;

    private void displayQuestions(JSONArray responseData) {
        questionList = new ArrayList<>();
        for (int i = 0; i < responseData.length(); i++) {
            try {
                questionObject = responseData.getJSONObject(i);
                questionList.add(new QuestionListModel(questionObject.getString("queryBody"), questionObject.getJSONObject("_id"), questionObject.getInt("queryNumViews"), questionObject.getJSONObject("queryLastUpdateTime").getLong("$date"), questionObject.getJSONArray("queryTags")));
            } catch (JSONException e) {
                Log.d("JSON Error", e.toString());
            }
        }
        questionListAdapter = new QuestionListAdapter(questionList);
        filterList = questionList;
        forumRV.setAdapter(questionListAdapter);
    }

    public void sortByNewest() {
        if (filterList == null) {
            return;
        }
        Collections.sort(filterList, new CustomComparatorDate());
        questionListAdapter = new QuestionListAdapter(filterList);
        forumRV.setAdapter(questionListAdapter);
    }

    public void sortByMostViewed() {

        if (filterList == null) {
            return;
        }

        Collections.sort(filterList, new CustomComparatorMostViewed());
        questionListAdapter = new QuestionListAdapter(filterList);
        forumRV.setAdapter(questionListAdapter);

    }

    ToggleButton finance, capitalMarkets, macroEconimics, business;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        backButton = findViewById(R.id.activity_forum_back_button);
        askQuestion = findViewById(R.id.ask_question);
        forumRV = findViewById(R.id.topquesrecycler);
        forumRV.setLayoutManager(new LinearLayoutManager(this));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForumActivity.this, MainActivity.class));
                finish();
            }
        });
        tags=findViewById(R.id.tags);


        tags.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {

                switch (checkedId){

                    case R.id.tb_finance:
                        if(isChecked)
                        {Toast.makeText(ForumActivity.this, "finance", Toast.LENGTH_SHORT).show();}

                        break;
                    case R.id.tb_capital_markets:

                        if(isChecked)
                        {Toast.makeText(ForumActivity.this, "Cp markets", Toast.LENGTH_SHORT).show();}

                        //capitalMarkets
                        break;
                    case R.id.tb_macro_economics:
                        if(isChecked)
                        {Toast.makeText(ForumActivity.this, "econo", Toast.LENGTH_SHORT).show();}

                        break;
                    case R.id.tb_business:
                        if(isChecked)
                        {Toast.makeText(ForumActivity.this, "business", Toast.LENGTH_SHORT).show();}



                }
            }
        });

        getQuestions();


        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questioningIntent = new Intent(ForumActivity.this, AskQuestion.class);
                startActivity(questioningIntent);
                finish();
            }
        });
        searchQuestions = findViewById(R.id.search_questions);
        searchQuestions.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                if (questionList != null && questionList.size() != 0) {
                    if (newQuery.equals("")) {
                        Log.d("newQuery", newQuery);
                        questionListAdapter = new QuestionListAdapter(questionList);
                        forumRV.setAdapter(questionListAdapter);
                    } else {
                        applyFilter(newQuery, questionList);
                    }
                }
            }
        });

        finance = findViewById(R.id.tb_finance);
        capitalMarkets = findViewById(R.id.tb_capital_markets);
        macroEconimics = findViewById(R.id.tb_macro_economics);
        business = findViewById(R.id.tb_business);



    }

    private void applyFilter(String newQuery, List<QuestionListModel> questionList) {
        filterList = new ArrayList<>();
        for (QuestionListModel questionListModel : questionList) {
            if (questionListModel.getQuestionText().toUpperCase().contains(newQuery.toUpperCase())) {
                filterList.add(questionListModel);
            }
        }

        questionListAdapter = new QuestionListAdapter(filterList);
        forumRV.setAdapter(questionListAdapter);
    }


    private void getQuestions() {
        questionRequestQueue = Volley.newRequestQueue(this);
        requestHeaders.put("token", AppConstants.token);
        String requestEndpoint = getResources().getString(R.string.apiBaseURL)+"query/display";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestEndpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("resp",response);
                    JSONArray responseData = new JSONObject(response).getJSONArray("message");
                    displayQuestions(responseData);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Question Error", error.toString());
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
    }
}
