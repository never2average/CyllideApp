package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;

import java.util.ArrayList;
import java.util.List;

public class PortfolioGameLeaderboardActivity extends AppCompatActivity {
    ImageView tab1, tab2, tab3, backBtn, levelImage;
    TextView currentStreakDays, currentLevel, numberStreaks;
    RecyclerView leaderBoardRV;
    List<PortfolioGameLeaderboardRVModel> dummyData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_game_leaderboard);
        tab1 = findViewById(R.id.pg_leaderboard_activity_1);
        tab2 = findViewById(R.id.pg_leaderboard_activity_2);
        tab3 = findViewById(R.id.pg_leaderboard_activity_3);
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGameLeaderboardActivity.this,PortfolioGameHomeActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGameLeaderboardActivity.this,PortfolioGamePortfolioActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        backBtn = findViewById(R.id.portfolio_game_leaderboard_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGameLeaderboardActivity.this,PortfolioGameHomeActivity.class);
                startActivity(returnHome);
                finish();
            }
        });

        levelImage = findViewById(R.id.pg_leaderboard_current_level_img);
        currentStreakDays = findViewById(R.id.pg_leaderboard_current_streak);
        numberStreaks = findViewById(R.id.pg_leaderboard_num_streaks);
        currentLevel = findViewById(R.id.pg_leaderboard_current_level);
        leaderBoardRV = findViewById(R.id.pg_leaderboard_rv);
        leaderBoardRV.setLayoutManager(new LinearLayoutManager(this));
        for(int i=0;i<10;i++){
            dummyData.add(new PortfolioGameLeaderboardRVModel(
                    "Priyesh",
                    "https://google.com",
                    5
            ));
        }
        leaderBoardRV.setAdapter(new PortfolioGameLeaderboardRVAdapter(getBaseContext(), dummyData));

    }
}
