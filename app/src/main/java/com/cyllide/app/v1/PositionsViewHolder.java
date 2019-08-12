package com.cyllide.app.v1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.portfolio.PortfolioPositionsRV.PositionsModel;

public class PositionsViewHolder extends RecyclerView.ViewHolder {
    TextView quantityTV,nameTV,avgPrice,ltp, cost;

    public PositionsViewHolder(@NonNull View itemView) {
        super(itemView);
        quantityTV = itemView.findViewById(R.id.position_quantity);
        nameTV = itemView.findViewById(R.id.position_name);
        avgPrice = itemView.findViewById(R.id.position_average_price);
        ltp = itemView.findViewById(R.id.position_ltp);
        cost = itemView.findViewById(R.id.position_cost);
    }
    public void populate(PositionsModel positionsModel){
        //TODO MESSED UP CLEAN HERE
        quantityTV.setText(positionsModel.getPositionQuantity());
        nameTV.setText(positionsModel.getPositionTicker());
        ltp.setText(positionsModel.getPositionltp());
//        cost.setText(positionsModel.getPositionCost());
    }
}
