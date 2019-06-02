package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;

public class PortfolioGameLeaderboardActivity extends AppCompatActivity {
    ImageView tab1, tab2, tab3, backBtn;

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

    }
}
