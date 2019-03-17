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

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV.OrdersAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV.OrdersModel;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.BalanceClass;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.CurrentPositions;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PositionsAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PositionsModel;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class PortfolioPositionsFragment extends Fragment {

    private RecyclerView positionsRV, pendingOrdersRV;


    private List<PositionsModel> portfolioPositionsData(){
        List<PositionsModel> data= new ArrayList<>();
        for(int i = 0; i<CurrentPositions.tickerName.size(); i++){
            if(System.currentTimeMillis()/1000L - CurrentPositions.tickerEntryTime.get(i) >= 300){
                data.add(new PositionsModel(CurrentPositions.tickerName.get(i),CurrentPositions.tickerQuantity.get(i),String.valueOf(1234.23),CurrentPositions.tickerPositionType.get(i),CurrentPositions.tickerQuantity.get(i)*1234.23));
                BalanceClass.balance-=(CurrentPositions.tickerQuantity.get(i)*1234.23);
            }
        }
        return data;
    }
    private List<OrdersModel> pendingOrdersData() {
        List<OrdersModel> data = new ArrayList<>(12);
        for (int i = 0; i < CurrentPositions.tickerName.size(); i++) {
            if(System.currentTimeMillis()/1000L - CurrentPositions.tickerEntryTime.get(i) < 300){
                data.add(new OrdersModel(CurrentPositions.tickerPositionType.get(i),CurrentPositions.tickerQuantity.get(i),CurrentPositions.tickerName.get(i),String.valueOf(1234.23)));
            }
        }
        return data;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.portfolio_positions_fragment,null);
        positionsRV = rootView.findViewById(R.id.positions_rv);
        positionsRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        pendingOrdersRV = rootView.findViewById(R.id.pending_Orders_RV);
        pendingOrdersRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<PositionsModel> data1 = portfolioPositionsData();
        PositionsAdapter positionsAdapter= new PositionsAdapter(data1);
        positionsRV.setAdapter(positionsAdapter);

        List<OrdersModel> ordersModels = pendingOrdersData();
        OrdersAdapter ordersAdapter = new OrdersAdapter(ordersModels);
        pendingOrdersRV.setAdapter(ordersAdapter);

        return rootView;
    }

}

