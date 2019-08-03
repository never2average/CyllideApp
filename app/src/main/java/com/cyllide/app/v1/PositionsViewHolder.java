package com.cyllide.app.v1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PositionsViewHolder extends RecyclerView.ViewHolder {
    TextView quantityTV,nameTV,avgPrice,ltp;
    ImageView buy;

    public PositionsViewHolder(@NonNull View itemView) {
        super(itemView);
        quantityTV = itemView.findViewById(R.id.position_quantity);
        nameTV = itemView.findViewById(R.id.position_name);
        avgPrice = itemView.findViewById(R.id.position_average_price);
        ltp = itemView.findViewById(R.id.position_ltp);
        buy = itemView.findViewById(R.id.position_buy_button) ;
    }
}
