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

    private TextView questionTV, commentTV1, commentTV2, commentTV3, commentTV4;
    private MaterialCardView questionCard;
    private String question;
    ArrayList<String> tagList= new ArrayList<>();

    public QuestionListViewHolder(@NonNull View itemView) {
        super(itemView);
        questionCard = itemView.findViewById(R.id.question_card);
        questionTV = itemView.findViewById(R.id.questionText);
        commentTV1 = itemView.findViewById(R.id.tag1);
        commentTV2 = itemView.findViewById(R.id.tag2);
        commentTV3 = itemView.findViewById(R.id.tag3);
        commentTV4 = itemView.findViewById(R.id.tag4);
    }
    public void populate(QuestionListModel stocksModel) {
		question=stocksModel.getQuestionText();
        questionTV.setText(stocksModel.getQuestionText());
        tagList = stocksModel.getQuestionTags();
        if(tagList.size() == 4){
            commentTV1.setText(tagList.get(0));
            commentTV2.setText(tagList.get(1));
            commentTV3.setText(tagList.get(2));
            commentTV4.setText(tagList.get(3));
        }
        else{
            if(tagList.size() == 3){
                commentTV1.setText(tagList.get(0));
                commentTV2.setText(tagList.get(1));
                commentTV2.setText(tagList.get(2));
                commentTV4.setAlpha(0.0f);
            }
            else{
                if(tagList.size() == 2){
                    commentTV1.setText(tagList.get(0));
                    commentTV2.setText(tagList.get(1));
                    commentTV3.setAlpha(0.0f);
                    commentTV4.setAlpha(0.0f);
                }
                else{
                    if(tagList.size() == 1){
                        commentTV1.setText(tagList.get(0));
                        commentTV2.setAlpha(0.0f);
                        commentTV3.setAlpha(0.0f);
                        commentTV4.setAlpha(0.0f);
                    }
                    else{
                        commentTV1.setAlpha(0.0f);
                        commentTV2.setAlpha(0.0f);
                        commentTV3.setAlpha(0.0f);
                        commentTV4.setAlpha(0.0f);
                    }
                }
            }
        }
        questionCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(itemView.getContext(), QuestionAnswerActivity.class);
				intent.putExtra("question title",question);
				itemView.getContext().startActivity(intent);
			}
		});

    }

}
