package com.cyllide.app.beta;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.beta.portfolio.PortfolioPositionsRV.PositionsModel;

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
        ltp.setText(String.format("%.2f",Double.parseDouble(positionsModel.getPositionltp())));
        avgPrice.setText( String.format("%.2f",Double.parseDouble(positionsModel.getPositionCurrPrice())));
        cost.setText(String.format("%.2f", Double.parseDouble(positionsModel.getPositionValue())));
        if(Double.valueOf(positionsModel.getPositionValue())>=0){
            cost.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.green));
        }
        else{
            cost.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.red));

        }

//        cost.setText(positionsModel.getPositionCost());
    }
}
