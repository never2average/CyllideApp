package com.cyllide.app.beta.stories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyllide.app.beta.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesViewHolder> {

    List<StoriesModel> partList;

    public StoriesAdapter(List<StoriesModel> partList) {
        this.partList = partList;
    }

    @NonNull
    @Override
    public StoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // view_g => name of the layout file
        View view = inflater.inflate(R.layout.view_stories, parent, false);
        StoriesViewHolder holder = new StoriesViewHolder(view);
        return holder;
    }//link xml to recycler view

    @Override//means whatever we are extending is changed to put our own stuff
    public void onBindViewHolder(@NonNull StoriesViewHolder holder, int position) {
        StoriesModel parts = partList.get(position);
        holder.populate(parts);
    }

    @Override
    public int getItemCount() {
        return partList.size();
    }
}
