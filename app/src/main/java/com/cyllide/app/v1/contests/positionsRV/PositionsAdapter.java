package com.cyllide.app.v1.contests.positionsRV;

import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;

import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class PositionsAdapter extends RecyclerView.Adapter<PositionsAdapter.MyViewHolder> {

    CardView cv;
    Context context;
    RequestQueue requestQueue;
    Map<String,String> requestHeaders = new ArrayMap<>();

    private List<Positions2> positions2List;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pName, pReturns, pValue;

        public MyViewHolder(View view) {
            super(view);
            pName = view.findViewById(R.id.position_name);
            pReturns = view.findViewById(R.id.position_returns);
            pValue = view.findViewById(R.id.position_value);
            context= view.getContext();
        }
    }


    public PositionsAdapter(List<Positions2> pflioList) {
        this.positions2List = pflioList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_positions_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.pName.setText(positions2List.get(position).getTicker());
        holder.pValue.setText(positions2List.get(position).getQuantity()+"");
        holder.pReturns.setText(positions2List.get(position).getEntryPrice()+"");
    }

    @Override
    public int getItemCount() {
        return positions2List.size();
    }

}