package com.cyllide.app.beta.contests;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyllide.app.beta.R;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MonthlyViewHolder extends RecyclerView.ViewHolder {

    private TextView monthlyName, monthlyCapacity, monthlyWinners, monthlyTimeRemaining, monthlyParticipantsRemaining;
    private String name, capacity, timeRemaining, winners, progress, participants;
    private LinearLayout monthlyPremium;
    private Boolean isPremium;
    private ProgressBar progressBar;

    private FragmentActivity weeklyActivity;

    View view;

    public MonthlyViewHolder(View itemView, FragmentActivity wa)
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
        weeklyActivity = wa;
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
            monthlyPremium.setBackgroundColor(ResourcesCompat.getColor(view.getResources(), R.color.primary_light,null));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyActivity.startActivity(new Intent(weeklyActivity, MonthlyActivity.class));
            }
        });
    }
}