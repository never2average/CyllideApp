package com.example.kartikbhardwaj.bottom_navigation.portfolio;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.AppConstants;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;
import com.nex3z.togglebuttongroup.button.LabelToggle;


public class MyPortfolioViewholder extends RecyclerView.ViewHolder {
    TextView portfolioTV;
    LabelToggle capexTV;
    MaterialCardView materialCardView;
    String portfolioName;
    String capexName;
    View rvView;


    public MyPortfolioViewholder(@NonNull final View itemView) {
        super(itemView);
        rvView = itemView;
        portfolioTV =itemView.findViewById(R.id.my_portfolios_portfolio_name);
        capexTV =itemView.findViewById(R.id.my_portfolios_capex);
        materialCardView=itemView.findViewById(R.id.my_portfolios_view_card_material);

    }



    public void populate(final MyPortfolioModel item){
        portfolioName=item.getPortfolioname();
        capexName =item.getCapex();
        portfolioTV.setText(portfolioName);
        capexTV.setText(capexName);
        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.portfolioID = item.getId();
                Intent portfolioIntent = new Intent(rvView.getContext(),PortfolioActivity.class);
                rvView.getContext().startActivity(portfolioIntent);
            }
        });


    }
}
