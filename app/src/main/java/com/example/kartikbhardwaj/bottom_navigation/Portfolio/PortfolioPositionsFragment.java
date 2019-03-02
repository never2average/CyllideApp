package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

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

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.BalanceClass;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PositionsAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PositionsModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.ArrayList;
import java.util.List;


public class PortfolioPositionsFragment extends Fragment {


    public TextView BalanceTXTV;
    private RecyclerView RV;


    private List<PositionsModel> dummyData(){
        List<PositionsModel> data= new ArrayList<>();
        for(int i=0;i<3;i++){
            data.add(new PositionsModel("RELIANCE","100", "220.13", "LONG", "23400"));
        }
        return data;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BalanceTXTV = view.findViewById(R.id.balance);
        // seting balance in textview
        BalanceTXTV.setText("₹ " + String.valueOf(BalanceClass.balance));



    }
    public void setTextViewText(String value){
        BalanceTXTV.setText(value);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.portfolio_positions_fragment,null);
        RV = rootView.findViewById(R.id.positions_rv);
        RV.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<PositionsModel> data1 =dummyData();
        PositionsAdapter positionsAdapter= new PositionsAdapter(data1);//,rootView);
        RV.setAdapter(positionsAdapter);
        return rootView;
    }



   public void setBalance()
    {
        BalanceTXTV.setText("₹ " + String.valueOf(BalanceClass.balance));


    }
}

