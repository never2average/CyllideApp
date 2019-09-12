package com.cyllide.app.beta.contests;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyllide.app.beta.R;
import com.google.android.material.card.MaterialCardView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WeeklyViewHolder extends RecyclerView.ViewHolder {

    private TextView weeklyName, weeklyCapacity, weeklyWinners, weeklyTimeRemaining, weeklyParticipantsRemaining;
    private String name, capacity, timeRemaining, winners, progress, participants;
    private ProgressBar progressBar;
    private LinearLayout weeklyPremium;
    private Boolean isPremium;
    View view;
    Context context;
    Dialog portfolioSelectionPopup;
    private MaterialCardView cv;
    RecyclerView portfolioView;


//    private List<PortfolioModel> dummyData() {
//        List<PortfolioModel> data = new ArrayList<>(5);
//        for (int i = 0; i < 5; i++) {
//            data.add(new PortfolioModel("TEST_DAAATAAA", 0.0));
//        }//data is the list of objects to be set in the list item
//        return data;
//    }

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
        context=itemView.getContext();
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
            //portfolioSelectionPopup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//            Intent intent = new Intent(context,testPortfolioActivity.class);
//            context.startActivity(intent);
            //super.onCreate(savedInstanceState);
            portfolioSelectionPopup.setContentView(R.layout.activity_test_portfolio);
            portfolioView= portfolioSelectionPopup.findViewById(R.id.portfolios);
            portfolioView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(portfolioSelectionPopup.getContext());
            //List<PortfolioModel> data=dummyData();
            //data.add(new PortfolioModel("lkl",0.0));
            //PortfolioAdapter portfolioAdapter = new PortfolioAdapter(data);
           // portfolioView.setLayoutManager(layoutManager);
          //  portfolioView.setAdapter(portfolioAdapter);
            portfolioSelectionPopup.show();

//            portfolioSelectionPopup.setContentView(R.layout.portfolio_popup);
//            portfolioView= portfolioSelectionPopup.findViewById(R.id.portfolios);
//            portfolioView.setHasFixedSize(true);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(portfolioSelectionPopup.getContext(),RecyclerView.VERTICAL,false);
//            List<PortfolioModel>data=dummyData();
//            PortfolioAdapter portfolioAdapter = new PortfolioAdapter(data);
//            portfolioView.setLayoutManager(layoutManager);
//            portfolioView.setAdapter(portfolioAdapter);
//            portfolioSelectionPopup.show();
//            Log.e("Lame",data.get(0).getPortfolioname());




        }
    });
    }

}
