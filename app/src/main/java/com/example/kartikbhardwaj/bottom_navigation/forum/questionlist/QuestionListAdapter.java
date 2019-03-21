package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListViewHolder> {

    List<QuestionListModel> questionListModels;

    public QuestionListAdapter(List<QuestionListModel> questionListModels) {
        this.questionListModels = questionListModels;
    }

    @NonNull
    @Override
    public QuestionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.avail_question_view,parent,false);
        QuestionListViewHolder holder = new QuestionListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListViewHolder holder, int position) {
        QuestionListModel stocksModel = questionListModels.get(position);
        holder.populate(stocksModel);
    }

    @Override
    public int getItemCount() {
        return questionListModels.size();
    }
}
