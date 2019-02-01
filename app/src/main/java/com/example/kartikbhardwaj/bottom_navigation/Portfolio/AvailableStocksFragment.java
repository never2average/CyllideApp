package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStockRV.AvailableStockAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStockRV.AvailableStockModel;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStocksRV.AvailableStocksAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStocksRV.AvailableStocksModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.ArrayList;
import java.util.List;


public class AvailableStocksFragment extends Fragment {


    SearchView searchView;
    RecyclerView RV;
    private String indexNames[]={"Nifty Auto","Nifty IT","Nifty Pharma","Nifty","Sensex"};
    private String indexValues[]={"1920","5120","2340","11000","35000"};
    private Double indexChanges[]={-6.1,3.23,-1.29,7.9,0.34};


    private String stockNames[]={"MRF","RELIANCE","VEDL","HINDALCO","INFY"};
    private String stockPrices[]={"70000","1020","240","170","1000"};
    private Double priceChanges[]={-3.1,6.23,-7.29,1.9,2.34};

    private List<AvailableStocksModel> dummyData() {
        List<AvailableStocksModel> stocksModelList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stocksModelList.add(new AvailableStocksModel(indexNames[i],indexValues[i],indexChanges[i]));
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
        final List<AvailableStocksModel> data1=dummyData();
        AvailableStocksAdapter stocksAdapter=new AvailableStocksAdapter(data1);
        RV.setAdapter(stocksAdapter);
        searchView=view.findViewById(R.id.searchbarstocks);
//        searchView.setIconifiedByDefault(false);
//        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<AvailableStockModel> data2=dummyData2(query);
                RV.setAdapter(new AvailableStockAdapter(data2));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")==false) {
                    List<AvailableStockModel> data2 = dummyData2(newText);
                    RV.setAdapter(new AvailableStockAdapter(data2));
                    return true;
                }
                else{
                    RV.setAdapter(new AvailableStocksAdapter(data1));
                    return true;
                }
            }
        });
        return view;

    }
}