package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV;

import com.example.kartikbhardwaj.bottom_navigation.R;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PositionsViewHolder extends RecyclerView.ViewHolder {

    TextView positionTickerTV;
    TextView positionQuantityTV,positionCurrPriceTV,positionTypeTV,positionValueTV;


    public PositionsViewHolder(@NonNull View itemView) {
        super(itemView);
        positionCurrPriceTV=itemView.findViewById(R.id.tv_pos_curr_price);
        positionQuantityTV=itemView.findViewById(R.id.tv_pos_quantity);
        positionTickerTV=itemView.findViewById(R.id.tv_pos_ticker);
        positionTypeTV=itemView.findViewById(R.id.pos_type);
        positionValueTV=itemView.findViewById(R.id.pos_total_amt);
    }

    public void populate(PositionsModel item){
        positionValueTV.setText(item.getPositionValue());
        positionTypeTV.setText(item.getPositionType());
        positionTickerTV.setText(item.getPositionTicker());
        positionCurrPriceTV.setText(item.getPositionCurrPrice());
        positionQuantityTV.setText("X"+item.getPositionQuantity());

    }
}
