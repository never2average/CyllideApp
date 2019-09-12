package com.cyllide.app.beta.stories;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cyllide.app.beta.R;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;




public class StoriesViewHolder extends RecyclerView.ViewHolder {

    private TextView titleTV,authorTV,summaryTV;
    private SimpleDraweeView image;
    private TextView storyTag;
    private CardView storiesLL;

    public StoriesViewHolder(View itemView)
    {
        super(itemView);
        storiesLL=itemView.findViewById(R.id.stories_card_view);
        image=itemView.findViewById(R.id.stories_image_view);
        storyTag = itemView.findViewById(R.id.story_tag);
        titleTV=itemView.findViewById(R.id.story_title);
        authorTV=itemView.findViewById(R.id.story_author);
        summaryTV=itemView.findViewById(R.id.story_summary);
    }

    public void populate(final StoriesModel stories)
    {
        storyTag.setText(" "+stories.getContentType());
        storyTag.setBackgroundColor(Color.parseColor(stories.getContentColor()));
        storyTag.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.black));
        titleTV.setText(stories.getStoryName());
        authorTV.setText("By: "+stories.getContentAuthor());
        summaryTV.setText(stories.getStoryDescription());
        Glide.with(itemView.getContext()).load(stories.getStoryImageURL()).into(image);
        storiesLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), StoriesPageActivity.class);
                intent.putExtra("url",stories.getContentMarkdownLink());
                intent.putExtra("mongoID",stories.getMongoID());
                itemView.getContext().startActivity(intent);


            }
        });
    }
}
