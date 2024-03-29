package com.cyllide.app.beta;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.portfolio.PortfolioPositionsRV.PositionsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class PortfolioGamePositionsFragment extends Fragment {
    Context context;
    RecyclerView positionsRV;
    private ImageView backBtn;
    Map<String, String> positionsHeader = new HashMap<>();
    RequestQueue positionsQueue;
    TextView totalTV, noStocksToShowTV;
    GifImageView loading;
    double totalValue;

    public PortfolioGamePositionsFragment(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_positions, container, false);
        positionsRV = view.findViewById(R.id.portfoliopositionsrv);
        loading = view.findViewById(R.id.loading_screen);
        loading.setVisibility(View.VISIBLE);
        totalTV = view.findViewById(R.id.total_value);
        noStocksToShowTV = view.findViewById(R.id.no_stocks);
        setRetainInstance(false);

        return view;
    }

    private void getPositionsLTPVolley() {
        String url = getResources().getString(R.string.apiBaseURL)+"ohlc";
        RequestQueue positionsRequestQueue = Volley.newRequestQueue(context);
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
        String url = context.getResources().getString(R.string.apiBaseURL)+"portfolios/positionlist";
        positionsQueue = Volley.newRequestQueue(context);
        positionsHeader.put("token", AppConstants.token);
        final PositionsAdapter positionsAdapter = new PositionsAdapter(positionsModels);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        positionsRV.setLayoutManager(layoutManager);


        StringRequest positionRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DONE", response);
                try {
                    positionsModels = new ArrayList<>();
                    totalValue = 0;
                    JSONArray responseList = (new JSONObject(response).getJSONArray("data"));
                    for(int i = 0; i<responseList.length();i++){
                        PositionsModel pm = new PositionsModel();
                        pm.setPositionTicker(responseList.getJSONObject(i).getString("ticker"));
                        pm.setPositionQuantity(Integer.toString(responseList.getJSONObject(i).getInt("quantity")));
                        double positionValue = Double.parseDouble(ltp.getString(responseList.getJSONObject(i).getString("ticker").toUpperCase())) - responseList.getJSONObject(i).getDouble("entryPrice") ;
                        positionValue  *= responseList.getJSONObject(i).getInt("quantity");
                        totalValue+=positionValue;
                        pm.setPositionCurrPrice(Double.toString(responseList.getJSONObject(i).getDouble("entryPrice")));
                        pm.setPositionltp(ltp.getString(responseList.getJSONObject(i).getString("ticker").toUpperCase()));
                        pm.setPositionValue(Double.toString(positionValue));
                        positionsModels.add(pm);


                    }
                    final PositionsAdapter positionsAdapter = new PositionsAdapter(positionsModels);
                    positionsRV.setAdapter(positionsAdapter);
                    positionsAdapter.notifyDataSetChanged();
                    loading.setVisibility(View.GONE);
                    totalTV.setText(String.format("%.2f",totalValue));
                    totalTV.setTextColor(ContextCompat.getColor(context,R.color.red));
                    if(positionsModels.size()==0){
                        noStocksToShowTV.setVisibility(View.VISIBLE);
                    }
                    else{
                        noStocksToShowTV.setVisibility(View.GONE);
                    }

                    if(totalValue>0){
                        totalTV.setTextColor(ContextCompat.getColor(context,R.color.green));
                    }

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
    public void onResume() {
        super.onResume();
        Log.d("HEAAARRR","RESUMMEDDD");
        getPositionsLTPVolley();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Log.d("HEAAARRR","RESUMMEDDD");

            getPositionsLTPVolley();        }
    }
}
