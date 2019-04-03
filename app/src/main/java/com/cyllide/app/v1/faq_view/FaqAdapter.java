package com.cyllide.app.v1.faq_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cyllide.app.v1.R;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FaqAdapter extends RecyclerView.Adapter<Faq_viewholder> {
    List<FaqModal>faqlist;

    @NonNull
    @Override
    public Faq_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.faq_view,parent,false);
        Faq_viewholder holder = new Faq_viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Faq_viewholder holder, int position) {
        FaqModal list = faqlist.get(position);
        holder.populate(list);
    }

    @Override
    public int getItemCount() {
        return faqlist.size();
    }

    public FaqAdapter( List<FaqModal>faqlist) {
        this.faqlist=faqlist;
    }
}
