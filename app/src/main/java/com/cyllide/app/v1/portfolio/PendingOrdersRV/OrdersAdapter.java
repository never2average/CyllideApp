package com.cyllide.app.v1.portfolio.PendingOrdersRV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.R;
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
        cardView=view.findViewById(R.id.orders_card);
        return holder;
        }




}
