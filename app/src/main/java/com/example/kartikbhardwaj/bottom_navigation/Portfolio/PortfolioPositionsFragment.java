package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import android.os.BadParcelableException;
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

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PortfolioPositionsInterface;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PositionsAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PositionsModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.ArrayList;
import java.util.List;


public class PortfolioPositionsFragment extends Fragment {


    public TextView BalanceTXTV;
    private RecyclerView RV;
//    private String PositionTicker[]={"AAPL","IBM"};
//    private String PositionQuantity[]={"29","31"};
//    private String PositionCurrPrice[]={"156(+2.29%)▲","123.45(-1.23%)▼"};
//    private String PositionType[]={"LONG","SHORT"};
//    private String PositionValue[]={"$10,000","$20,000"};


    private List<PositionsModel> dummyData(){
        List<PositionsModel> data= new ArrayList<>();
        for(int i=0;i<=1;i++){
            data.add(new PositionsModel(PortfolioPositionsInterface.stockTicker.get(i),PortfolioPositionsInterface.Quantity.get(i).toString(),PortfolioPositionsInterface.orderPrice.get(i).toString(),PortfolioPositionsInterface.positionType.get(i),String.valueOf(PortfolioPositionsInterface.Quantity.get(i)*PortfolioPositionsInterface.orderPrice.get(i))));

        }
        return data;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BalanceTXTV=view.findViewById(R.id.balance);
        Balance.addmoney(10000);
        BalanceTXTV.setText("$ "+Float.toString(Balance.balance));


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.portfolio_positions_fragment,null);
        RV=(RecyclerView)rootView.findViewById(R.id.positions_rv);
        RV.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<PositionsModel> data1 =dummyData();
        PositionsAdapter positionsAdapter= new PositionsAdapter(data1);
        RV.setAdapter(positionsAdapter);
        return rootView;
    }
}
