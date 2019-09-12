package com.cyllide.app.beta.forum.questionlist.questionPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyllide.app.beta.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionTagAdapter extends RecyclerView.Adapter<QuestionTagViewHolder> {
    List<QuestionTagModel> questionTagModels = new ArrayList<QuestionTagModel>();

    public QuestionTagAdapter(ArrayList<QuestionTagModel> arr){
        questionTagModels = arr;
    }

    @NonNull
    @Override
    public QuestionTagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.question_tag,parent,false);
        QuestionTagViewHolder holder = new QuestionTagViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionTagViewHolder holder, int position) {
        QuestionTagModel questionTagModel = questionTagModels.get(position);
        holder.populate(questionTagModel);

    }

    @Override
    public int getItemCount() {
        return questionTagModels.size();
    }
}
