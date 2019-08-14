package com.cyllide.app.v1;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cyllide.app.v1.portfolio.PortfolioGameLeaderboardActivity;
import com.cyllide.app.v1.portfolio.PortfolioGameLeaderboardRVAdapter;
import com.cyllide.app.v1.portfolio.PortfolioGameLeaderboardRVModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PortfolioGameLeaderboardFragment extends Fragment {
    Context context;
    TextView currentStreakDays, currentLevel, numberStreaks;
    RecyclerView leaderBoardRV;
    ImageView levelImage;
    List<PortfolioGameLeaderboardRVModel> leaderBoardData;
    private RequestQueue leaderBoardQueue;
    private Map<String,String> leaderBoardMap = new ArrayMap<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio_leaderboard, container, false);

        levelImage = view.findViewById(R.id.pg_leaderboard_current_level_img);
        currentStreakDays = view.findViewById(R.id.pg_leaderboard_current_streak);
        numberStreaks = view.findViewById(R.id.pg_leaderboard_num_streaks);
        currentLevel = view.findViewById(R.id.pg_leaderboard_current_level);
        leaderBoardRV = view.findViewById(R.id.pg_leaderboard_rv);
        leaderBoardRV.setLayoutManager(new LinearLayoutManager(context));
        populateLeaderBoard();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public PortfolioGameLeaderboardFragment(Context context) {
        this.context = context;
    }

    private void populateLeaderBoard() {
        String url = getResources().getString(R.string.apiBaseURL)+"contest/leaderboard";
        leaderBoardQueue = Volley.newRequestQueue(context);
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
                                    Glide.with(context)
                                            .load("https://www.pngrepo.com/download/87539/bronze-medal.png")
                                            .apply(requestOptionsBeg)
                                            .into(levelImage);
                                    break;
                                case "Professional":
                                    RequestOptions requestOptionsPro = new RequestOptions().override(140).circleCrop();
                                    Glide.with(context)
                                            .load("https://www.pngrepo.com/download/87539/bronze-medal.png")
                                            .apply(requestOptionsPro)
                                            .into(levelImage);
                                    break;
                                case "Masterclass":
                                    RequestOptions requestOptionsMaster = new RequestOptions().override(140).circleCrop();
                                    Glide.with(context)
                                            .load("https://www.pngrepo.com/download/87539/bronze-medal.png")
                                            .apply(requestOptionsMaster)
                                            .into(levelImage);
                                    break;
                            }
                        }
                    }
                    leaderBoardRV.setAdapter(new PortfolioGameLeaderboardRVAdapter(context, leaderBoardData));
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
