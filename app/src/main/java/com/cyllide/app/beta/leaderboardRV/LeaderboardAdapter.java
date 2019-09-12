package com.cyllide.app.beta.leaderboardRV;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.ProfileActivity;
import com.cyllide.app.beta.contests.PortfolioViewerDialogFragment;
import com.cyllide.app.beta.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    private static final int width = 50, height = 50;
    CardView cv;
    Context context;
    FragmentManager fragmentManager;

    private List<LeaderboardModel> leaderboardModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView,returnTextView;
        public ImageView profilePicView;
        public TextView rankTextView;


        public MyViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.portfolioname);
            returnTextView = view.findViewById(R.id.portfolioreturns);
            profilePicView = view.findViewById(R.id.leaderboard_profile_iv);
            rankTextView = view.findViewById(R.id.leaderboard_rv_rank);
            cv= view.findViewById(R.id.leaderboard_card_view);
            context= view.getContext();

        }

    }


    public LeaderboardAdapter(List<LeaderboardModel> leaderboardList, FragmentManager fragmentManager) {
        this.leaderboardModelList = leaderboardList;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_leaderboard_rv, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final LeaderboardModel portfolio = leaderboardModelList.get(position);
        holder.nameTextView.setText(portfolio.getName());

        if(portfolio.getReturns()<0){
            holder.returnTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.progressred));
        }
        else{
            holder.returnTextView.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.progressgreen));
        }
        holder.returnTextView.setText(Double.toString(portfolio.getReturns())+"%");
        holder.rankTextView.setText(Integer.toString(portfolio.getRank()));

        Glide.with(holder.itemView.getContext())
                .load(portfolio.getProfileURL())
                .apply(RequestOptions.overrideOf(100))
                .into(holder.profilePicView);
        cv = holder.itemView.findViewById(R.id.leaderboard_card_view);


        holder.profilePicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), ProfileActivity.class);
                AppConstants.viewUsername = portfolio.getPortfolioOwner();
                holder.itemView.getContext().startActivity(intent);
            }
        });
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.portfolioID = portfolio.getPortfolioID();
                DialogFragment dialog = new PortfolioViewerDialogFragment();
                dialog.show(((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager(), "PortfolioViewer");
            }
        });


        if(portfolio.isMine()){
            holder.itemView.findViewById(R.id.leaderboard_card_view).
                    setBackgroundResource(R.color.colorPrimary);
        }

    }

    @Override
    public int getItemCount() {
        return leaderboardModelList.size();
    }
}
