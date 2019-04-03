package com.cyllide.app.v1.portfolio;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
import com.cyllide.app.v1.portfolio.OrderHistoryRV.OrderHistoryAdapter;
import com.cyllide.app.v1.portfolio.OrderHistoryRV.OrderHistoryModel;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class OrderHistoryFragment extends Fragment {

    private RecyclerView orderHistoryRV;
    private RequestQueue orderHistoryQueue;
    private Map<String,String> orderHistoryMap = new ArrayMap<>();
    List orderHistoryList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        orderHistoryRV = view.findViewById(R.id.order_history_rv);
        orderHistoryRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        orderHistoryRV.setHasFixedSize(true);
        getFinishedOrders(view.getContext(),orderHistoryRV);
        return view;
    }

    private void getFinishedOrders(Context context, final RecyclerView orderHistoryRV){
        String url = getResources().getString(R.string.apiBaseURL)+"portfolios/positionlist";
        orderHistoryQueue = Volley.newRequestQueue(context);
        orderHistoryMap.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        orderHistoryMap.put("portfolioID", AppConstants.portfolioID);
        orderHistoryMap.put("posType","Closed");
        Log.d("resp",AppConstants.portfolioID);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("resp",response);
                    JSONArray responseData = new JSONObject(response).getJSONArray("data");
                    populateOrderHistoryRV(responseData,orderHistoryRV);

                    //TODO SET STUFF

                } catch (JSONException e) {
                    Log.d("ERROR",e.toString());
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
                return orderHistoryMap;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        orderHistoryQueue.add(stringRequest);

    }

    private void populateOrderHistoryRV(JSONArray jsonArray, RecyclerView recyclerView) {
        orderHistoryList = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                orderHistoryList.add(
                        new OrderHistoryModel(
                                jsonArray.getJSONObject(i).getString("ticker"),
                                Boolean.parseBoolean(jsonArray.getJSONObject(i).getString("longPosition")),
                                jsonArray.getJSONObject(i).getJSONObject("exitTime").getLong("$date"),
                                jsonArray.getJSONObject(i).getDouble("entryPrice"),
                                jsonArray.getJSONObject(i).getDouble("exitPrice"),
                                jsonArray.getJSONObject(i).getInt("quantity")


                        )
                );
                Log.d("portfolioHistory",jsonArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
         OrderHistoryAdapter myOrderHistoryAdapter = new OrderHistoryAdapter(orderHistoryList);
        recyclerView.setAdapter(myOrderHistoryAdapter);
    }
}
