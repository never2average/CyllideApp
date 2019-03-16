package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

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
        List<OrderHistoryModel> data = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            data.add(new OrderHistoryModel("RELIANCE","Long","01-03-2018 21:47",25.12,25.45,25));
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
