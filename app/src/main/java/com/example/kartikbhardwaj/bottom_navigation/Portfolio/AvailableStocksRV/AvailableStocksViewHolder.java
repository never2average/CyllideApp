package com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStocksRV;

import com.example.kartikbhardwaj.bottom_navigation.R;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableStocksViewHolder extends RecyclerView.ViewHolder {

    TextView indexName,indexValNet;

    public AvailableStocksViewHolder(@NonNull View itemView) {
        super(itemView);
        indexName=itemView.findViewById(R.id.indexName);
        indexValNet=itemView.findViewById(R.id.indexValue);
    }

    public void populate(AvailableStocksModel stocksModel){
        indexName.setText(stocksModel.getIndexName());
        if(stocksModel.getIndexChanges()>=0){
            indexValNet.setTextColor(Color.parseColor("#00ff00"));
            indexValNet.setText(stocksModel.getIndexValue()+"(+"+String.valueOf(stocksModel.getIndexChanges())+"%)"+"▲");
        }
        else{
            indexValNet.setTextColor(Color.parseColor("#ff0000"));
            indexValNet.setText(stocksModel.getIndexValue()+"("+String.valueOf(stocksModel.getIndexChanges())+"%)"+"▼");

        }

    }
}
