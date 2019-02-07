package com.example.kartikbhardwaj.bottom_navigation.LeaderboardRV;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    CardView cv;
    Context context;

    private List<LeaderboardModel> leaderboardModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView, rankTextView,returnTextView;

        public MyViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.portfolioname);
            rankTextView = (TextView) view.findViewById(R.id.rank);
            returnTextView = view.findViewById(R.id.portfolioreturns);
            cv= view.findViewById(R.id.leaderboard_card_view);
            context= view.getContext();
            cv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    Toast.makeText(context,"What next?",Toast.LENGTH_LONG).show();
            }
            });

        }

    }


    public LeaderboardAdapter(List<LeaderboardModel> leaderboardList) {
        this.leaderboardModelList = leaderboardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contest_leaderboard_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LeaderboardModel pflio = leaderboardModelList.get(position);
        holder.nameTextView.setText(pflio.getName());
        holder.returnTextView.setText(Double.toString(pflio.getReturns()));
        holder.rankTextView.setText(Integer.toString(pflio.getRank()));
        if(position%2==0){
           holder.itemView.findViewById(R.id.leaderboard_card_view).setBackgroundResource(R.color.contest_grey);
            //changes the alternate element colour
        }
    }

    @Override
    public int getItemCount() {
        return leaderboardModelList.size();
    }
}
