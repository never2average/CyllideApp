package com.example.kartikbhardwaj.bottom_navigation.Contests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV.PortfolioAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV.PortfolioModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.ArrayList;
import java.util.List;

public class testPortfolioActivity extends AppCompatActivity {
    RecyclerView portfolioView;

//    private List<PortfolioModel> dummyData() {
//        List<PortfolioModel> data = new ArrayList<>(5);
//        for (int i = 0; i < 5; i++) {
//            data.add(new PortfolioModel("TEST_DAAATAAA", 0.0));
//        }//data is the list of objects to be set in the list item
//        return data;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // data to populate the RecyclerView with
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_portfolio);
        portfolioView= findViewById(R.id.portfolios);
        portfolioView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        //List<PortfolioModel> data=dummyData();
        //data.add(new PortfolioModel("lkl",0.0));
       // PortfolioAdapter portfolioAdapter = new PortfolioAdapter(data);
       // portfolioView.setLayoutManager(layoutManager);
       // portfolioView.setAdapter(portfolioAdapter);
        //Log.e("Lame",data.get(0).getPortfolioName());
    }
}
