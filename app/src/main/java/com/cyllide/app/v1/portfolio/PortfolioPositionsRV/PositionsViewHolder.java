package com.cyllide.app.v1.portfolio.PortfolioPositionsRV;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
import com.cyllide.app.v1.portfolio.PortfolioPositionsFragment;
import com.google.android.material.card.MaterialCardView;

import android.app.Dialog;
import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class PositionsViewHolder extends RecyclerView.ViewHolder {
    TextView quantityTV,nameTV,avgPrice,ltp, cost;

    public PositionsViewHolder(@NonNull View itemView) {
        super(itemView);
        quantityTV = itemView.findViewById(R.id.position_quantity);
        nameTV = itemView.findViewById(R.id.position_name);
        avgPrice = itemView.findViewById(R.id.position_average_price);
        ltp = itemView.findViewById(R.id.position_ltp);
        cost = itemView.findViewById(R.id.position_cost);
    }
    public void populate(PositionsModel positionsModel){
        quantityTV.setText(positionsModel.getPositionQuantity());
        nameTV.setText(positionsModel.getPositionTicker());
        ltp.setText(positionsModel.getPositionCurrPrice());
        cost.setText(positionsModel.getPositionCost());
    }
}
