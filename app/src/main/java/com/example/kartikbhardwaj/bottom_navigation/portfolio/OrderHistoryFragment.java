package com.example.kartikbhardwaj.bottom_navigation.portfolio;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.AppConstants;
import com.example.kartikbhardwaj.bottom_navigation.R;

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

import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class OrderHistoryFragment extends Fragment {

    private RecyclerView orderHistoryRV;
    private RequestQueue orderHistoryQueue;
    private Map<String,String> orderHistoryMap = new ArrayMap<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        orderHistoryRV = view.findViewById(R.id.order_history_rv);
        orderHistoryRV.setLayoutManager(new LinearLayoutManager(view.getContext()));
        orderHistoryRV.setHasFixedSize(true);
        getFinishedOrders(view.getContext());
        return view;
    }

    private void getFinishedOrders(Context context){
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
}
