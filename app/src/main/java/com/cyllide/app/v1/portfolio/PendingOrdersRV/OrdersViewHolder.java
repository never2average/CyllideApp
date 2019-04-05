package com.cyllide.app.v1.portfolio.PendingOrdersRV;

import android.app.Dialog;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class OrdersViewHolder extends RecyclerView.ViewHolder {
    TextView PositionType;
    TextView Quantity;
    TextView CurrentStockPrice;
    TextView StockTicker;
    Dialog dialog;
    MaterialCardView ordersCV;


    public OrdersViewHolder(@NonNull View itemView) {
        super(itemView);

        PositionType=itemView.findViewById(R.id.position_type_tv);
        Quantity=itemView.findViewById(R.id.quantity_tv);
        CurrentStockPrice=itemView.findViewById(R.id.current_price_tv);
        StockTicker=itemView.findViewById(R.id.stock_ticker_tv);
        dialog = new Dialog(itemView.getContext());
        dialog.setContentView(R.layout.portfolio_conformation_dialog);
        ordersCV = itemView.findViewById(R.id.orders_card);

        ordersCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();


            }
        });

        Button buttonYes = dialog.findViewById(R.id.portfolio_dislog_yes_button);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button buttonNo = dialog.findViewById(R.id.portfolio_dislog_no_button);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public void populate(OrdersModel item){
        PositionType.setText(item.getPositionType());
        Quantity.setText(item.getQuantity());
        StockTicker.setText(item.getStockTicker());
        CurrentStockPrice.setText(item.getCurrentStockPrice());


    }

    RequestQueue deletePendingOrderQueue;
    Map<String, String> deletePendingOrderRequestHeader = new ArrayMap<>();

    private void deletePendingOrders(Context context, final RecyclerView pendingOrdersRV){
        String url = context.getResources().getString(R.string.apiBaseURL)+"portfolios/positionlist";
        deletePendingOrderQueue = Volley.newRequestQueue(context);
        deletePendingOrderRequestHeader.put("token", AppConstants.token);
        deletePendingOrderRequestHeader.put("portfolioID", AppConstants.portfolioID);
        deletePendingOrderRequestHeader.put("posType","Pending");
        Log.d("resp",AppConstants.portfolioID);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Question Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return deletePendingOrderRequestHeader;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        deletePendingOrderQueue.add(stringRequest);

    }

}
