package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.google.android.material.card.MaterialCardView;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;
import java.util.List;


public class PortfolioGameHomeActivity extends AppCompatActivity {

    CardStackView cardStack;
    List<String> testData;
    ImageView backBtn;
    PortfolioGameCardAdapter adapter;
    ImageView tab1, tab2, tab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfolio_game_home_activity);
        tab1 = findViewById(R.id.pg_home_activity_1);
        tab2 = findViewById(R.id.pg_home_activity_2);
        tab3 = findViewById(R.id.pg_home_activity_3);
        cardStack = findViewById(R.id.swipe_deck);
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGameHomeActivity.this,PortfolioGamePortfolioActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGameHomeActivity.this,PortfolioGameLeaderboardActivity.class);
                startActivity(returnHome);
                finish();
            }
        });

        testData = new ArrayList<>();
        for(int i=0;i<5; i++){
            testData.add(String.valueOf(i));
        }
/**
     adapter = new PortfolioGameCardAdapter(testData, PortfolioGameHomeActivity.this);
        if(cardStack != null){
            cardStack.setAdapter(adapter);
        }


        cardStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void onViewSwipedToRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void onStackEmpty() {

            }
        });



**/

        MaterialCardView dontChooseStockBtn = findViewById(R.id.portfolio_game_cross);
        dontChooseStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cardStack.swipeTopViewToLeft();
                cardStack.swipe();
            }
        });
        MaterialCardView chooseStockDoubleQuantity = findViewById(R.id.portfolio_game_diamond);
        chooseStockDoubleQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cardStack.swipeTopViewToRight();
            }
        });

        MaterialCardView chooseStockBtn = findViewById(R.id.portfolio_game_heart);
        chooseStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapter.notifyDataSetChanged();
//                cardStack.swipeTopCardRight(300);
            }
        });
        backBtn = findViewById(R.id.portfolio_game_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGameHomeActivity.this,MainActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        ArrayList<String> lol = new ArrayList<>(4);
        cardStack.setLayoutManager( new CardStackLayoutManager(this));
        cardStack.setAdapter(new CardSwipeRecyclerAdapter(testData,this));
    }

    @Override
    public void onBackPressed(){
        Intent returnHome = new Intent(this,MainActivity.class);
        startActivity(returnHome);
        finish();
    }

}
