package com.cyllide.app.v1.contests.portfolioRV;

import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
import com.google.android.material.card.MaterialCardView;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PortfolioViewHolder extends RecyclerView.ViewHolder {

    View view;
    TextView portfolioName;
    MaterialCardView portfolioCard;
    private RequestQueue requestQueue;
    Map<String,String> registrationHeaders = new ArrayMap<>();

    public PortfolioViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        portfolioName = itemView.findViewById(R.id.pflioname);
        portfolioCard = itemView.findViewById(R.id.popup_portfolio_card_view);

    }

    public void populate(final PortfolioModel portfolioModel, final Context context) {
        portfolioName.setText(portfolioModel.getPortfolioName());
        portfolioCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPortfolio(portfolioModel.getId(),context);
            }
        });
    }

    void registerPortfolio(String id, final Context context) {
        requestQueue = Volley.newRequestQueue(context);
        registrationHeaders.put("token",AppConstants.token);
        registrationHeaders.put("contestUID", AppConstants.contestID);
        registrationHeaders.put("portfolioUID",id);
        String url = context.getResources().getString(R.string.apiBaseURL)+"contest/enroll/portfolio";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("RegisterPortfolio",response);
                Intent intent = new Intent(context,LeaderboardsActivity.class);
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return registrationHeaders;
            }
        };
        requestQueue.add(stringRequest);
    }
}
