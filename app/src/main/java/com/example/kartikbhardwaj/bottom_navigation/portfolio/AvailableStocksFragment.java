package com.example.kartikbhardwaj.bottom_navigation.portfolio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.AvailableStockRV.AvailableStockAdapter;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.AvailableStockRV.AvailableStockModel;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.AvailableIndicesRV.AvailableIndexAdapter;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.AvailableIndicesRV.AvailableIndexModel;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.PortfolioPositionsRV.BalanceClass;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class AvailableStocksFragment extends Fragment {


    FloatingSearchView searchView;
    public TextView BalanceTXTV;
    RecyclerView RV;
    private String indexNames[]={"Nifty Auto","Nifty IT","Nifty Pharma","Nifty","Sensex"};
    private String indexValues[]={"1920","5120","2340","11000","35000"};
    private Double indexChanges[]={-6.1,3.23,-1.29,7.9,0.34};


    private String stockNames[]={"MRF","RELIANCE","VEDL","HINDALCO","INFY"};
    private Double stockPrices[]={70000.0,1020.0,240.0,170.0,1000.0};
    private Double priceChanges[]={-3.1,6.23,-7.29,1.9,2.34};

    private List<AvailableIndexModel> dummyData() {
        List<AvailableIndexModel> stocksModelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stocksModelList.add(new AvailableIndexModel(indexNames[i],indexValues[i],indexChanges[i]));
        }
        return stocksModelList;
    }

    private List<AvailableStockModel> dummyData2(String filter){
        List<AvailableStockModel> stockModelList=new ArrayList<>();
        for(int i=0;i<5;i++){
            if (stockNames[i].contains(filter.toUpperCase())){
                stockModelList.add(new AvailableStockModel(stockNames[i],stockPrices[i],priceChanges[i]));
            }

        }
        return stockModelList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.available_stocks_fragment,null);
        RV=view.findViewById(R.id.showstocks);
        RV.setLayoutManager(new LinearLayoutManager(getActivity()));
        final List<AvailableIndexModel> data1=dummyData();
        AvailableIndexAdapter stocksAdapter=new AvailableIndexAdapter(data1);
        RV.setAdapter(stocksAdapter);
        BalanceTXTV = view.findViewById(R.id.balance);
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        BalanceTXTV.setText("₹ " + String.valueOf(formatter.format(BalanceClass.balance)));
        searchView=view.findViewById(R.id.searchbarstocks);
        searchView.clearFocus();
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                if(newQuery.equals("")==false) {
                    List<AvailableStockModel> data2 = dummyData2(newQuery);
                    RV.setAdapter(new AvailableStockAdapter(data2,getContext()));
                }
                else{
                    RV.setAdapter(new AvailableIndexAdapter(data1));
                }
            }
        });
        return view;

    }
    public void setTextViewText(String value){
        BalanceTXTV.setText(value);
    }

}