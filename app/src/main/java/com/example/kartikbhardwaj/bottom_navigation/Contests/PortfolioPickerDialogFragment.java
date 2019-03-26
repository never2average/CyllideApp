package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.AppConstants;
import com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV.PortfolioAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV.PortfolioModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PortfolioPickerDialogFragment extends DialogFragment {

    PortfolioPickerClickListener listener;
    private RequestQueue portfoliolist;
    Map<String,String> headers=new ArrayMap<>();


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (PortfolioPickerClickListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception

            throw new ClassCastException(context.toString()
                    + " must implement PortfolioPickerClickListener");
        }
    }

    public interface PortfolioPickerClickListener{
        public  void onItemClick(PortfolioModel portfolio);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_portfolio_picker, null);
        builder.setView(dialogLayout);
        final RecyclerView itemList = dialogLayout.findViewById(R.id.portfolios);
        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchRelevantPortfolios();
        dialogLayout.findViewById(R.id.close_btn_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }


    private void fetchRelevantPortfolios()
    {
        String url = "http://api.cyllide.com/api/client/contest/list/portfolios/rel";
        portfoliolist = Volley.newRequestQueue(getContext());

        headers.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        headers.put("capex", AppConstants.capex);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(new JSONObject(response).getString("data"));
                    Log.d("array",jsonArray.toString());
                } catch (JSONException e) {
                    Log.e("JSONERROR",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){

                return headers;

            }
        };
        portfoliolist.add(stringRequest);


    }
}
