package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV;

import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolioModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kartikbhardwaj.bottom_navigation.R;


public class OrdersViewHolder extends RecyclerView.ViewHolder {
    TextView OrderType;
    TextView PositionType;
      TextView Quantity;
    TextView ExecutionPrice;
    TextView CurrentStockPrice;
    TextView StockTicker;


    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        OrderType=itemView.findViewById(R.id.order_type_tv);
        PositionType=itemView.findViewById(R.id.position_type_tv);
        Quantity=itemView.findViewById(R.id.quantity_tv);
        ExecutionPrice=itemView.findViewById(R.id.exec_price_tv);
        CurrentStockPrice=itemView.findViewById(R.id.current_price_tv);
        StockTicker=itemView.findViewById(R.id.stock_ticker_tv);
    }


    public void populate(OrdersModel item){
        OrderType .setText(item.getOrderType());
        PositionType.setText(item.getPositionType());
        Quantity.setText(item.getQuantity());
        ExecutionPrice.setText(item.getExecutionPrice());
        StockTicker.setText(item.getStockTicker());
        CurrentStockPrice.setText(item.getCurrentStockPrice());


    }
}
