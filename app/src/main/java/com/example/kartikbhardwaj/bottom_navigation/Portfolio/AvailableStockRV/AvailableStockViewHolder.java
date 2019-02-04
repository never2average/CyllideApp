package com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStockRV;

import com.example.kartikbhardwaj.bottom_navigation.ChartActivity;
import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioActivity;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsFragment;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.BalanceClass;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PendingOrdersInterface;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PortfolioPositionsInterface;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableStockViewHolder extends RecyclerView.ViewHolder {

    private EditText stockQuantity;
    private TextView stockName,stockValNet;
    private ImageView plusBtn,analyzeBtn;
    private LinearLayout stockCard;
    private ExpandableLayout expandableLayout;
    private TextView stockTickerSelected;
    private Button placeOrder;
    private String positiontype;
    private String orderType;
    private double priceAtPlace;
  private int previousQuantity;
  private   int j;
  private   double previousPrice;
  private   int newQuantity;
  private   double newPrice;
  private   String previousPositionType;
  private   String newPositiontype;
  private double previousBalance;
  private double newBalance;
  private int quantity;

    Dialog popup;

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
                SingleSelectToggleGroup OrderType;
                OrderType=popup.findViewById(R.id.Order_type_choices);
                placeOrder=popup.findViewById(R.id.place_order);
                placeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                            newPrice = priceAtPlace;

                            for(int i=0;i<PortfolioPositionsInterface.stockTicker.size();i++) {
                                if (stockName.getText().toString().equals(PortfolioPositionsInterface.stockTicker.get(i))) {
                                    j = 1;

                                    previousQuantity = PortfolioPositionsInterface.Quantity.get(i);
                                    previousPrice = PortfolioPositionsInterface.orderPrice.get(i);
                                    newQuantity = Integer.valueOf(stockQuantity.getText().toString());
                                    previousPositionType = PortfolioPositionsInterface.positionType.get(i);
                                    newPositiontype = positiontype;


                                    if (orderType.equals("Market")) {




                                        if (positiontype.equals(PortfolioPositionsInterface.positionType.get(i))) {

                                            PortfolioPositionsInterface.Quantity.set(i, (previousQuantity + newQuantity));
                                            PortfolioPositionsInterface.orderPrice.set(i, ((((previousQuantity * previousPrice) + (newQuantity * priceAtPlace)) / (previousQuantity + newQuantity))));


                                        } else {


                                            if (previousQuantity > newQuantity) {


                                                PortfolioPositionsInterface.Quantity.set(i, (previousQuantity - newQuantity));
                                                // PortfolioPositionsInterface.positionType.set(i, previousPositionType);


                                            } else {

                                                if (newQuantity > previousQuantity)

                                                {


                                                    PortfolioPositionsInterface.Quantity.set(i, (newQuantity - previousQuantity));
                                                    PortfolioPositionsInterface.positionType.set(i, positiontype);


                                                } else if (newQuantity == previousQuantity) {


                                                    PortfolioPositionsInterface.positionType.remove(i);
                                                    PortfolioPositionsInterface.orderType.remove(i);
                                                    PortfolioPositionsInterface.stockTicker.remove(i);
                                                    PortfolioPositionsInterface.orderPrice.remove(i);
                                                    PortfolioPositionsInterface.Quantity.remove(i);


                                                }

                                            }


                                        }


                                    }


                                }
                            }
                            if(j!=1)
                            {

                            PortfolioPositionsInterface.positionType.add(positiontype);
                            PortfolioPositionsInterface.orderType.add(orderType);
                            PortfolioPositionsInterface.stockTicker.add(stockName.getText().toString());
                            PortfolioPositionsInterface.orderPrice.add(priceAtPlace);
                            PortfolioPositionsInterface.Quantity.add(Integer.valueOf(stockQuantity.getText().toString()));


                            }



                         else {

                            PendingOrdersInterface.positionType.add(positiontype);
                            PendingOrdersInterface.orderType.add(orderType);
                            PendingOrdersInterface.stockTicker.add(stockName.getText().toString());
                            PendingOrdersInterface.orderPrice.add(priceAtPlace);
                            PendingOrdersInterface.Quantity.add(Integer.valueOf(stockQuantity.getText().toString()));
                            PendingOrdersInterface.executionPrice.add(Double.valueOf(stockQuantity.getText().toString()));






                        }

                        if (positiontype.equals("LONG")) {

                            previousBalance=BalanceClass.balance;
                            quantity= Integer.valueOf(stockQuantity.getText().toString());


                            newBalance=previousBalance-quantity*priceAtPlace;
                            BalanceClass.balance=newBalance;
                            Toast.makeText(itemView.getContext(),"inside long "+String.valueOf(previousBalance)+" "+String.valueOf(newBalance),Toast.LENGTH_LONG).show();




                        } else  if (positiontype.equals("SHORT")){

                            previousBalance=BalanceClass.balance;
                            quantity= Integer.valueOf(stockQuantity.getText().toString());
                            newBalance=previousBalance+quantity*priceAtPlace;
                            BalanceClass.balance=newBalance;




                        }

                        popup.dismiss();


                    }



                });



                final SingleSelectToggleGroup positionChoice;

                positionChoice=popup.findViewById(R.id.Position_choices);



                positionChoice.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.Buy:
                               // Toast.makeText(itemView.getContext(),"Buy",Toast.LENGTH_LONG).show();
                                positiontype ="LONG";



                                break;
                            case R.id.Sell:
                                //Toast.makeText(itemView.getContext(),"Sell",Toast.LENGTH_LONG).show();
                                positiontype="SHORT";
                                break;


                            case R.id.Short:
                                //Toast.makeText(itemView.getContext(),"Short",Toast.LENGTH_LONG).show();
                                positiontype="SHORT";
                                break;


                            case R.id.Cover:
                                //Toast.makeText(itemView.getContext(),"Cover",Toast.LENGTH_LONG).show();
                                positiontype="LONG";






                        }
                    }
                });




                OrderType.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                      switch (checkedId) {
                          case R.id.Market:
                              orderType = "Market";
                              break;
                          case R.id.Limit:
                              orderType="Limit";
                              break;
                          case R.id.Stop_Loss:
                              orderType="StopLoss";
                              break;
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