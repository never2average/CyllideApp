package com.cyllide.app.v1.portfolio.PendingOrdersRV;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
import com.cyllide.app.v1.portfolio.PortfolioPositionsFragment;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Map;


public class OrdersViewHolder extends RecyclerView.ViewHolder {
    TextView PositionType;
    TextView Quantity;
    TextView CurrentStockPrice;
    TextView StockTicker;
    Dialog dialog;
    MaterialCardView ordersCV;
    private RequestQueue stockPriceRequestQueue;
    private Map<String,String> stringMap = new ArrayMap<>();
    Map<String,String> exitHoldingPosition = new ArrayMap<>();
    private RequestQueue requestQueue;


    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        PositionType=itemView.findViewById(R.id.position_type_tv);
        Quantity=itemView.findViewById(R.id.quantity_tv);
        CurrentStockPrice=itemView.findViewById(R.id.current_price_tv);
        StockTicker=itemView.findViewById(R.id.stock_ticker_tv);
        dialog = new Dialog(itemView.getContext());
        dialog.setContentView(R.layout.portfolio_conformation_dialog);
        ordersCV = itemView.findViewById(R.id.orders_card);
    }


    public void populate(final OrdersModel item){
        PositionType.setText(item.getPositionType());
        Quantity.setText(item.getQuantity());
        StockTicker.setText(item.getStockTicker());
        getSingleValue(item.getStockTicker());
        ordersCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        Button buttonYes = dialog.findViewById(R.id.portfolio_dialog_yes_button);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                exitPositionVolley(item);
            }
        });

        Button buttonNo = dialog.findViewById(R.id.portfolio_dialog_no_button);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    void exitPositionVolley(OrdersModel ordersModel){
        exitHoldingPosition.put("token",AppConstants.token);
        exitHoldingPosition.put("portfolioID",AppConstants.portfolioID);
        exitHoldingPosition.put("state","Pending");
        exitHoldingPosition.put("ticker",ordersModel.getStockTicker());
        exitHoldingPosition.put("quantity",ordersModel.getQuantity());
        exitHoldingPosition.put("isLong",ordersModel.getPositionType());

        requestQueue = Volley.newRequestQueue(itemView.getContext());
        String url = itemView.getResources().getString(R.string.apiBaseURL)+"portfolio/order";
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                Fragment myFragment = new PortfolioPositionsFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.portfolio_container, myFragment).addToBackStack(null).commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("exitError",error.toString());
            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return exitHoldingPosition;
            }
        };
        requestQueue.add(stringRequest);
    }

    void getSingleValue(String ticker){
        stockPriceRequestQueue = Volley.newRequestQueue(itemView.getContext());
        String url = itemView.getResources().getString(R.string.dataApiBaseURL) + "stocks/close";
        stringMap.put("value","1231D123");
        stringMap.put("ticker","123"+ticker+"123");
        stringMap.put("singleVal","True");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Double data = jsonObject.getDouble("data");
                    Double movement = jsonObject.getDouble("movement");
                    DecimalFormat df = new DecimalFormat("####0.000");
                    if (jsonObject.getDouble("movement") >= 0) {
                        CurrentStockPrice.setTextColor(Color.parseColor("#00ff00"));
                        CurrentStockPrice.setText(df.format(data) + "(+" + df.format(movement) + "%)" + "▲");
                    } else {
                        CurrentStockPrice.setTextColor(Color.parseColor("#ff0000"));
                        CurrentStockPrice.setText(df.format(data)+ "(" + df.format(movement) + "%)" + "▼");
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
        stockPriceRequestQueue.add(stringRequest);
    }


}
