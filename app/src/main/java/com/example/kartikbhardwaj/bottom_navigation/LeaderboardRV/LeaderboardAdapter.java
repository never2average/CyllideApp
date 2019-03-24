package com.example.kartikbhardwaj.bottom_navigation.LeaderboardRV;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioViewerDialogFragment;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    private static final int width = 50, height = 50;//width and height params for profile pic
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
            cv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view){
                    DialogFragment dialogFragment = new PortfolioViewerDialogFragment();
                    dialogFragment.show(fragmentManager, "PortfolioViewer");
            }
            });

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
        LeaderboardModel pflio = leaderboardModelList.get(position);
        holder.nameTextView.setText(pflio.getName());
        holder.returnTextView.setText(Double.toString(pflio.getReturns()));
        Glide.with(context)
                .load(R.drawable.stock_img_man)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.bulb))
                .into(holder.profilePicView);
        if(position%2==0){
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
