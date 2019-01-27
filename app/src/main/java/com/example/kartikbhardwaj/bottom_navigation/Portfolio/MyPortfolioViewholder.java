package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.faq_view.FaqModal;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kartikbhardwaj.bottom_navigation.R;


public class MyPortfolioViewholder extends RecyclerView.ViewHolder {
    TextView portfolio;
    TextView returns;
    String portfolioName;
    String returnsName;


    public MyPortfolioViewholder(@NonNull View itemView) {
        super(itemView);
        portfolio=itemView.findViewById(R.id.portfolio);
        returns=itemView.findViewById(R.id.return1);
    }



    public void populate(MyPortfolioModel item){
        portfolioName=item.getPortfolioname();
        returnsName=item.getReturns();
        portfolio.setText(portfolioName);
        returns.setText(returnsName+" %");
    }
}
