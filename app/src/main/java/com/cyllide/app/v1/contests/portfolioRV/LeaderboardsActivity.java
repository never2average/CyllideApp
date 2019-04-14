package com.cyllide.app.v1.contests.portfolioRV;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.contests.MonthlyActivity;
import com.cyllide.app.v1.leaderboardRV.LeaderboardAdapter;
import com.cyllide.app.v1.leaderboardRV.LeaderboardModel;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderboardsActivity extends AppCompatActivity {
    RecyclerView leaderboardView;
    RequestQueue leaderBRequestQ;
    TextView pos1,pos2,pos3;
    Map<String,String> leaderBrequestHdrs = new ArrayMap<>();
    TextView numPortfolios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        leaderboardView = findViewById(R.id.leaderboard_recycler_view);
        RecyclerView.LayoutManager leaderboardLayoutManager = new LinearLayoutManager(this);
        pos1 = findViewById(R.id.pos1_tv);
        pos2 = findViewById(R.id.pos2_tv);
        pos3 = findViewById(R.id.pos3_tv);
        leaderboardView.setLayoutManager(leaderboardLayoutManager);
        numPortfolios = findViewById(R.id.num_portfolios_tv);
        ImageView backButton = findViewById(R.id.leaderboard_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaderboardsActivity.this, MonthlyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getLeaderBoard();
    }

    private void getMyPortfolios(){

    }


    private void getLeaderBoard()
    {
        leaderBRequestQ= Volley.newRequestQueue(LeaderboardsActivity.this);
        leaderBrequestHdrs.put("token",AppConstants.token);
        leaderBrequestHdrs.put("contestUID", AppConstants.contestID);
        Log.d("Id",AppConstants.contestID);
        String url = getResources().getString(R.string.apiBaseURL)+"contest/leaderboard";
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("leaderBoardData",response);
                JSONArray arrayData=null;
                try {
                    arrayData = new JSONObject(response).getJSONArray("message");
                    numPortfolios.setText(arrayData.length()+"  Portfolios");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try{
                    ImageView imageView = findViewById(R.id.image1_leader_board);
                    pos1.setText(arrayData.getJSONObject(0).getString("portfolioOwner"));
                    Glide.with(LeaderboardsActivity.this).load(arrayData.getJSONObject(0).getString("portfolioProfilePic")).apply(RequestOptions.centerCropTransform()).into(imageView);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    ImageView imageView = findViewById(R.id.image2_leader_board);
                    pos2.setText(arrayData.getJSONObject(1).getString("portfolioOwner"));
                    Glide.with(LeaderboardsActivity.this).load(arrayData.getJSONObject(0).getString("portfolioProfilePic")).apply(RequestOptions.centerCropTransform()).into(imageView);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    ImageView imageView = findViewById(R.id.image3_leader_board);
                    pos3.setText(arrayData.getJSONObject(2).getString("portfolioOwner"));
                    Glide.with(LeaderboardsActivity.this).load(arrayData.getJSONObject(0).getString("portfolioProfilePic")).apply(RequestOptions.centerCropTransform()).into(imageView);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                try {
                List<LeaderboardModel> leaderboardModelArrayList = new ArrayList<>();
                for(int i = 0; i<arrayData.length();i++){
                        leaderboardModelArrayList.add(new LeaderboardModel(arrayData.getJSONObject(i).getString("portfolioName"),arrayData.getJSONObject(i).getInt("rank"),arrayData.getJSONObject(i).getDouble("returns"),arrayData.getJSONObject(i).getString("portfolioProfilePic"),arrayData.getJSONObject(i).getJSONObject("_id").getString("$oid"),arrayData.getJSONObject(i).getString("portfolioOwner"),arrayData.getJSONObject(i).getBoolean("myPortfolio")));
                }
                LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(leaderboardModelArrayList, getSupportFragmentManager());
                leaderboardView.setAdapter(leaderboardAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("leaderboard error",error.toString());
            }
        }){
            @Override
            public Map<String,String> getHeaders(){return leaderBrequestHdrs;}
        };
        leaderBRequestQ.add(stringRequest);
    }

}
