package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTv,dateTv,sourceTv;
    private SimpleDraweeView image;
    private String name, description, imageURL, source, date;

    public NewsViewHolder(View itemView)
    {
        super(itemView);
        nameTv=itemView.findViewById(R.id.news_name);
        image=itemView.findViewById(R.id.news_image_view);
        dateTv=itemView.findViewById(R.id.news_date);
        sourceTv=itemView.findViewById(R.id.news_source);
    }

    public void populate(NewsModel news)
    {
        name=news.getNewsName();
        description=news.getNewsDescription();
        imageURL=news.getNewsImageURL();


        nameTv.setText(name);
        image.setImageURI(imageURL);
        dateTv.setText(date);
        sourceTv.setText(source);
    }
}
