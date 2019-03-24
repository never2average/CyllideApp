package com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.kartikbhardwaj.bottom_navigation.LeaderboardRV.LeaderboardAdapter;
import com.example.kartikbhardwaj.bottom_navigation.LeaderboardRV.LeaderboardModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardsActivity extends AppCompatActivity {
    RecyclerView leaderboardView;

    List<LeaderboardModel> getDummyData() {
        List<LeaderboardModel> data = new ArrayList<>(5);
        for (int i = 0; i <= 5; i++) {
            LeaderboardModel a = new LeaderboardModel("Player " + Integer.toString(i), i, Float.parseFloat(Integer.toString(i)));
            data.add(a);
        }
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);
        leaderboardView = findViewById(R.id.leaderboard_recycler_view);
        RecyclerView.LayoutManager leaderboardLayoutManager = new LinearLayoutManager(this);
        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(getDummyData());
        leaderboardView.setLayoutManager(leaderboardLayoutManager);
        leaderboardView.setAdapter(leaderboardAdapter);


        loadDummyData();

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

}
