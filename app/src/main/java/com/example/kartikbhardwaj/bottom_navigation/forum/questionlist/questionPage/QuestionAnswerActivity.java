package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.questionPage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionAnswerActivity extends AppCompatActivity {

	RecyclerView ansRecyclerView;
	String question;
	TextView questionDetail, questionTitle;
	String questionID;
	private RequestQueue answerQueue;
	private Map<String, String> requestHeaders = new ArrayMap<String, String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_answer);
		ansRecyclerView = findViewById(R.id.ansRV);
		questionDetail = findViewById(R.id.questionDetailText);
		questionTitle = findViewById(R.id.questionTitle);
		question = getIntent().getStringExtra("questionTitle");
		questionTitle.setText(question);
		ansRecyclerView.setHasFixedSize(true);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this){
			@Override
            public boolean canScrollVertically(){
			    return false;
            }
		};
		ansRecyclerView.setLayoutManager(layoutManager);

        try {
            questionID = new JSONObject(getIntent().getStringExtra("questionID")).getString("$oid");
            fillAnswers(questionID);

        } catch (JSONException e) {
            Log.d("InvalidQid",e.toString());
        }
    }


    private void fillAnswers(String questionID) {
	    answerQueue = Volley.newRequestQueue(this);
	    requestHeaders.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
	    requestHeaders.put("qid",questionID);
        String requestEndpoint = "http://api.cyllide.com/api/client/query/display/one";
        StringRequest answers = new StringRequest(Request.Method.GET, requestEndpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("answerResponse",response);
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
