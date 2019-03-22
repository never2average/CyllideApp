package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MonthlyAdapter extends RecyclerView.Adapter<MonthlyViewHolder> {
    List<MonthlyModel> partList2;
    private FragmentActivity ac;

    public MonthlyAdapter(List<MonthlyModel> partList2, FragmentActivity ac) {
        this.partList2 = partList2;
        this.ac = ac;
    }

    @NonNull
    @Override
    public MonthlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // view_g => name of the layout file
        View view = inflater.inflate(R.layout.view_monthly, parent, false);
        MonthlyViewHolder holder = new MonthlyViewHolder(view, ac);
        return holder;
    }//link xml to recycler view

    @Override//means whatever we are extending is changed to put our own stuff
    public void onBindViewHolder(@NonNull MonthlyViewHolder holder, int position) {
        MonthlyModel parts = partList2.get(position);
        holder.populate(parts);
    }

    @Override
    public int getItemCount() {
        return partList2.size();
    }
}
