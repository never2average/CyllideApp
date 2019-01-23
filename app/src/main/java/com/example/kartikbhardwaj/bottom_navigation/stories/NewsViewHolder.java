package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.recyclerview.widget.RecyclerView;

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTv,dateTv,sourceTv, descTv;
    private LinearLayout newsLL;
    private SimpleDraweeView image;
    private String name, description, imageURL, source, date, url, author;
    Intent intent= new Intent(itemView.getContext(),NewsPageActivity.class);

    public NewsViewHolder(View itemView)
    {
        super(itemView);
        newsLL=itemView.findViewById(R.id.newsll);
        nameTv=itemView.findViewById(R.id.news_name);
        image=itemView.findViewById(R.id.news_image_view);
        dateTv=itemView.findViewById(R.id.news_date);
        sourceTv=itemView.findViewById(R.id.news_source);
        descTv=itemView.findViewById(R.id.news_desc);
    }

    public void populate(NewsModel news)
    {
        name=news.getNewsName();
        description=news.getNewsDescription();
        imageURL=news.getNewsImageURL();
        date=news.getNewsDate();
        source=news.getNewsSource();
        url=news.getNewsUrl();
        author=news.getNewsAuthor();

        nameTv.setText(name);
        image.setImageURI(imageURL);
        dateTv.setText(date);
        sourceTv.setText(source);
        descTv.setText(description);
        intent.putExtra("newsname",name);
        intent.putExtra("newsimageurl",imageURL);
        intent.putExtra("newsdesc",description);
        intent.putExtra("newsdate",date);
        intent.putExtra("newssource",source);
        intent.putExtra("newsurl",url);
        intent.putExtra("newsauthor",author);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.getContext().startActivity(intent);
            }
        });
        nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.getContext().startActivity(intent);
            }
        });
        newsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.getContext().startActivity(intent);
            }
        });
    }
}
