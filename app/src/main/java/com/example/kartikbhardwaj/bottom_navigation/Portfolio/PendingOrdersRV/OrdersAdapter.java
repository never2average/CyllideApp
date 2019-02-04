package com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersRV;

import android.app.Activity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolioModel;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolioViewholder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PendingOrdersFragment;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.card.MaterialCardView;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersViewHolder> {
   private List<OrdersModel>data;
   private MaterialCardView cardView;
   private GestureDetector mDetector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;


    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {

        OrdersModel list = data.get(position);
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mDetector.onTouchEvent(event);

                return true;
            }
        });





        holder.populate(list);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public OrdersAdapter(List<OrdersModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.pending_orders_rv_view,parent,false);
        OrdersViewHolder holder = new OrdersViewHolder(view);
        cardView=view.findViewById(R.id.card);

        mDetector = new GestureDetector(view.getContext(), new MyListener());











        return holder;
        //return null;
    }



}
class MyListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;



    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
       // return super.onFling(e1, e2, velocityX, velocityY);
        if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {



            return true; // Right to left
        }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

            return true; // Left to right
        }

        if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            return false; // Bottom to top
        }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            return false; // Top to bottom
        }
        return false;
    }


    }

