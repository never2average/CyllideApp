package com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.Contests.ContestLeaderboard;
import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.MyViewHolder> {

    CardView cv;
    Context context;

    private List<PortfolioModel> portfolioModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pflioName, pflioReturns;

        public MyViewHolder(View view) {
            super(view);
            pflioName = (TextView) view.findViewById(R.id.pflioname);
            //pflioReturns = (TextView) view.findViewById(R.id.pflioreturns);
            cv= view.findViewById(R.id.popup_portfolio_card_view);
            context= view.getContext();
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LeaderboardsActivity.class);
                    context.startActivity(intent);
                }
            });

        }

    }


    public PortfolioAdapter(List<PortfolioModel> pflioList) {
        this.portfolioModelList = pflioList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_portfolio_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PortfolioModel pflio = portfolioModelList.get(position);
        holder.pflioName.setText(pflio.getPortfolioName());
        //holder.pflioReturns.setText(Double.toString(pflio.getReturns()));
    }

    @Override
    public int getItemCount() {
        return portfolioModelList.size();
    }
}

//
//
//import com.example.kartikbhardwaj.bottom_navigation.R;
//
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.ViewHolder> {
//
//    private List<String> mData;
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//
//    // data is passed into the constructor
//    public PortfolioAdapter(Context context, List<String> data) {
//        this.mInflater = LayoutInflater.from(context);
//        this.mData = data;
//    }
//
//    // inflates the row layout from xml when needed
////    @Override
////    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
////        return new ViewHolder(view);
////    }
////
////    // binds the data to the TextView in each row
////    @Override
////    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
////        String animal = mData.get(position);
////        holder.myTextView.setText(animal);
////    }
////
////    // total number of rows
////    @Override
////    public int getItemCount() {
////        return mData.size();
////    }
////
////
////    // stores and recycles views as they are scrolled off screen
////    public class PortfolioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
////        TextView myTextView;
////
////        PortfolioViewHolder(View itemView) {
////            super(itemView);
////            myTextView = itemView.findViewById(R.id.pflioname);
////            itemView.setOnClickListener(this);
////        }
////
////        @Override
////        public void onClick(View view) {
////            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
////        }
////    }
////
////    // convenience method for getting data at click position
////    String getItem(int id) {
////        return mData.get(id);
////    }
////
////    // allows clicks events to be caught
////    void setClickListener(ItemClickListener itemClickListener) {
////        this.mClickListener = itemClickListener;
////    }
////
////    // parent activity will implement this method to respond to click events
////    public interface ItemClickListener {
////        void onItemClick(View view, int position);
////    }
////}
//public class PortfolioViewHolder extends RecyclerView.Adapter<PortfolioAdapter> {
//    List<PortfolioModel> partList2;
//    //Context context;
//
//    public PortfolioAdapter(List<PortfolioModel> pm){
//        this.partList2=pm;
//    }
//    @NonNull
//    @Override
//    public PortfolioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        // view_g => name of the layout file
//        View view = inflater.inflate(R.layout.popup_portfolio_card, parent, false);
//        PortfolioViewHolder holder = new PortfolioViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PortfolioViewHolder holder, int position) {
//        PortfolioModel parts = partList2.get(position);
//        //holder.populate(parts);
//        holder.portfolioName.setText(parts.getPortfolioname());
//        holder.portfolioReturns.setText(Double.toString(parts.getReturns()));
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}
