package com.example.kartikbhardwaj.bottom_navigation.portfolio.AvailableStockRV;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.AppConstants;
import com.example.kartikbhardwaj.bottom_navigation.charts.ChartActivity;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.PortfolioPositionsRV.CurrentPositions;
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
    RequestQueue placePosition;
    Map<String,String> positionMap = new ArrayMap<>();


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


                popup = new Dialog(itemView.getContext());

                popup.setContentView(R.layout.add_stock_popup);
                stockQuantity = popup.findViewById(R.id.stockquantity);
                stockTickerSelected = popup.findViewById(R.id.stock_ticker_selected);
                stockTickerSelected.setText(stockName.getText().toString());


                popup.getWindow().setBackgroundDrawableResource(android.R.color.white);
                popup.show();

                final SingleSelectToggleGroup positionChoice;

                positionChoice = popup.findViewById(R.id.long_short_choice);


                positionChoice.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.Buy:
                                positiontype = "LONG";
                                break;
                            case R.id.Sell:
                                positiontype = "SHORT";
                                break;
                        }
                    }
                });

                placeOrder = popup.findViewById(R.id.place_order);

                placeOrder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            placeOrderVolley(itemView, positiontype, stockQuantity.getText().toString(), stockName.getText().toString(),popup,itemView.getContext());
                        } catch (NumberFormatException e) {
                            Toast.makeText(itemView.getContext(), "Enter a Valid Number", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }


    void placeOrderVolley(final View view, String positionType, String quantity, String ticker, final Dialog dialog, final Context context){
        positionMap.put("token", AppConstants.token);
        positionMap.put("portfolioID", AppConstants.portfolioID);
        positionMap.put("ticker", ticker);
        positionMap.put("quantity", quantity);
        positionMap.put("isLong", positionType);
        String url = view.getResources().getString(R.string.apiBaseURL)+"portfolio/order";
        placePosition = Volley.newRequestQueue(view.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Toast.makeText(context,"Order Placed Successfully",Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
          @Override
          public Map<String,String> getHeaders(){ return positionMap; }
        };
        placePosition.add(stringRequest);
    }

    public void populate(AvailableStockModel stocksModel, Context context){
        stockName.setText(stocksModel.getIndexName());
        getSingleValue(stocksModel.getIndexName(),context);
    }

    void getSingleValue(String ticker, Context context){
        requestQueue = Volley.newRequestQueue(context);
        String url = "http://data.cyllide.com/data/stock/close";
        stringMap.put("value","1231D123");
        stringMap.put("ticker","123"+ticker+"123");
        stringMap.put("singleVal","True");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String string = jsonObject.getString("data");
                    if (jsonObject.getDouble("movement") >= 0) {
                        stockValNet.setTextColor(Color.parseColor("#00ff00"));
                        stockValNet.setText(string + "(+" + String.valueOf(jsonObject.getDouble("movement")) + "%)" + "▲");
                    } else {
                        stockValNet.setTextColor(Color.parseColor("#ff0000"));
                        stockValNet.setText(string + "(" + String.valueOf(jsonObject.getDouble("movement"))+ "%)" + "▼");
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
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
}