package com.cyllide.app.v1.forum.questionlist.questionPage;

import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.ProfileActivity;
import com.cyllide.app.v1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

public class QuestionAnswerViewHolder extends RecyclerView.ViewHolder{

	private TextView answerTV, answeredByTV, answeredOnTV, upVoteCount;
	private RequestQueue upVoteRequestQueue;
	ImageButton upVoteButton, downVoteButton;
	private String answer, answeredBy;
	long answeredOn;
	ImageView answerProfilePic;
	Map<String, String> requestHeader = new ArrayMap<String, String>();
	View view;


	public QuestionAnswerViewHolder(View itemView)
	{
		super(itemView);
		answeredByTV = itemView.findViewById(R.id.answered_by_tv);
		answerTV = itemView.findViewById(R.id.answer_tv);
		answeredOnTV = itemView.findViewById(R.id.answered_on_tv);
		upVoteCount = itemView.findViewById(R.id.answer_up_upvote_count);
		answerProfilePic = itemView.findViewById(R.id.answer_profile_pic);

		upVoteRequestQueue = Volley.newRequestQueue(itemView.getContext());
		upVoteButton = itemView.findViewById(R.id.answer_up_vote_button);
		downVoteButton = itemView.findViewById(R.id.answer_down_vote_button);
		view = itemView;
	}
	public void populate(final QuestionAnswerModel answers)
	{
		answer = answers.getAnswerDetail();
		answeredOn = answers.getDateStamp();
		answeredBy = answers.getAnsweredBy();
		SimpleDateFormat format = new SimpleDateFormat("EEE,dd MMM yyyy");
		Date date = new Date(answers.getDateStamp());
		answeredOnTV.setText(format.format(date));
		answeredByTV.setText(answeredBy);
		answerTV.setText(answer);
		upVoteCount.setText(Integer.toString(answers.getAnswerUpVotes()));

		Glide.with(itemView.getContext()).load(answers.getProfileURL()).into(answerProfilePic);

		upVoteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getUpVoteVolley(answers.getAnswerID(),1);
				Log.e("UpVote","Probably sent");

			}
		});


		downVoteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				getUpVoteVolley(answers.getAnswerID(),-1);

			}
		});

		answerProfilePic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent profileIntentView = new Intent(view.getContext(), ProfileActivity.class);
				AppConstants.viewUsername = answers.getAnsweredBy();
				view.getContext().startActivity(profileIntentView);
			}
		});



	}

	void getUpVoteVolley(String id, int vote){


		String requestURL = itemView.getContext().getResources().getString(R.string.apiBaseURL)+"answer/upvote";
		requestHeader.put("aid",id);
		requestHeader.put("votes",Integer.toString(vote));
		requestHeader.put("token", AppConstants.token);
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
