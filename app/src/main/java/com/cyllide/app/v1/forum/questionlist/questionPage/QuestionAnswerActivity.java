package com.cyllide.app.v1.forum.questionlist.questionPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
import com.cyllide.app.v1.forum.CustomComparatorAnswerUpVotes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class QuestionAnswerActivity extends AppCompatActivity {

	RecyclerView ansRecyclerView, questionTagRecyclerView;
    ArrayList<QuestionTagModel> questionTagModels = new ArrayList<>();
    ArrayList<QuestionAnswerModel> questionAnswerModels = new ArrayList<>();
	TextView questionTitle, questionAskedByText, questionLastModifiedText;
	String questionID;
	TextInputEditText answerBody;
	private RequestQueue answerQueue, postQueue;
	private Map<String, String> requestHeaders = new ArrayMap<String, String>();
    private Map<String, String> requestHeadersPost = new ArrayMap<String, String>();
	MaterialButton postAnswer;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_answer);
		ansRecyclerView = findViewById(R.id.ansRV);
		questionTitle = findViewById(R.id.questionTitle);
		answerBody = findViewById(R.id.answer_body);
		questionAskedByText = findViewById(R.id.question_asked_by_text);
		questionLastModifiedText = findViewById(R.id.last_updated_question_text);
		ansRecyclerView.setHasFixedSize(true);
		questionTagRecyclerView = findViewById(R.id.question_tags_reyclerview);
		postAnswer = findViewById(R.id.post_answer);
		postAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAnswerVolley(answerBody.getText().toString());
            }
        });
		RecyclerView.LayoutManager  tagsLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
		RecyclerView.LayoutManager answerLayoutManager = new LinearLayoutManager(this);
		ansRecyclerView.setLayoutManager(answerLayoutManager);
		questionTagRecyclerView.setLayoutManager(tagsLayoutManager);

        try {
            questionID = new JSONObject(getIntent().getStringExtra("questionID")).getString("$oid");
            fillAnswers(questionID);

        } catch (JSONException e) {
            Toast.makeText(getBaseContext(),"Answer could not be sent",Toast.LENGTH_SHORT).show();
            Log.d("InvalidQid",e.toString());
        }
    }

    private void postAnswerVolley(String answerBody) {
	    postQueue = Volley.newRequestQueue(this);
        String requestEndpoint = getResources().getString(R.string.apiBaseURL)+"answer/add";
        requestHeadersPost.put("token", AppConstants.token);
        requestHeadersPost.put("qid",questionID);
        requestHeadersPost.put("answerBody",answerBody);
	    StringRequest postAnswer = new StringRequest(Request.Method.POST, requestEndpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(QuestionAnswerActivity.this,response,Toast.LENGTH_LONG).show();
                fillAnswers(questionID);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QuestionAnswerActivity.this,error.toString(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return requestHeadersPost;
            }
        };
	    postQueue.add(postAnswer);
    }


    private void fillAnswers(String questionID) {
	    answerQueue = Volley.newRequestQueue(this);
	    requestHeaders.put("token",AppConstants.token);
	    requestHeaders.put("qid",questionID);
        String requestEndpoint = getResources().getString(R.string.apiBaseURL)+"query/display/one";
        StringRequest answers = new StringRequest(Request.Method.GET, requestEndpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("answerResponse",response);
                    JSONObject responseData = new JSONObject(response).getJSONObject("message");
                    questionTitle.setText(responseData.getString("queryBody"));
                    questionAskedByText.setText(responseData.getString("queryUID"));
                    String utcTime = responseData.getJSONObject("queryLastUpdateTime").getString("$date");
                    Date lastUpdated = new Date(Long.parseLong(utcTime));
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    questionLastModifiedText.setText(format.format(lastUpdated));
                    JSONArray tagList = responseData.getJSONArray("queryTags");
                    JSONArray answerList = responseData.getJSONArray("answerList");
                    for(int i=0;i<tagList.length();i++){
                        questionTagModels.add(new QuestionTagModel(tagList.getString(i)));
                    }
                    for(int i=0;i<answerList.length();i++){
                        JSONObject singleAnswer = answerList.getJSONObject(i);
                       questionAnswerModels.add(new QuestionAnswerModel(singleAnswer.getJSONObject("_id").getString("$oid"),singleAnswer.getString("answerBody"),singleAnswer.getInt("answerUpvotes"),singleAnswer.getString("answerUID"),singleAnswer.getJSONObject("answerTime").getLong("$date"),singleAnswer.getString("profilePic")));
                    }
                    Collections.sort(questionAnswerModels,new CustomComparatorAnswerUpVotes());

                    QuestionTagAdapter questionTagAdapter = new QuestionTagAdapter(questionTagModels);
                    QuestionAnswerAdapter questionAnswerAdapter = new QuestionAnswerAdapter(questionAnswerModels);
                    questionTagRecyclerView.setAdapter(questionTagAdapter);
                    ansRecyclerView.setAdapter(questionAnswerAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("answerError", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return requestHeaders;
            }
        };
	    answerQueue.add(answers);
    }
}
