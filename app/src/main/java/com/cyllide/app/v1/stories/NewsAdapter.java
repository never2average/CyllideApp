package com.cyllide.app.v1.stories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.R;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<NewsModel> partList;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public NewsAdapter(List<NewsModel> partList) {
        this.partList = partList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // view_g => name of the layout file
        if(viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.view_news, parent, false);
            NewsViewHolder holder = new NewsViewHolder(view);
            return holder;
        }

        else{
            View view = inflater.inflate(R.layout.news_loading_card,parent,false);
            return new LoadingViewHolder(view);
        }

    }//link xml to recycler view

    @Override//means whatever we are extending is changed to put our own stuff
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {

            NewsModel parts = partList.get(position);
            ((NewsViewHolder) holder).populate(parts);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }

    }

    @Override
    public int getItemCount() {
        return partList == null ? 0 : partList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return partList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }



}

 class LoadingViewHolder extends RecyclerView.ViewHolder {

    LinearLayout loading;

    public LoadingViewHolder(@NonNull View itemView) {
        super(itemView);
        loading = itemView.findViewById(R.id.activity_stories_news_loading_card_layout);
    }
}





