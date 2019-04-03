package com.cyllide.app.v1.contests.portfolioRV;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyllide.app.v1.R;

import java.util.List;

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