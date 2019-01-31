package com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStockRV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableStockAdapter extends RecyclerView.Adapter<AvailableStockViewHolder> {

    public AvailableStockAdapter(List<AvailableStockModel> data) {
        this.data = data;
    }

    List<AvailableStockModel> data;

    @NonNull
    @Override
    public AvailableStockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.financial_instr_card,parent,false);
        AvailableStockViewHolder holder = new AvailableStockViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableStockViewHolder holder, int position) {
        AvailableStockModel stocksModel=data.get(position);
        holder.populate(stocksModel);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
