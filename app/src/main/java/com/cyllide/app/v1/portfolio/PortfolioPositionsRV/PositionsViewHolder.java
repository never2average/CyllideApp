package com.cyllide.app.v1.portfolio.PortfolioPositionsRV;

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

import android.app.Dialog;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class PositionsViewHolder extends RecyclerView.ViewHolder {

    TextView positionTickerTV;
    TextView positionQuantityTV,positionCurrPriceTV,positionTypeTV,positionValueTV;
    MaterialCardView positionsCV;
    Dialog dialog;



    public PositionsViewHolder(@NonNull View itemView) {
        super(itemView);
        positionCurrPriceTV=itemView.findViewById(R.id.tv_pos_curr_price);
        positionQuantityTV=itemView.findViewById(R.id.tv_pos_quantity);
        positionTickerTV=itemView.findViewById(R.id.tv_pos_ticker);
        positionTypeTV=itemView.findViewById(R.id.pos_type);
        positionValueTV=itemView.findViewById(R.id.pos_total_amt);
        positionsCV = itemView.findViewById(R.id.position_cv);
        dialog = new Dialog(itemView.getContext());
        dialog.setContentView(R.layout.portfolio_conformation_dialog);

        positionsCV.setOnClickListener(new View.OnClickListener() {
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

    RequestQueue deleteHoldingPositionsQueue;
    Map<String, String> deleteHoldingPositionRequestHeader = new ArrayMap<>();

    private void deleteHoldingPositions(Context context, final RecyclerView recyclerView){
        String url = context.getResources().getString(R.string.apiBaseURL)+"portfolios/positionlist";
        deleteHoldingPositionsQueue = Volley.newRequestQueue(context);
        deleteHoldingPositionRequestHeader.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        deleteHoldingPositionRequestHeader.put("portfolioID", AppConstants.portfolioID);
        deleteHoldingPositionRequestHeader.put("posType","Holding");
        Log.d("resp",AppConstants.portfolioID);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,  new Response.Listener<String>() {
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
                return deleteHoldingPositionRequestHeader;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        deleteHoldingPositionsQueue.add(stringRequest);

    }

    public void populate(PositionsModel item){
        positionValueTV.setText("₹ "+item.getPositionValue());
        positionTypeTV.setText(item.getPositionType());
        positionTickerTV.setText(item.getPositionTicker());
        positionCurrPriceTV.setText(item.getPositionCurrPrice());
        positionQuantityTV.setText("X"+item.getPositionQuantity());

    }
}
