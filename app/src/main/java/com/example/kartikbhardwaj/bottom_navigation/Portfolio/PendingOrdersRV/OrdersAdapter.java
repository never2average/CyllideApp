package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolioModel;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolioViewholder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersFragment;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PendingOrdersInterface;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersViewHolder> {
   private List<OrdersModel>data;
   private MaterialCardView cardView;
   private String savedPositionType;
   private String savedOrderType;
   private String savedStockTicker;
   private double savedOrderPrice;
   private int savedQuantity;


    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {

        OrdersModel list = data.get(position);

        holder.populate(list);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public OrdersAdapter(List<OrdersModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.pending_orders_rv_view,parent,false);
        OrdersViewHolder holder = new OrdersViewHolder(view);
        cardView=view.findViewById(R.id.card);












        return holder;
        //return null;
        }


    public void removeItem(int position) {
        data.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
        savedOrderPrice=PendingOrdersInterface.orderPrice.get(position);
        savedOrderType=PendingOrdersInterface.orderType.get(position);
        savedPositionType=PendingOrdersInterface.positionType.get(position);
        savedStockTicker=PendingOrdersInterface.stockTicker.get(position);
        savedQuantity=PendingOrdersInterface.Quantity.get(position);
        PendingOrdersInterface.positionType.remove(position);
        PendingOrdersInterface.orderType.remove(position);
        PendingOrdersInterface.stockTicker.remove(position);
        PendingOrdersInterface.orderPrice.remove(position);
        PendingOrdersInterface.Quantity.remove(position);



    }

    public void restoreItem(OrdersModel item, int position) {
        data.add(position, item);
        PendingOrdersInterface.positionType.add(position,savedPositionType);
        PendingOrdersInterface.orderType.add(position,savedOrderType);
        PendingOrdersInterface.stockTicker.add(position,savedStockTicker);
        PendingOrdersInterface.orderPrice.add(position,savedOrderPrice);
        PendingOrdersInterface.Quantity.add(position,savedQuantity);
        // notify item added by position
        notifyItemInserted(position);
    }



}
