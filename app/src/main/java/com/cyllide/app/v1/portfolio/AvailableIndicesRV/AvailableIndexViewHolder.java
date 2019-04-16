package com.cyllide.app.v1.portfolio.AvailableIndicesRV;

import com.cyllide.app.v1.R;

import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableIndexViewHolder extends RecyclerView.ViewHolder {

    TextView indexName,indexValNet;
    ExpandableLayout expandableLayout;
    LinearLayout findexCard;

    public AvailableIndexViewHolder(@NonNull View itemView) {
        super(itemView);
        indexName=itemView.findViewById(R.id.indexName);
        indexValNet=itemView.findViewById(R.id.indexValue);
        expandableLayout = itemView.findViewById(R.id.indexExtraOptions);
        findexCard = itemView.findViewById(R.id.findexcard);
    }

    public void populate(AvailableIndexModel stocksModel){
        indexName.setText(stocksModel.getIndexName());
        if(stocksModel.getIndexChanges()>=0){
            indexValNet.setTextColor(Color.parseColor("#00ff00"));
            indexValNet.setText(stocksModel.getIndexValue()+"(+"+String.valueOf(stocksModel.getIndexChanges())+"%)"+"▲");
        }
        else{
            indexValNet.setTextColor(Color.parseColor("#ff0000"));
            indexValNet.setText(stocksModel.getIndexValue()+"("+String.valueOf(stocksModel.getIndexChanges())+"%)"+"▼");

        }
        findexCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expandableLayout.isExpanded()){
                    expandableLayout.setExpanded(false);
                }
                else{
                    expandableLayout.setExpanded(true);
                }
            }
        });

    }
}
