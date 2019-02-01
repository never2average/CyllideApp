package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsFragment;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PositionsAdapter extends RecyclerView.Adapter<PositionsViewHolder>  {
    List<PositionsModel> positionsModels;

    @NonNull
    @Override
    public PositionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.portfolio_positions_rv_view,parent,false);
        PositionsViewHolder holder=new PositionsViewHolder(view);
        return holder;
    }

    public PositionsAdapter(List<PositionsModel> positionsModels) {
        this.positionsModels = positionsModels;
    }

    @Override
    public void onBindViewHolder(@NonNull final PositionsViewHolder holder, final int position) {
        PositionsModel positionsModel=positionsModels.get(position);
        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();
                positionsModels.remove(position);

                notifyItemRemoved(position);
                notifyItemRangeChanged(position,positionsModels.size());
               String balance= holder.positionCurrPriceTV.getText().toString();
               Float balance1 = Float.valueOf(balance);
            }
        });
        holder.populate(positionsModel);
    }

    @Override
    public int getItemCount() {
        return positionsModels.size();
    }
}
