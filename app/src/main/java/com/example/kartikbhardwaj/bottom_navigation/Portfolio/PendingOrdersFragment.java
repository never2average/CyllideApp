package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PendingOrdersInterface;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioPositionsRV.PortfolioPositionsInterface;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class PendingOrdersFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener  {
    RecyclerView RV;
    int position;


    private List<OrdersModel> dummyData() {
        List<OrdersModel> data = new ArrayList<>(12);
        for (int i = 0; i < PendingOrdersInterface.orderPrice.size(); i++) {
            data.add(new OrdersModel(PendingOrdersInterface.orderType.get(i),
                    PendingOrdersInterface.positionType.get(i),
                    String.valueOf(PendingOrdersInterface.Quantity.get(i)),
                    String.valueOf(PendingOrdersInterface.executionPrice.get(i)),
                    PendingOrdersInterface.stockTicker.get(i),
                    String.valueOf(PendingOrdersInterface.orderPrice.get(i))));
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
        RV.setItemAnimator(new DefaultItemAnimator());
        RV.addItemDecoration(new DividerItemDecoration(rootView.getContext(), DividerItemDecoration.VERTICAL));
        RV.setAdapter(ordersAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(RV);



//        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                Toast.makeText(rootView.getContext(),"onMove",Toast.LENGTH_LONG).show();
//                return true;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                // Row is swiped from recycler view
//                // remove it from adapter
//                if (viewHolder instanceof OrdersViewHolder) {
//
//
//                    String name = data.get(viewHolder.getAdapterPosition()).getStockTicker();
//                    final OrdersModel deletedItem = data.get(viewHolder.getAdapterPosition());
//                    final int deletedIndex = viewHolder.getAdapterPosition();
//
//
//                    PendingOrdersInterface.positionType.remove(position);
//                    PendingOrdersInterface.orderType.remove(position);
//                    PendingOrdersInterface.stockTicker.remove(position);
//                    PendingOrdersInterface.orderPrice.remove(position);
//                    PendingOrdersInterface.Quantity.remove(position);
//                    //final List<OrdersModel> data2 =dummyData();
//
//                    // OrdersAdapter adapter=new OrdersAdapter(data2);
//                    // RV.setAdapter(adapter);
//                    ordersAdapter.removeItem(position);
//
//
//                    Snackbar snackbar = Snackbar
//                            .make(rootView.findViewById(R.id.fragment_ID), name + " removed from cart!", Snackbar.LENGTH_LONG);
//                    snackbar.setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            // undo is selected, restore the deleted item
//                            ordersAdapter.restoreItem(deletedItem, deletedIndex);
//                        }
//                    });
//                    snackbar.setActionTextColor(Color.YELLOW);
//                    snackbar.show();
//                }
//
//
//            }
//
//            @Override
//            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                // view the background view
//            }
//        };

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(RV);


        return rootView;




    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof OrdersViewHolder) {
            // get the removed item name to display it in snack bar
            String name = data.get(viewHolder.getAdapterPosition()).getStockTicker();
                    final OrdersModel deletedItem = data.get(viewHolder.getAdapterPosition());
                    final int deletedIndex = viewHolder.getAdapterPosition();

            // backup of removed item for undo purpose

            // remove the item from recycler view

                   //final List<OrdersModel> data2 =dummyData();

                    // OrdersAdapter adapter=new OrdersAdapter(data2);
                   // RV.setAdapter(adapter);
                   ordersAdapter.removeItem(position);

            // showing snack bar with Undo option

            Snackbar snackbar = Snackbar
                         .make(getView().findViewById(R.id.fragment_ID), name + " removed from Pending Orders List", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                       public void onClick(View view) {

                            // undo is selected, restore the deleted item
                            ordersAdapter.restoreItem(deletedItem, deletedIndex);
                        }
                    });
                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();



}}}




