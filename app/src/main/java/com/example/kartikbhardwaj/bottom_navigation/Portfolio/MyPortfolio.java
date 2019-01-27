package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kartikbhardwaj.bottom_navigation.faq_view.FaqAdapter;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.FaqModal;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.Faq_Activity;

import java.util.ArrayList;
import java.util.List;
import com.example.kartikbhardwaj.bottom_navigation.R;


public class MyPortfolio extends AppCompatActivity {
    Button createNewPortfolio;


    RecyclerView RV;


    String portfolionames[]={"p******","p******","p******","p******","p******","p******","p******","p******","p******","p******"};
    String returnsValue[]={"$$","$$","$$","$$","$$","$$","$$","$$","$$","$$"};
    private List<MyPortfolioModel> dummyData() {
        List<MyPortfolioModel> data = new ArrayList<>(12);
        for (int i = 0; i < 10; i++) {
            data.add(new MyPortfolioModel(portfolionames[i], returnsValue[i]));
        }//data is the list of objects to be set in the list item
        return data;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_portfolio);
        RV = findViewById(R.id.my_portfolio);
        final Context context = MyPortfolio.this;


        createNewPortfolio=findViewById(R.id.new_portfolio);


        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(context));
        List<MyPortfolioModel> data = dummyData();
        final MyPortfolioAdapter mAdapter = new MyPortfolioAdapter(data);
        RV.setAdapter(mAdapter);


        createNewPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent portfolioIntent = new Intent(MyPortfolio.this,PortfolioActivity.class);
                startActivity(portfolioIntent);
            }
        });



    }
}
