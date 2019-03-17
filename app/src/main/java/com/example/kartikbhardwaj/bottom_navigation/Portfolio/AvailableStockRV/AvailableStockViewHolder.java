package com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStockRV;

import com.example.kartikbhardwaj.bottom_navigation.Charts.ChartActivity;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.CurrentPositions;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.time.Instant;
import java.time.LocalDateTime;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableStockViewHolder extends RecyclerView.ViewHolder {

    private TextView stockName,stockValNet;
    private ImageView plusBtn,analyzeBtn;
    private LinearLayout stockCard;
    private ExpandableLayout expandableLayout;
    private TextView stockTickerSelected;
    EditText stockQuantity;
    double priceAtPlace;
    String positiontype;
    Dialog popup;
    Button placeOrder;

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
                intent.putExtra("ticker",stockName.getText().toString());
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

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                popup=new Dialog(itemView.getContext());

                popup.setContentView(R.layout.add_stock_popup);
                stockQuantity=popup.findViewById(R.id.stockquantity);
                stockTickerSelected=popup.findViewById(R.id.stock_ticker_selected);
                stockTickerSelected.setText(stockName.getText().toString());


                popup.getWindow().setBackgroundDrawableResource(android.R.color.white);
                popup.show();

                final SingleSelectToggleGroup positionChoice;

                positionChoice=popup.findViewById(R.id.Position_choices);



                positionChoice.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.Buy:
                                positiontype ="LONG";
                                break;
                            case R.id.Sell:
                                positiontype="SHORT";
                                break;
                        }
                    }
                });

                placeOrder = popup.findViewById(R.id.place_order);

                placeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CurrentPositions.tickerName.add(stockName.getText().toString());
                        try {
                            CurrentPositions.tickerQuantity.add(Integer.parseInt(stockQuantity.getText().toString()));
                            if(positiontype.equals("LONG")){
                                CurrentPositions.tickerPositionType.add(true);
                            }
                            else{
                                CurrentPositions.tickerPositionType.add(true);
                            }
                            CurrentPositions.tickerEntryTime.add(System.currentTimeMillis()/1000L);
                            popup.dismiss();
                        }
                        catch(NumberFormatException e){
                            Toast.makeText(itemView.getContext(),"Enter a Valid Number",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    public void populate(AvailableStockModel stocksModel){
        stockName.setText(stocksModel.getIndexName());
        if(stocksModel.getIndexChanges()>=0){
            priceAtPlace= stocksModel.getIndexValue();
            stockValNet.setTextColor(Color.parseColor("#00ff00"));
            stockValNet.setText(stocksModel.getIndexValue()+"(+"+String.valueOf(stocksModel.getIndexChanges())+"%)"+"▲");
        }
        else{
            priceAtPlace= stocksModel.getIndexValue();
            stockValNet.setTextColor(Color.parseColor("#ff0000"));
            stockValNet.setText(stocksModel.getIndexValue()+"("+String.valueOf(stocksModel.getIndexChanges())+"%)"+"▼");

        }
    }
}