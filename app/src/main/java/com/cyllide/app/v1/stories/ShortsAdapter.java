package com.cyllide.app.v1.stories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.R;

import java.util.ArrayList;

public class ShortsAdapter extends RecyclerView.Adapter<ShortsViewHolder> {

    ArrayList<ShortsModal> data ;
    @NonNull
    @Override
    public ShortsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.shorts_rv_view,parent,false);
        ShortsViewHolder holder= new ShortsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShortsViewHolder holder, int position) {
        ShortsModal item =data.get(position);
        holder.populate(item);

    }

    public ShortsAdapter(ArrayList<ShortsModal> data) {
        this.data = data;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

