package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.PasswordChangeStatus;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.FaqModal;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;


public class MyPortfolioViewholder extends RecyclerView.ViewHolder {
    TextView portfolio;
    TextView returns;
    MaterialCardView materialCardView;
    String portfolioName;
    String returnsName;



    public MyPortfolioViewholder(@NonNull final View itemView) {
        super(itemView);
        portfolio=itemView.findViewById(R.id.portfolio);
        returns=itemView.findViewById(R.id.return1);
        materialCardView=itemView.findViewById(R.id.card_view);
        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent portfolioIntent = new Intent(itemView.getContext(),PortfolioActivity.class);
                portfolioIntent.putExtra("stock_name",portfolio.getText().toString());
                PasswordChangeStatus.rvCardstatus=true;
                itemView.getContext().startActivity(portfolioIntent);



            }
        });

    }



    public void populate(MyPortfolioModel item){
        portfolioName=item.getPortfolioname();
        returnsName=item.getReturns();
        portfolio.setText(portfolioName);
        returns.setText(returnsName+" %");


    }
}
