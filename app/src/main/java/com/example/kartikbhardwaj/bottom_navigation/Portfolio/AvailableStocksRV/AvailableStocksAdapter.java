package com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStocksRV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableStocksAdapter extends RecyclerView.Adapter<AvailableStocksViewHolder> {
    public AvailableStocksAdapter(List<AvailableStocksModel> data) {
        this.data = data;
    }

    List<AvailableStocksModel> data;

    @NonNull
    @Override
    public AvailableStocksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.financial_index_card,parent,false);
        AvailableStocksViewHolder holder = new AvailableStocksViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableStocksViewHolder holder, int position) {
        AvailableStocksModel stocksModel=data.get(position);
        holder.populate(stocksModel);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
