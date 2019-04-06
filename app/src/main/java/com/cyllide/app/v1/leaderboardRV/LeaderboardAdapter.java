package com.cyllide.app.v1.leaderboardRV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.contests.PortfolioViewerDialogFragment;
import com.cyllide.app.v1.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
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


        public MyViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.portfolioname);
            returnTextView = view.findViewById(R.id.portfolioreturns);
            profilePicView = view.findViewById(R.id.leaderboard_profile_iv);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LeaderboardModel portfolio = leaderboardModelList.get(position);
        holder.nameTextView.setText(portfolio.getName());
        holder.returnTextView.setText(Double.toString(portfolio.getReturns()));
        Glide.with(context)
                .load(portfolio.getProfileURL())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.profilePicView);
        cv = holder.itemView.findViewById(R.id.leaderboard_card_view);
//        cv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        if(position%2==1){
           holder.itemView.findViewById(R.id.leaderboard_card_view).
                   setBackgroundResource(R.color.lightgray);
            //changes the alternate element colour
        }
    }

    @Override
    public int getItemCount() {
        return leaderboardModelList.size();
    }
}
