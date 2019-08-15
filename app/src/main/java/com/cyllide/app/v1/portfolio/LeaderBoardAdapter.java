package com.cyllide.app.v1.portfolio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.R;
import com.cyllide.app.v1.leaderboardRV.LeaderboardAdapter;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardViewHolder> {
    ArrayList<LeaderBoardModel> data;


    public LeaderBoardAdapter(ArrayList<LeaderBoardModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LeaderBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leaderboard_rv_view, parent, false);

        return new LeaderBoardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardViewHolder holder, int position) {
        LeaderBoardModel item =data.get(position);
        holder.populate(item);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
