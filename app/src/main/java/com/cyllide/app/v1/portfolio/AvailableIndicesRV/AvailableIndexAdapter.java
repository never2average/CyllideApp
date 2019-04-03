package com.cyllide.app.v1.portfolio.AvailableIndicesRV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyllide.app.v1.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableIndexAdapter extends RecyclerView.Adapter<AvailableIndexViewHolder> {
    public AvailableIndexAdapter(List<AvailableIndexModel> data) {
        this.data = data;
    }

    List<AvailableIndexModel> data;

    @NonNull
    @Override
    public AvailableIndexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.financial_index_card,parent,false);
        AvailableIndexViewHolder holder = new AvailableIndexViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableIndexViewHolder holder, int position) {
        AvailableIndexModel stocksModel=data.get(position);
        holder.populate(stocksModel);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
