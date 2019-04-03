package com.cyllide.app.v1.portfolio.OrderHistoryRV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyllide.app.v1.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryViewHolder> {

    List<OrderHistoryModel> data;
    public OrderHistoryAdapter(List<OrderHistoryModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.order_exit_history_card,parent,false);
        OrderHistoryViewHolder holder = new OrderHistoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        OrderHistoryModel orderHistoryModel = data.get(position);
        holder.populate(orderHistoryModel);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
