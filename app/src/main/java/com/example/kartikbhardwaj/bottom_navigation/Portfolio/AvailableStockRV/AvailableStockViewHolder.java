package com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStockRV;

import com.example.kartikbhardwaj.bottom_navigation.ChartActivity;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableStockViewHolder extends RecyclerView.ViewHolder {

    private TextView stockName,stockValNet;
    private ImageView plusBtn,analyzeBtn;
    private LinearLayout stockCard;
    private ExpandableLayout expandableLayout;

    public AvailableStockViewHolder(@NonNull final View itemView) {
        super(itemView);
        stockName=itemView.findViewById(R.id.stockName);
        stockValNet=itemView.findViewById(R.id.stockValue);
        plusBtn=itemView.findViewById(R.id.purchasebtn);
        analyzeBtn=itemView.findViewById(R.id.analyzebtn);
        stockCard=itemView.findViewById(R.id.stockcard);
        expandableLayout=itemView.findViewById(R.id.expandablepositionoptions);

        analyzeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(itemView.getContext(),ChartActivity.class);
                itemView.getContext().startActivity(intent);
            }
        });

        stockCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout.isExpanded()) {
                    expandableLayout.setExpanded(false);
                }
                else{
                    expandableLayout.setExpanded(true);
                }
            }
        });

    }

    public void populate(AvailableStockModel stocksModel){
        stockName.setText(stocksModel.getIndexName());
        if(stocksModel.getIndexChanges()>=0){
            stockValNet.setTextColor(Color.parseColor("#00ff00"));
            stockValNet.setText(stocksModel.getIndexValue()+"(+"+String.valueOf(stocksModel.getIndexChanges())+"%)"+"▲");
        }
        else{
            stockValNet.setTextColor(Color.parseColor("#ff0000"));
            stockValNet.setText(stocksModel.getIndexValue()+"("+String.valueOf(stocksModel.getIndexChanges())+"%)"+"▼");

        }

    }
}
