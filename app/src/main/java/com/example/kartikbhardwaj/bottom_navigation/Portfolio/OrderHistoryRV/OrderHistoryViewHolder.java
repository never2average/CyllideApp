package com.example.kartikbhardwaj.bottom_navigation.Portfolio.OrderHistoryRV;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.text.DecimalFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

    private TextView tickerName, positionType, exitTime, entryPrice, exitPrice, orderQuantity, orderPnl;

    public OrderHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        tickerName = itemView.findViewById(R.id.exit_order_ticker_name);
        positionType = itemView.findViewById(R.id.exit_order_position_type);
        exitTime = itemView.findViewById(R.id.exit_order_exit_time);
        entryPrice = itemView.findViewById(R.id.exit_order_entry_price);
        exitPrice = itemView.findViewById(R.id.exit_order_exit_price);
        orderQuantity = itemView.findViewById(R.id.exit_order_quantity);
        orderPnl = itemView.findViewById(R.id.exit_order_pnl);
    }

    public void populate(OrderHistoryModel orderHistoryModel){
        DecimalFormat format = new DecimalFormat("##.00");
        tickerName.setText(orderHistoryModel.getStockTicker());
        positionType.setText(orderHistoryModel.getPositionType());
        exitPrice.setText(String.valueOf(orderHistoryModel.getExitPrice()));
        exitTime.setText(orderHistoryModel.getExitTime());
        entryPrice.setText(String.valueOf(orderHistoryModel.getEntryPrice()));
        orderQuantity.setText(String.valueOf(orderHistoryModel.getQuantity()));
        if (orderHistoryModel.getPositionType().equals("Long")) {
            if (orderHistoryModel.getEntryPrice() <= orderHistoryModel.getExitPrice()) {
                orderPnl.setTextColor(Color.parseColor("#00ff00"));
                orderPnl.setText(
                        String.valueOf(format.format(orderHistoryModel.getQuantity() * (orderHistoryModel.getExitPrice() - orderHistoryModel.getEntryPrice())))
                                + " ▲");
            } else {
                orderPnl.setTextColor(Color.parseColor("#ff0000"));
                orderPnl.setText(

                        format.format(orderHistoryModel.getQuantity() * (orderHistoryModel.getExitPrice() - orderHistoryModel.getEntryPrice())) + " ▼"
                );

            }
        }
        else{
            if (orderHistoryModel.getEntryPrice() > orderHistoryModel.getExitPrice()) {
                orderPnl.setTextColor(Color.parseColor("#00ff00"));
                orderPnl.setText(
                        String.valueOf(orderHistoryModel.getQuantity() * (orderHistoryModel.getEntryPrice() - orderHistoryModel.getExitPrice()))
                                + " ▲");
            } else {
                orderPnl.setTextColor(Color.parseColor("#ff0000"));
                orderPnl.setText(
                        String.valueOf(orderHistoryModel.getQuantity() * (orderHistoryModel.getEntryPrice() - orderHistoryModel.getExitPrice())) + " ▼"
                );

            }
        }
    }
}
