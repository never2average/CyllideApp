package com.cyllide.app.v1.contests;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.contests.positionsRV.Positions2;
import com.cyllide.app.v1.contests.positionsRV.PositionsAdapter;
import com.cyllide.app.v1.R;

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

public class PortfolioViewerDialogFragment extends DialogFragment {

    RequestQueue holdingPositionsQueue;
    TextView nothingToShowTV;
    Map<String, String> holdingPositionRequestHeader = new ArrayMap<>();
    RecyclerView itemList;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_portfolio_viewer, null);
        builder.setView(dialogLayout);
        itemList = dialogLayout.findViewById(R.id.positions);
        nothingToShowTV = dialogLayout.findViewById(R.id.dialog_portfolio_viewer_nothing_to_show);
        itemList.setLayoutManager(new LinearLayoutManager(getContext()));
        dialogLayout.findViewById(R.id.close_btn_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getHoldingPositions(dialogLayout.getContext());
        return builder.create();
    }

    private void getHoldingPositions(Context context){
        String url = getResources().getString(R.string.apiBaseURL)+"portfolios/positionlist";
        holdingPositionsQueue = Volley.newRequestQueue(context);
        holdingPositionRequestHeader.put("token", AppConstants.token);
        holdingPositionRequestHeader.put("portfolioID", AppConstants.portfolioID);
        holdingPositionRequestHeader.put("posType","Holding");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray responseData = new JSONObject(response).getJSONArray("data");
                    if(responseData.length() == 0){
                        itemList.setVisibility(View.INVISIBLE);
                        nothingToShowTV.setVisibility(View.VISIBLE);

                    }
                    else{
                        itemList.setVisibility(View.VISIBLE);
                        nothingToShowTV.setVisibility(View.INVISIBLE);

                    }

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
