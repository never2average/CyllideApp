package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.recyclerview.widget.RecyclerView;

public class StoriesViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTv;
    private SimpleDraweeView image;
    private String name, description, imageURL;

    public StoriesViewHolder(View itemView)
    {
        super(itemView);
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
    }
}
