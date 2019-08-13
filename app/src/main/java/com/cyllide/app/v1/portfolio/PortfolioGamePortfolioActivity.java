package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import com.cyllide.app.v1.PositionsAdapter;
import com.cyllide.app.v1.R;
import com.cyllide.app.v1.portfolio.PortfolioPositionsRV.PositionsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PortfolioGamePortfolioActivity extends AppCompatActivity {
    ImageView tab1, tab2, tab3;
    RecyclerView positionsRV;
    private ImageView backBtn;
    Map<String, String> positionsHeader = new HashMap<>();
    RequestQueue positionsQueue;
//    ArrayList<PositionsModel = [;

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
        getPositionsLTPVolley();
//        getPositionsVolley();
    }

    private void getPositionsLTPVolley() {
        String url = getResources().getString(R.string.apiBaseURL)+"ohlc";
        Context context;
        RequestQueue positionsRequestQueue = Volley.newRequestQueue(PortfolioGamePortfolioActivity.this);
        StringRequest positionRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DONE", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    getPositionsVolley(jsonObject);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
        positionsRequestQueue.add(positionRequest);

    }

    ArrayList<PositionsModel> positionsModels = new ArrayList<>();

    void getPositionsVolley(final JSONObject ltp) {
        positionsHeader = new ArrayMap<>();
        String url = getResources().getString(R.string.apiBaseURL)+"portfolios/positionlist";
        positionsQueue = Volley.newRequestQueue(PortfolioGamePortfolioActivity.this);
        positionsHeader.put("token", AppConstants.token);
        final PositionsAdapter positionsAdapter = new PositionsAdapter(positionsModels);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(PortfolioGamePortfolioActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        positionsRV.setLayoutManager(layoutManager);

//        positionsRV.setLayoutManager(new LinearLayoutManager(PortfolioGamePortfolioActivity.this));

        StringRequest positionRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DONE", response);
                try {
                    positionsModels = new ArrayList<>();
                    JSONArray responseList = (new JSONObject(response).getJSONArray("data"));
                    for(int i = 0; i<responseList.length();i++){
                        PositionsModel pm = new PositionsModel();
                        pm.setPositionTicker(responseList.getJSONObject(i).getString("ticker"));
                        pm.setPositionQuantity(Integer.toString(responseList.getJSONObject(i).getInt("quantity")));
                        pm.setPositionCurrPrice(Double.toString(responseList.getJSONObject(i).getDouble("entryPrice")));
                        pm.setPositionltp(ltp.getString(responseList.getJSONObject(i).getString("ticker").toUpperCase()));
                        positionsModels.add(pm);


                    }
                    final PositionsAdapter positionsAdapter = new PositionsAdapter(positionsModels);
                    positionsRV.setAdapter(positionsAdapter);
                    positionsAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
