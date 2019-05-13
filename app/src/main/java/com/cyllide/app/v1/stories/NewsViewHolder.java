package com.cyllide.app.v1.stories;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyllide.app.v1.R;
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
        newsLL=itemView.findViewById(R.id.storiesll);
        nameTv=itemView.findViewById(R.id.story_title);
        image=itemView.findViewById(R.id.stories_image_view);
        dateTv=itemView.findViewById(R.id.story_title);
        sourceTv=itemView.findViewById(R.id.story_author);
        descTv=itemView.findViewById(R.id.story_tag);
    }

    public void populate(NewsModel news)
    {
        name=news.getNewsName();
        description=news.getNewsDescription();
        imageURL=news.getNewsImageURL();
//        date=news.getNewsDate();
//        source=news.getNewsSource();
        url=news.getNewsUrl();
        author=news.getNewsAuthor();

        nameTv.setText(name);
        image.setImageURI(imageURL);
        dateTv.setText(date);
        sourceTv.setText(source);
        descTv.setText(description);
        intent.putExtra("newsurl",url);
        intent.putExtra("mongoID",news.getMongoID());
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
                //TODO Finish
            }
        });
        newsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.getContext().startActivity(intent);
                //TODO Finish
            }
        });
    }
}
