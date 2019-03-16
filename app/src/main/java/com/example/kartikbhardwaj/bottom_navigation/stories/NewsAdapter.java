package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class NewsAdapter extends RealmRecyclerViewAdapter<NewsModel,NewsViewHolder>{
    List<NewsModel> partList;

    public NewsAdapter(OrderedRealmCollection<NewsModel> partList) {
        super(partList,true);
        Log.e("NewsAdapter","Creating NewsAdapter");
        this.partList = partList;
    }
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // view_g => name of the layout file
        View view = inflater.inflate(R.layout.view_news, parent, false);
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }//link xml to recycler view

    @Override//means whatever we are extending is changed to put our own stuff
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel parts = partList.get(position);
        holder.populate(parts);
    }

    @Override
    public int getItemCount() {
        return partList.size();
    }
}
