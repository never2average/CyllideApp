package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyViewHolder> {

    List<WeeklyModel> partList3;

    public WeeklyAdapter(List<WeeklyModel> partList3) {
        this.partList3= partList3;
    }

    @NonNull
    @Override
    public WeeklyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // view_g => name of the layout file
        View view = inflater.inflate(R.layout.view_weekly, parent, false);
        WeeklyViewHolder holder = new WeeklyViewHolder(view);
        return holder;
    }//link xml to recycler view

    @Override//means whatever we are extending is changed to put our own stuff
    public void onBindViewHolder(@NonNull WeeklyViewHolder holder, int position) {
        WeeklyModel parts = partList3.get(position);
        holder.populate(parts);
    }

    @Override
    public int getItemCount() {
        return partList3.size();
    }
}
