package com.example.kartikbhardwaj.bottom_navigation.Contests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.kartikbhardwaj.bottom_navigation.LeaderboardRV.LeaderboardAdapter;
import com.example.kartikbhardwaj.bottom_navigation.LeaderboardRV.LeaderboardModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.ArrayList;
import java.util.List;

public class ContestLeaderboard extends AppCompatActivity {

    RecyclerView leaderboardView;

    List<LeaderboardModel> getDummyData(){
        List<LeaderboardModel> data = new ArrayList<>(5);
        for(int i=0;i<=5;i++){
            LeaderboardModel a= new LeaderboardModel("Anshuman"+Integer.toString(i),i,Float.parseFloat(Integer.toString(i)));
            data.add(a);
        }
        return data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contest_leaderboard_free);
        leaderboardView = findViewById(R.id.leaderboard_recycler_view);
        RecyclerView.LayoutManager leaderboardLayoutManager = new LinearLayoutManager(this);
        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(getDummyData(),
                getSupportFragmentManager());
        leaderboardView.setLayoutManager(leaderboardLayoutManager);
        leaderboardView.setAdapter(leaderboardAdapter);
    }
}
