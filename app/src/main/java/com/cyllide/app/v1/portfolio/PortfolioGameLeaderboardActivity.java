package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PortfolioGameLeaderboardActivity extends AppCompatActivity {
    ImageView tab1, tab2, tab3, backBtn, levelImage;
    TextView currentStreakDays, currentLevel, numberStreaks;
    RecyclerView leaderBoardRV;
    List<PortfolioGameLeaderboardRVModel> leaderBoardData;
    private RequestQueue leaderBoardQueue;
    private Map<String,String> leaderBoardMap = new ArrayMap<>();

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
        populateLeaderBoard();

    }

    private void populateLeaderBoard() {
        String url = getResources().getString(R.string.apiBaseURL)+"contest/leaderboard";
        leaderBoardQueue = Volley.newRequestQueue(PortfolioGameLeaderboardActivity.this);
        leaderBoardMap.put("token", AppConstants.token);
        StringRequest leaderBoardRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("LBRESP", response);
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("leaderboard");
                    leaderBoardData = new ArrayList<>();
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        leaderBoardData.add(
                                new PortfolioGameLeaderboardRVModel(
                                        jsonObject.getString("userName"),
                                        jsonObject.getString("profilePic"),
                                        jsonObject.getInt("numStreaks"),
                                        jsonObject.getJSONObject("_id").getString("$oid")
                                )
                        );
                        if(jsonObject.getBoolean("isTrue")){
                            currentStreakDays.setText(jsonObject.getInt("numStreaks")+"");
                            numberStreaks.setText(jsonObject.getInt("numDaysCurrentStreak")+"");
                            currentLevel.setText(jsonObject.getString("userLevel"));
                            switch (jsonObject.getString("userLevel")){
                                case "Beginner":
                                    RequestOptions requestOptionsBeg = new RequestOptions().override(140).circleCrop();
                                    Glide.with(PortfolioGameLeaderboardActivity.this)
                                            .load("https://www.pngrepo.com/download/87539/bronze-medal.png")
                                            .apply(requestOptionsBeg)
                                            .into(levelImage);
                                    break;
                                case "Professional":
                                    RequestOptions requestOptionsPro = new RequestOptions().override(140).circleCrop();
                                    Glide.with(PortfolioGameLeaderboardActivity.this)
                                            .load("https://www.pngrepo.com/download/87539/bronze-medal.png")
                                            .apply(requestOptionsPro)
                                            .into(levelImage);
                                    break;
                                case "Masterclass":
                                    RequestOptions requestOptionsMaster = new RequestOptions().override(140).circleCrop();
                                    Glide.with(PortfolioGameLeaderboardActivity.this)
                                            .load("https://www.pngrepo.com/download/87539/bronze-medal.png")
                                            .apply(requestOptionsMaster)
                                            .into(levelImage);
                                    break;
                            }
                        }
                    }
                    leaderBoardRV.setAdapter(new PortfolioGameLeaderboardRVAdapter(getBaseContext(), leaderBoardData));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PGERROR", error.toString());
            }
        }){
            @Override
            public Map<String,String> getHeaders(){return leaderBoardMap;}
        };
        leaderBoardQueue.add(leaderBoardRequest);
    }
}
