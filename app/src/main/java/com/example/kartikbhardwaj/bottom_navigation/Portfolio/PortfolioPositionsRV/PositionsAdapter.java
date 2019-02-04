package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.Balance;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioActivity;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsFragment;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class PositionsAdapter extends RecyclerView.Adapter<PositionsViewHolder>  {
    List<PositionsModel> positionsModels;


    View mView;
    String positionType;
    double previousBalance;
    double quantity;
    double newBalance;
    double orderPrice;
    TextView tv;


    @NonNull
    @Override
    public PositionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.portfolio_positions_rv_view,parent,false);
        PositionsViewHolder holder=new PositionsViewHolder(view);
        return holder;
    }

    public PositionsAdapter(List<PositionsModel> positionsModels,View mView) {
        this.mView=mView;
        this.positionsModels = positionsModels;
    }

    @Override
    public void onBindViewHolder(@NonNull final PositionsViewHolder holder, final int position) {
        PositionsModel positionsModel=positionsModels.get(position);
        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = holder.getAdapterPosition();

                positionType=PortfolioPositionsInterface.positionType.get(position);
                quantity= PortfolioPositionsInterface.Quantity.get(position);
                orderPrice=PortfolioPositionsInterface.orderPrice.get(position);

                // function to modify balance when element is delleted



                    previousBalance=BalanceClass.balance;
                    newBalance=previousBalance+quantity*orderPrice;
                    BalanceClass.balance=newBalance;
                PortfolioPositionsFragment fragment =new PortfolioPositionsFragment();
                //tv =fragment.BalanceTXTV
               // fragment.BalanceTXTV.setText("$ "+String.valueOf(BalanceClass.balance));

               //  fragment.setTextViewText(String.valueOf(BalanceClass.balance));



                    // remove item from recycler view
                positionsModels.remove(position);

                notifyItemRemoved(position);

                notifyItemRangeChanged(position,positionsModels.size());


                // remove item from array list

                PortfolioPositionsInterface.positionType.remove(position);
                PortfolioPositionsInterface.orderType.remove(position);
                PortfolioPositionsInterface.stockTicker.remove(position);
                PortfolioPositionsInterface.orderPrice.remove(position);
                PortfolioPositionsInterface.Quantity.remove(position);
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
