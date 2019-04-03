package com.cyllide.app.v1.portfolio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cyllide.app.v1.R;


public class MyPortfolioAdapter extends RecyclerView.Adapter<MyPortfolioViewholder> {

    List<MyPortfolioModel>data;


    @Override
    public int getItemCount() {
        return data.size();
    }

    public MyPortfolioAdapter(List<MyPortfolioModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyPortfolioViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.myportfolios_rv_view,parent,false);
        MyPortfolioViewholder holder = new MyPortfolioViewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPortfolioViewholder holder, int position) {
        MyPortfolioModel list = data.get(position);
        holder.populate(list);

    }
}
