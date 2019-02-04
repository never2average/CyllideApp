package com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV;

import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PortfolioViewHolder extends RecyclerView.ViewHolder {
    private TextView portfolioName;
    private TextView portfolioReturns;
    private String name;
    private double returns;
    View view;
    public PortfolioViewHolder(@NonNull View itemView) {
        super(itemView);
        portfolioName=itemView.findViewById(R.id.pflioname);
        portfolioReturns=itemView.findViewById(R.id.pflioreturns);
        view = itemView;
    }

    void populate(PortfolioModel portfolioModel){
        name = portfolioModel.getPortfolioname();
        returns=portfolioModel.getReturns();
        portfolioName.setText(name);
        portfolioReturns.setText(Double.toString(returns));
    }
}
