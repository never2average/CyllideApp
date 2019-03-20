package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.questionPage.QuestionAnswerActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionListViewHolder extends RecyclerView.ViewHolder {

    private TextView questionTV;
    private MaterialCardView questionCard;
    private String question;
    ArrayList<String> tagList= new ArrayList<>();
    private View questionView;

    public QuestionListViewHolder(@NonNull View itemView) {
        super(itemView);
        questionView = itemView;
        questionCard = itemView.findViewById(R.id.question_card);
        questionTV = itemView.findViewById(R.id.questionText);
    }
    public void populate(final QuestionListModel stocksModel) {
		question=stocksModel.getQuestionText();
        questionTV.setText(stocksModel.getQuestionText());
        questionCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(itemView.getContext(), QuestionAnswerActivity.class);
				intent.putExtra("questionTitle",question);
				intent.putExtra("questionID",stocksModel.getQuestionID().toString());
				itemView.getContext().startActivity(intent);
			}
		});

    }

}
