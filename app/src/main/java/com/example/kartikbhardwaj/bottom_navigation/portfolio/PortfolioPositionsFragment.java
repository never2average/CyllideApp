package com.example.kartikbhardwaj.bottom_navigation.portfolio;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.AppConstants;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.PendingOrdersRV.OrdersAdapter;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.PendingOrdersRV.OrdersModel;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.PortfolioPositionsRV.BalanceClass;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.PortfolioPositionsRV.CurrentPositions;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.PortfolioPositionsRV.PositionsAdapter;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.PortfolioPositionsRV.PositionsModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PortfolioPositionsFragment extends Fragment {

    private RecyclerView positionsRV, pendingOrdersRV;
    RequestQueue pendingOrderQueue;
    RequestQueue holdingPositionsQueue;
    Map<String, String> pendingOrderRequestHeader = new ArrayMap<>();
    Map<String, String> holdingPositionRequestHeader = new ArrayMap<>();


    private List<PositionsModel> portfolioPositionsData(){
        List<PositionsModel> data= new ArrayList<>();
        for(int i = 0; i<CurrentPositions.tickerName.size(); i++){
            if(System.currentTimeMillis()/1000L - CurrentPositions.tickerEntryTime.get(i) >= 300){
                data.add(new PositionsModel(CurrentPositions.tickerName.get(i),CurrentPositions.tickerQuantity.get(i),String.valueOf(1234.23),CurrentPositions.tickerPositionType.get(i),CurrentPositions.tickerQuantity.get(i)*1234.23));
                BalanceClass.balance-=(CurrentPositions.tickerQuantity.get(i)*1234.23);
            }
        }
        return data;
    }
    private List<OrdersModel> pendingOrdersData() {
        List<OrdersModel> data = new ArrayList<>(12);
        for (int i = 0; i < CurrentPositions.tickerName.size(); i++) {
            if(System.currentTimeMillis()/1000L - CurrentPositions.tickerEntryTime.get(i) < 300){
                data.add(new OrdersModel(CurrentPositions.tickerPositionType.get(i),CurrentPositions.tickerQuantity.get(i),CurrentPositions.tickerName.get(i),String.valueOf(1234.23)));
            }
        }
        return data;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.portfolio_positions_fragment,null);
        positionsRV = rootView.findViewById(R.id.positions_rv);
        positionsRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        pendingOrdersRV = rootView.findViewById(R.id.pending_Orders_RV);
        pendingOrdersRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<PositionsModel> data1 = portfolioPositionsData();
        PositionsAdapter positionsAdapter= new PositionsAdapter(data1);
        positionsRV.setAdapter(positionsAdapter);

        List<OrdersModel> ordersModels = pendingOrdersData();
        OrdersAdapter ordersAdapter = new OrdersAdapter(ordersModels);
        pendingOrdersRV.setAdapter(ordersAdapter);
        getPendingOrders(getContext());
        getHoldingPositions(getContext());

        return rootView;
    }

    private void getPendingOrders(Context context){
        String url = getResources().getString(R.string.apiBaseURL)+"portfolios/positionlist";
        pendingOrderQueue = Volley.newRequestQueue(context);
        pendingOrderRequestHeader.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        pendingOrderRequestHeader.put("portfolioID", AppConstants.portfolioID);
        pendingOrderRequestHeader.put("posType","Pending");
        Log.d("resp",AppConstants.portfolioID);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("resp",response);
                    JSONArray responseData = new JSONObject(response).getJSONArray("message");
                    //TODO SET STUFF

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Question Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return pendingOrderRequestHeader;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        pendingOrderQueue.add(stringRequest);

    }


    private void getHoldingPositions(Context context){
        String url = getResources().getString(R.string.apiBaseURL)+"portfolios/positionlist";
        holdingPositionsQueue = Volley.newRequestQueue(context);
        holdingPositionRequestHeader.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        holdingPositionRequestHeader.put("portfolioID", AppConstants.portfolioID);
        holdingPositionRequestHeader.put("posType","Holding");
        Log.d("resp",AppConstants.portfolioID);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("resp",response);
                    JSONArray responseData = new JSONObject(response).getJSONArray("message");
                    //TODO SET STUFF

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Question Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return holdingPositionRequestHeader;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        holdingPositionsQueue.add(stringRequest);

    }


}

