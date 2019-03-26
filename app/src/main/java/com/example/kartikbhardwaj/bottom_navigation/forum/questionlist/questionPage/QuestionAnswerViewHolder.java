package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.questionPage;

import android.content.SharedPreferences;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

public class QuestionAnswerViewHolder extends RecyclerView.ViewHolder{

	private TextView answerTV, answeredByTV, answeredOnTV, upVoteCount,downVoteCount;
	private RequestQueue upVoteRequestQueue;
	ImageButton upVoteButton, downVoteButton;
	private String answer, answeredBy;
	long answeredOn;
	Map<String, String> requestHeader = new ArrayMap<String, String>();


	public QuestionAnswerViewHolder(View itemView)
	{
		super(itemView);
		answeredByTV = itemView.findViewById(R.id.answered_by_tv);
		answerTV = itemView.findViewById(R.id.answer_tv);
		answeredOnTV = itemView.findViewById(R.id.answered_on_tv);
		upVoteCount = itemView.findViewById(R.id.answer_up_upvote_count);
		downVoteCount = itemView.findViewById(R.id.answer_down_upvote_count);

		upVoteRequestQueue = Volley.newRequestQueue(itemView.getContext());
		upVoteButton = itemView.findViewById(R.id.answer_up_vote_button);
		downVoteButton = itemView.findViewById(R.id.answer_down_vote_button);
	}
	public void populate(final QuestionAnswerModel answers)
	{
		answer = answers.getAnswerDetail();
		answeredOn = answers.getDateStamp();
		answeredBy = answers.getAnsweredBy();
		answeredOnTV.setText(new Date(answers.getDateStamp()).toString());
		answeredByTV.setText(answeredBy);
		answerTV.setText(answer);
		upVoteCount.setText(Integer.toString(answers.getAnswerUpVotes()));
        downVoteCount.setText(Integer.toString(answers.getAnswerUpVotes()));


		upVoteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				getUpVoteVolley(answers.getAnswerID(),1);
				Log.e("UpVote","Proabaly sent");

			}
		});


		downVoteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				getUpVoteVolley(answers.getAnswerID(),-1);

			}
		});



	}

	void getUpVoteVolley(String id, int vote){


		String requestURL = "http://api.cyllide.com/api/client/answer/upvote";
		requestHeader.put("aid",id);
		requestHeader.put("votes",Integer.toString(vote));
		requestHeader.put("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
		//TODO REMOVE HARDCODED KEY
		StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					upVoteCount.setText(new JSONObject(response).getString("numUpvotes"));
                    Log.e("UpVote",response);
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
				return requestHeader;
			}
			@Override
			protected Response<String> parseNetworkResponse(NetworkResponse response) {
				int mStatusCode = response.statusCode;
				Log.d("whats failing", String.valueOf(mStatusCode));
				return super.parseNetworkResponse(response);
			}
		};
		upVoteRequestQueue.add(stringRequest);

	}


}
