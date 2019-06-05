package com.cyllide.app.v1.portfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.R;
import com.cyllide.app.v1.leaderboardRV.LeaderboardAdapter;
import com.cyllide.app.v1.leaderboardRV.LeaderboardModel;

import java.util.List;

public class PortfolioGameLeaderboardRVAdapter extends RecyclerView.Adapter<PortfolioGameLeaderboardRVViewHolder> {

    Context context;
    private List<PortfolioGameLeaderboardRVModel> leaderboardModelList;

    public PortfolioGameLeaderboardRVAdapter(Context context, List<PortfolioGameLeaderboardRVModel> leaderboardModelList) {
        this.context = context;
        this.leaderboardModelList = leaderboardModelList;
    }


    @NonNull
    @Override
    public PortfolioGameLeaderboardRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_portfolio_game_leaderboard_rv, parent, false);

        return new PortfolioGameLeaderboardRVViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PortfolioGameLeaderboardRVViewHolder holder, int position) {
        holder.populate(leaderboardModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return leaderboardModelList.size();
    }
}
