package com.cyllide.app.v1.stories;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyllide.app.v1.R;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.recyclerview.widget.RecyclerView;

public class StoriesViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTv;
    private LinearLayout storiesLL;
    private SimpleDraweeView image;
    private String name, description, imageURL;
    Intent intent= new Intent(itemView.getContext(),StoryPageActivity.class);

    public StoriesViewHolder(View itemView)
    {
        super(itemView);
        storiesLL=itemView.findViewById(R.id.storiesll);
        nameTv=itemView.findViewById(R.id.stories_name);
        image=itemView.findViewById(R.id.stories_image_view);
    }

    public void populate(StoriesModel stories)
    {
        name=stories.getStoryName();
        description=stories.getStoryDescription();
        imageURL=stories.getStoryImageURL();

        nameTv.setText(name);
        image.setImageURI(imageURL);

        intent.putExtra("storyname",name);
        intent.putExtra("storyurl",imageURL);
        intent.putExtra("storydesc",description);

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

        storiesLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemView.getContext().startActivity(intent);
            }
        });
    }
}
