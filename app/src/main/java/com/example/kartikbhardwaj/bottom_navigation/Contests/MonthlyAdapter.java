package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyViewHolder> {
    List<MonthlyModel> partList2;

    public MonthlyAdapter(List<MonthlyModel> partList2) {
        this.partList2 = partList2;
    }

    @NonNull
    @Override
    public MonthlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // view_g => name of the layout file
        View view = inflater.inflate(R.layout.view_monthly, parent, false);
        MonthlyViewHolder holder = new MonthlyViewHolder(view);
        return holder;
    }//link xml to recycler view

    @Override//means whatever we are extending is changed to put our own stuff
    public void onBindViewHolder(@NonNull MonthlyViewHolder holder, int position) {
        MonthlyModel parts = partList2.get(position);
        holder.populate(parts);
    }

    @Override
    public int getItemCount() {
        return partList2.size();
    }
}
