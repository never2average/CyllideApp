package com.cyllide.app.beta.portfolio;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.R;

public class PortfolioGameLeaderboardRVViewHolder extends RecyclerView.ViewHolder {
    ImageView profilePic;
    TextView playerName, playerStreaks;
    public PortfolioGameLeaderboardRVViewHolder(@NonNull View itemView) {
        super(itemView);
        profilePic = itemView.findViewById(R.id.rv_player_profile);
        playerName = itemView.findViewById(R.id.rv_player_name);
        playerStreaks = itemView.findViewById(R.id.rv_streak_days);
    }

    public void populate(PortfolioGameLeaderboardRVModel leaderboardModel) {

        if(leaderboardModel.getPlayerProfileURL().equals(AppConstants.noProfilePicURL)){
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(leaderboardModel.getPlayerName());
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(80)  // width in px
                    .height(80) // height in px
                    .endConfig()
                    .buildRound(Character.toString(leaderboardModel.getPlayerName().charAt(0)).toUpperCase(), color);
            profilePic.setImageDrawable(drawable);
        }
        else {
            RequestOptions requestOptions = new RequestOptions().override(80).circleCrop();
            Glide.with(itemView.getContext()).load(leaderboardModel.getPlayerProfileURL()).apply(requestOptions).into(profilePic);
        }
        playerName.setText(leaderboardModel.getPlayerName());
        playerStreaks.setText(String.valueOf(leaderboardModel.getPlayerNumDaysStreak()));
    }
}
