package com.example.kartikbhardwaj.bottom_navigation.contests.portfolioRV;

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
import com.example.kartikbhardwaj.bottom_navigation.AppConstants;
import com.example.kartikbhardwaj.bottom_navigation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LeaderboardsActivity extends AppCompatActivity {
    RecyclerView leaderboardView;
    RequestQueue leaderBRequestQ;
    Map<String,String> leaderBrequestHdrs=new ArrayMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);
        leaderboardView = findViewById(R.id.leaderboard_recycler_view);
        RecyclerView.LayoutManager leaderboardLayoutManager = new LinearLayoutManager(this);

        leaderboardView.setLayoutManager(leaderboardLayoutManager);

        ImageView backButton = findViewById(R.id.leaderboard_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getLeaderBoard();
    }


    private void loadDummyData() {

        int width  = 250;
        int height = 250;

        final TextView pos1 = findViewById(R.id.pos1_tv);
        final TextView pos2 = findViewById(R.id.pos2_tv);
        final TextView pos3 = findViewById(R.id.pos3_tv);

        //TODO: Change SimpleTarget to Target
        Glide.with(this)
                .load(R.drawable.stock_img_man)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.bulb))
                .into(new SimpleTarget<Drawable>(width,height) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        pos1.setCompoundDrawablesWithIntrinsicBounds(null, resource, null, null);
                    }
                });
        Glide.with(this)
                .load(R.drawable.stock_img_man)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.bulb))
                .into(new SimpleTarget<Drawable>(width,height) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        pos2.setCompoundDrawablesWithIntrinsicBounds(null, resource, null, null);
                    }
                });
        Glide.with(this)
                .load(R.drawable.stock_img_man)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.bulb))
                .into(new SimpleTarget<Drawable>(width,height) {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        pos3.setCompoundDrawablesWithIntrinsicBounds(null, resource, null, null);
                    }
                });
    }

    private void getLeaderBoard()
    {
        leaderBRequestQ= Volley.newRequestQueue(LeaderboardsActivity.this);
        leaderBrequestHdrs.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        leaderBrequestHdrs.put("contestUID", AppConstants.contestID);
        Log.d("Id",AppConstants.contestID);
        String url = "http://api.cyllide.com/api/client/contest/leaderboard";
        StringRequest stringRequest =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("leaderboarddata",response);
                try {
                    JSONArray arrayData=new JSONObject(response).getJSONArray("contestPortfolios");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override
            public Map<String,String> getHeaders(){return leaderBrequestHdrs;}
        };
        leaderBRequestQ.add(stringRequest);


    }

}
