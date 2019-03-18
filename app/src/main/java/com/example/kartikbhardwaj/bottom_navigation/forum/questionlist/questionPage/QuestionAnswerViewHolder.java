package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.questionPage;

import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;

import androidx.recyclerview.widget.RecyclerView;

public class QuestionAnswerViewHolder extends RecyclerView.ViewHolder{

	private TextView answerTV, answeredByTV, answeredOnTV;
	private String answer, answeredBy, answeredOn;

	public QuestionAnswerViewHolder(View itemView)
	{
		super(itemView);
		answeredByTV = itemView.findViewById(R.id.answered_by_tv);
		answerTV = itemView.findViewById(R.id.answer_tv);
		answeredOnTV = itemView.findViewById(R.id.answered_on_tv);
	}
	public void populate(QuestionAnswerModel answers)
	{
		answer = answers.getAnswerDetail();
		answeredOn = answers.getDateStamp();
		answeredBy = answers.getAnsweredBy();
		answeredOnTV.setText(answeredOn);
		answeredByTV.setText(answeredBy);
		answerTV.setText(answer);

	}
}
