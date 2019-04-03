package com.cyllide.app.v1.forum.questionlist.questionPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyllide.app.v1.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerViewHolder>
{
	List<QuestionAnswerModel> answers;

	public QuestionAnswerAdapter(List<QuestionAnswerModel> answers) {this.answers = answers;}

	@NonNull
	@Override
	public QuestionAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewGroup)
	{
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.view_answers, parent, false);
		QuestionAnswerViewHolder holder = new QuestionAnswerViewHolder(view);
		return holder;
	}

	@Override//means whatever we are extending is changed to put our own stuff
	public void onBindViewHolder(@NonNull QuestionAnswerViewHolder holder, int position) {
		QuestionAnswerModel answer = answers.get(position);
		holder.populate(answer);
	}
	@Override
	public int getItemCount() {
		return answers.size();
	}
}
