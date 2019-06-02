package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;

public class PortfolioGamePortfolioActivity extends AppCompatActivity {
    ImageView tab1, tab2, tab3;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_game_portfolio);
        tab1 = findViewById(R.id.pg_portfolio_activity_1);
        tab2 = findViewById(R.id.pg_portfolio_activity_2);
        tab3 = findViewById(R.id.pg_portfolio_activity_3);
        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGamePortfolioActivity.this, PortfolioGameHomeActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGamePortfolioActivity.this, PortfolioGameLeaderboardActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        backBtn = findViewById(R.id.portfolio_game_portfolio_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGamePortfolioActivity.this, PortfolioGameHomeActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
    }
}
