package com.cyllide.app.v1.forum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.google.android.material.card.MaterialCardView;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;
import com.nex3z.togglebuttongroup.button.ToggleButton;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
    ArrayList<String> selectedTags;
    JSONArray responseData;
    LinearLayout loadingLayout;
    MaterialCardView askAQuestionCV;
    TextView askAQuestionTV;
    boolean flag;

    ArrayList<String> forumTags= new ArrayList<>();

    private void displayQuestions(JSONArray responseData) {
        questionList = new ArrayList<>();
        for (int i = 0; i < responseData.length(); i++) {
            try {

                questionObject = responseData.getJSONObject(i);
                JSONArray queryTags = questionObject.getJSONArray("queryTags");
                int f = 0;
                if(selectedTags.size() == 0){
                    f=1;
                }
                else {
                    for (int j = 0; j < queryTags.length(); j++) {
                        if (selectedTags.contains(queryTags.getString(j))){
                            f=1;
                            break;
                        }
                    }
                }
                if(f==1) {
                    questionList.add(new QuestionListModel(questionObject.getString("queryBody"), questionObject.getJSONObject("_id"), questionObject.getInt("queryNumViews"), questionObject.getJSONObject("queryLastUpdateTime").getLong("$date"), questionObject.getJSONArray("queryTags")));
                }
            } catch (JSONException e) {
            }
        }
        questionListAdapter = new QuestionListAdapter(questionList);
        filterList = questionList;
        forumRV.setAdapter(questionListAdapter);
        loadingLayout.setVisibility(View.INVISIBLE);
        forumRV.setVisibility(View.VISIBLE);
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

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        loadingLayout = findViewById(R.id.activity_forum_loading_layout);
        backButton = findViewById(R.id.activity_forum_back_button);
        askQuestion = findViewById(R.id.ask_question);
        forumRV = findViewById(R.id.topquesrecycler);
        forumRV.setLayoutManager(new LinearLayoutManager(this));

        selectedTags = new ArrayList<String>();
        askAQuestionTV = findViewById(R.id.answer_question_tv);
        askAQuestionCV = findViewById(R.id.answer_question);
        askAQuestionCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){
                    askAQuestionTV.setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
                    askAQuestionCV.setCardBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.white));
                    flag = false;
                    displayQuestions(responseData);
                }
                else{
                    askAQuestionTV.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                    askAQuestionCV.setCardBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
                    flag = true;
                    sortByNewest();
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchQuestions.clearQuery();
                if(getCurrentFocus()!=null)
                {
                    InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                }
                onBackPressed();

            }
        });
        tags=findViewById(R.id.tags);
        for(int i = 0; i<forumTags.size(); i++) {
            int pixels = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

            LabelToggle lt = new LabelToggle(ForumActivity.this);
            lt.setId(i);
            final int id_ = lt.getId();
            lt.setText(forumTags.get(i));
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonLayoutParams.setMargins(pixels,pixels,pixels,pixels);
            lt.setPadding(pixels,pixels,pixels,pixels);
            lt.setLayoutParams(buttonLayoutParams);
            lt.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,6, getResources().getDisplayMetrics()));
            tags.addView(lt);



        }





        tags.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {


                            if(!selectedTags.contains(forumTags.get(checkedId))){
                                selectedTags.add(forumTags.get(checkedId));
                            }
                            else{
                                selectedTags.remove(forumTags.get(checkedId));
                            }

                if(responseData==null){
                    getQuestions();
                }
                displayQuestions(responseData);

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
        searchQuestions.setDimBackground(false);
        searchQuestions.setClearBtnColor(getColor(R.color.cyllide_grey));

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

        searchQuestions.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });
        forumTagsVolley();



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
                    responseData = new JSONObject(response).getJSONArray("message");
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

    void forumTagsVolley(){
        final RequestQueue forumTagsQueue = Volley.newRequestQueue(ForumActivity.this);
        String url = getString(R.string.apiBaseURL) + "forum/tags";
        StringRequest forumRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        forumTags.add(jsonArray.getString(i));
                    }
                    Log.d("ForumActivity", String.valueOf(forumTags.size()));
                    for(int i = 0; i<forumTags.size(); i++) {
                        int pixels = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

                        LabelToggle lt = new LabelToggle(ForumActivity.this);
                        lt.setId(i);
                        final int id_ = lt.getId();
                        lt.setText(forumTags.get(i));
                        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        buttonLayoutParams.setMargins(pixels,pixels,pixels,pixels);
                        lt.setPadding(pixels,pixels,pixels,pixels);
                        lt.setLayoutParams(buttonLayoutParams);
                        lt.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,6, getResources().getDisplayMetrics()));
                        tags.addView(lt);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        forumTagsQueue.add(forumRequest);
    }
}
