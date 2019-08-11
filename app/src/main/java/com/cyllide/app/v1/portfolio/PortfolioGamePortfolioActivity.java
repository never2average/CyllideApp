package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;

import java.util.Map;

public class PortfolioGamePortfolioActivity extends AppCompatActivity {
    ImageView tab1, tab2, tab3;
    RecyclerView positionsRV;
    private ImageView backBtn;
    Map<String, String> positionsHeader;
    RequestQueue positionsQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_game_portfolio);
        tab1 = findViewById(R.id.pg_portfolio_activity_1);
        tab2 = findViewById(R.id.pg_portfolio_activity_2);
        tab3 = findViewById(R.id.pg_portfolio_activity_3);
        positionsRV = findViewById(R.id.portfoliopositionsrv);
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGamePortfolioActivity.this, PortfolioGameHomeActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGamePortfolioActivity.this, PortfolioGameLeaderboardActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        backBtn = findViewById(R.id.portfolio_game_portfolio_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGamePortfolioActivity.this, PortfolioGameHomeActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        getPositionsVolley();
    }

    void getPositionsVolley() {
        positionsHeader = new ArrayMap<>();
        String url = getResources().getString(R.string.apiBaseURL)+"portfolios/positionlist";
        positionsQueue = Volley.newRequestQueue(PortfolioGamePortfolioActivity.this);
        positionsHeader.put("token", AppConstants.token);
        StringRequest positionRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DONE", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                return positionsHeader;
            }
        };
        positionsQueue.add(positionRequest);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(PortfolioGamePortfolioActivity.this,"onNewIntentcalled",Toast.LENGTH_SHORT).show();
    }
}
