package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.OrderHistoryRV.ClosedPositions;
import com.example.kartikbhardwaj.bottom_navigation.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.OrderHistoryRV.OrderHistoryAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.OrderHistoryRV.OrderHistoryModel;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class OrderHistoryFragment extends Fragment {

    private RecyclerView RV;

    private List<OrderHistoryModel> dummyData() {
        List<OrderHistoryModel> data = new ArrayList<>();
        for (int i = 0; i < ClosedPositions.tickerName.size(); i++) {
            data.add(
                    new OrderHistoryModel(
                            ClosedPositions.tickerName.get(i),
                            ClosedPositions.tickerPositionType.get(i),
                            ClosedPositions.tickerExitTime.get(i),
                            ClosedPositions.tickerEntryPrice.get(i),
                            ClosedPositions.tickerExitPrice.get(i),
                            ClosedPositions.tickerQuantity.get(i)
                    )
            );
        }
        return data;
    }

    final List<OrderHistoryModel> data =dummyData();
    final OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(data);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_history, container, false);
        RV=(RecyclerView)view.findViewById(R.id.order_history_rv);
        RV.setLayoutManager(new LinearLayoutManager(getActivity()));
        RV.setAdapter(orderHistoryAdapter);
        return view;
    }
}
