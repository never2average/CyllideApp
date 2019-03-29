package com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStockRV;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.Charts.ChartActivity;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.CurrentPositions;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableStockViewHolder extends RecyclerView.ViewHolder {
    private TextView stockName,stockValNet;
    private ImageView plusBtn,analyzeBtn,purchaseBtn;
    public LinearLayout stockCard;
    public ExpandableLayout expandableLayout;
    private TextView stockTickerSelected;
    EditText stockQuantity;
    double priceAtPlace;
    String positiontype;
    Dialog popup;
    Button placeOrder;
    private RequestQueue requestQueue;
    Map<String,String> stringMap = new ArrayMap<>();


    public AvailableStockViewHolder(@NonNull final View itemView) {
        super(itemView);

        stockName=itemView.findViewById(R.id.stockName);
        stockValNet=itemView.findViewById(R.id.stockValue);
        plusBtn=itemView.findViewById(R.id.purchasebtn);
        analyzeBtn=itemView.findViewById(R.id.analyzebtn);
        stockCard=itemView.findViewById(R.id.stockcard);
        expandableLayout=itemView.findViewById(R.id.expandablepositionoptions);
        purchaseBtn = itemView.findViewById(R.id.purchasebtn1);



        analyzeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(itemView.getContext(),ChartActivity.class);
                intent.putExtra("ticker",stockName.getText().toString());
                itemView.getContext().startActivity(intent);
            }
        });

        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundResource(R.drawable.cross_icon);
//                view.set
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

    public void populate(AvailableStockModel stocksModel, Context context){
        stockName.setText(stocksModel.getIndexName());
        getSingleValue(stocksModel.getIndexName(),context);
    }

    void getSingleValue(String ticker, Context context){
        requestQueue = Volley.newRequestQueue(context);
        String url = "http://data.cyllide.com/data/stock/close";
        stringMap.put("value","1221D122");
        stringMap.put("ticker","122"+ticker+"122");
        stringMap.put("singleVal","True");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String string = jsonObject.getString("data");
                    if(jsonObject.getDouble("movement")>=0){
                        stockValNet.setTextColor(Color.parseColor("#00ff00"));
                        stockValNet.setText(string+"(+"+String.valueOf(jsonObject.getDouble("movement")).substring(0,5)+"%)"+"▲");
                    }
                    else{
                        stockValNet.setTextColor(Color.parseColor("#ff0000"));
                        stockValNet.setText(string+"("+String.valueOf(jsonObject.getDouble("movement")).substring(0,5)+"%)"+"▼");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("resp", error.toString());
            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}