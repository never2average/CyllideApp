package com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV;


import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.Contests.ContestLeaderboard;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;
import java.util.Map;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioViewHolder> {

    Context context;
    private List<PortfolioModel> portfolioModelList;

    public PortfolioAdapter(Context context, List<PortfolioModel> portfolioModelList) {
        this.context = context;
        this.portfolioModelList = portfolioModelList;
    }




    @Override
    public PortfolioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_portfolio_list, parent, false);

        return new PortfolioViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PortfolioViewHolder holder, int position) {
        holder.populate(portfolioModelList.get(position), context);
    }

    @Override
    public int getItemCount() {
        return portfolioModelList.size();
    }
}