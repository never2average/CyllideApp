package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

public class WeeklyViewHolder extends RecyclerView.ViewHolder {

    private TextView weeklyName, weeklyCapacity, weeklyWinners, weeklyTimeRemaining, weeklyParticipantsRemaining;
    private String name, capacity, timeRemaining, winners, progress, participants;
    private ProgressBar progressBar;
    private LinearLayout weeklyPremium;
    private Boolean isPremium;
    View view;
    Dialog portfolioSelectionPopup;
    private MaterialCardView cv;

    public WeeklyViewHolder(View itemView)
    {
        super(itemView);
        weeklyName = itemView.findViewById(R.id.weekly_name);
        weeklyCapacity = itemView.findViewById(R.id.weekly_capacity);
        weeklyTimeRemaining = itemView.findViewById(R.id.weekly_time_remaining);
        weeklyWinners = itemView.findViewById(R.id.weekly_no_of_winners);
        progressBar = itemView.findViewById(R.id.weekly_progress_bar);
        weeklyParticipantsRemaining = itemView.findViewById(R.id.weekly_participants_remaining);
        weeklyPremium = itemView.findViewById(R.id.weekly_color_strip);
        view = itemView;
        cv = itemView.findViewById(R.id.contest_card_weekly);
        portfolioSelectionPopup= new Dialog(itemView.getContext());

    }

    public void populate(WeeklyModel news)
    {
        name = news.getweeklyName();
        capacity = news.getweeklyCapacity();
        timeRemaining = news.getweeklyTimeRemaining();
        winners = news.getweeklyWinners();
        progress = news.getweeklyProgress();
        participants = news.getweeklyParticipantsRemaining();
        isPremium = news.getPremium();


        weeklyName.setText(name);
        weeklyCapacity.setText(capacity);
        weeklyParticipantsRemaining.setText(participants);
        weeklyWinners.setText(winners);
        weeklyTimeRemaining.setText(timeRemaining);
        progressBar.setProgress(Integer.parseInt(progress));

        if(isPremium == false){
            weeklyPremium.setBackground(null);
            weeklyPremium.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), R.color.primary_light,null));
        }
    cv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(),"Aagaya",Toast.LENGTH_LONG).show();
            portfolioSelectionPopup.setContentView(R.layout.portfolio_popup);
            portfolioSelectionPopup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            portfolioSelectionPopup.show();

        }
    });
    }

}
