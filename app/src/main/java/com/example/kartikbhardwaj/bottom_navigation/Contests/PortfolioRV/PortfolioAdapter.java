package com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioViewHolder> {
    List<PortfolioModel> partList2;
    @NonNull
    @Override
    public PortfolioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // view_g => name of the layout file
        View view = inflater.inflate(R.layout.popup_portfolio_card, parent, false);
        PortfolioViewHolder holder = new PortfolioViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioViewHolder holder, int position) {
        PortfolioModel parts = partList2.get(position);
        holder.populate(parts);

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
