package com.cyllide.app.beta;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PortfolioGameCardRVAdapter extends RecyclerView.Adapter<PortfolioGameCardViewholder> {
    private ArrayList<PortfolioGameCardModel> data;

    public PortfolioGameCardRVAdapter(ArrayList<PortfolioGameCardModel> data) {
        this.data = data;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PortfolioGameCardViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.game_card_nifty50,parent,false);
        PortfolioGameCardViewholder holder = new PortfolioGameCardViewholder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull PortfolioGameCardViewholder holder, int position) {
        Log.d("DETAILSSS",data.get(position).getTicker());
        Log.d("DETAILSSS",data.get(position).getCompanySector());
        holder.populate(data.get(position));

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
