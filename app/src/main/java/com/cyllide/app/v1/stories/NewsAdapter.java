package com.cyllide.app.v1.stories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {
    List<NewsModel> partList;

    public NewsAdapter(List<NewsModel> partList) {
        this.partList = partList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // view_g => name of the layout file
        View view = inflater.inflate(R.layout.view_news, parent, false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }//link xml to recycler view

    @Override//means whatever we are extending is changed to put our own stuff
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel parts = partList.get(position);
        holder.populate(parts);
    }

    @Override
    public int getItemCount() {
        return partList.size();
    }
}