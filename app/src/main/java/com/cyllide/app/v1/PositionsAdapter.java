package com.cyllide.app.v1;

import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PositionsAdapter extends RecyclerView.Adapter<PositionsViewHolder> {
    ArrayList<PositionsModel> positionsModels;

    @NonNull
    @Override
    public PositionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull PositionsViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
