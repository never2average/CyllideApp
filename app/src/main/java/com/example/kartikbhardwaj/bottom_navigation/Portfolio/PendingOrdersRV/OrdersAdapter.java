package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolioModel;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolioViewholder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kartikbhardwaj.bottom_navigation.R;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersViewHolder> {
    List<OrdersModel>data;

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
        return holder;
        //return null;
    }
}
