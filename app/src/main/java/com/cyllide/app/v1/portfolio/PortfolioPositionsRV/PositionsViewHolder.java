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
import com.cyllide.app.v1.portfolio.PortfolioPositionsFragment;
import com.google.android.material.card.MaterialCardView;

import android.app.Dialog;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class PositionsViewHolder extends RecyclerView.ViewHolder {

    TextView positionTickerTV;
    TextView positionQuantityTV,positionCurrPriceTV,positionTypeTV,positionValueTV;
    MaterialCardView positionsCV;
    Dialog dialog;
    RequestQueue requestQueue;
    public Map<String,String> exitHoldingPosition = new ArrayMap<>();



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

    }

    private void exitPositionVolley(PositionsModel positionsModel) {
        exitHoldingPosition.put("token",AppConstants.token);
        exitHoldingPosition.put("portfolioID",AppConstants.portfolioID);
        exitHoldingPosition.put("state","Holding");
        exitHoldingPosition.put("ticker",positionsModel.getPositionTicker());
        exitHoldingPosition.put("quantity",positionsModel.getPositionQuantity());
        exitHoldingPosition.put("isLong",positionsModel.getPositionType());

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


    public void populate(final PositionsModel item){
        positionValueTV.setText("₹ "+item.getPositionValue());
        positionTypeTV.setText(item.getPositionType());
        positionTickerTV.setText(item.getPositionTicker());
        positionCurrPriceTV.setText(item.getPositionCurrPrice());
        positionQuantityTV.setText("X"+item.getPositionQuantity());
        positionsCV.setOnClickListener(new View.OnClickListener() {
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
}
