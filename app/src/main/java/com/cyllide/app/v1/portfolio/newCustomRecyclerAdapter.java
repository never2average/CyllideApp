package com.cyllide.app.v1.portfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.R;

import java.util.List;

public class newCustomRecyclerAdapter extends
        RecyclerView.Adapter<newCustomRecyclerAdapter.ViewHolder> {
    private List<String> lol;

    public newCustomRecyclerAdapter(List<String> lol) {
        this.lol = lol;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.game_card_nifty50, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return lol.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);


        }
    }
}