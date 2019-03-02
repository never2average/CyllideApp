package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.AvailableStockRV.RecyclerItemTouchHelper;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV.OrdersAdapter;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV.OrdersModel;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV.OrdersViewHolder;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class PendingOrdersFragment extends Fragment{
    RecyclerView RV;
    int position;


    private List<OrdersModel> dummyData() {
        List<OrdersModel> data = new ArrayList<>(12);
        for (int i = 0; i < 4; i++) {
            data.add(new OrdersModel("Market","Long","10","01-03-2018 00:40","RELIANCE","121.34"));
        }
        return data;
    }

    final List<OrdersModel> data =dummyData();
    final OrdersAdapter ordersAdapter= new OrdersAdapter(data);




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pending_orders_fragment,container,false);


        RV=(RecyclerView)rootView.findViewById(R.id.pending_Orders_RV);
        RV.setLayoutManager(new LinearLayoutManager(getActivity()));
        RV.setAdapter(ordersAdapter);
        return rootView;
    }
}




