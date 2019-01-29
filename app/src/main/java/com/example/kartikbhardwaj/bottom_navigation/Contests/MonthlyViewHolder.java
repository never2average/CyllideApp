package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MonthlyViewHolder extends RecyclerView.ViewHolder {

    private TextView monthlyName, monthlyCapacity, monthlyWinners, monthlyTimeRemaining, monthlyParticipantsRemaining;
    private String name, capacity, timeRemaining, winners, progress, participants;
    private LinearLayout monthlyPremium;
    private Boolean isPremium;
    private ProgressBar progressBar;

    View view;

    public MonthlyViewHolder(View itemView)
    {
        super(itemView);
        monthlyName = itemView.findViewById(R.id.monthly_name);
        monthlyCapacity = itemView.findViewById(R.id.monthly_capacity);
        monthlyTimeRemaining = itemView.findViewById(R.id.monthly_time_remaining);
        monthlyWinners = itemView.findViewById(R.id.monthly_no_of_winners);
        progressBar = itemView.findViewById(R.id.monthly_progress_bar);
        monthlyParticipantsRemaining = itemView.findViewById(R.id.monthly_participants_remaining);
        monthlyPremium = itemView.findViewById(R.id.monthly_color_strip);
        view = itemView;
    }

    public void populate(MonthlyModel news)
    {

        name = news.getMonthlyName();
        capacity = news.getMonthlyCapacity();
        timeRemaining = news.getMonthlyTimeRemaining();
        winners = news.getMonthlyWinners();
        progress = news.getMonthlyProgress();
        participants = news.getMonthlyParticipantsRemaining();
        isPremium = news.getPremium();

        monthlyName.setText(name);
        monthlyCapacity.setText(capacity);
        monthlyParticipantsRemaining.setText(participants);
        monthlyWinners.setText(winners);
        monthlyTimeRemaining.setText(timeRemaining);
        progressBar.setProgress(Integer.parseInt(progress));

        if(isPremium == false){
            monthlyPremium.setBackground(null);
            monthlyPremium.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), R.color.contest_grey,null));
        }
    }
}