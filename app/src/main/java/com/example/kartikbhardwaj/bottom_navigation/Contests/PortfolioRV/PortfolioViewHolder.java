package com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV;

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
import com.example.kartikbhardwaj.bottom_navigation.AppConstants;
import com.example.kartikbhardwaj.bottom_navigation.R;
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
        registrationHeaders.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        registrationHeaders.put("contestUID", AppConstants.contestID);
        registrationHeaders.put("portfolioUID",id);
        String url = "http://api.cyllide.com/api/client/contest/enroll/portfolio";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("error",response);
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
