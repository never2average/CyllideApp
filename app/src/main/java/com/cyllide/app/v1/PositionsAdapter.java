package com.cyllide.app.v1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.portfolio.PortfolioPositionsRV.PositionsModel;

import java.util.ArrayList;

public class PositionsAdapter extends RecyclerView.Adapter<PositionsViewHolder> {
    ArrayList<PositionsModel> positionsModels;

    public PositionsAdapter(ArrayList<PositionsModel> positionsModels){
        this.positionsModels = positionsModels;
    }

    @NonNull
    @Override
    public PositionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.positions_item_view,parent,false);
        PositionsViewHolder holder = new PositionsViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull PositionsViewHolder holder, int position) {
        PositionsModel pm = positionsModels.get(position);
        holder.populate(pm);

    }


    @Override
    public int getItemCount() {
        return positionsModels.size();
    }
}
