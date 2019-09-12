package com.cyllide.app.beta.stories;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cyllide.app.beta.R;

public class ShortsViewHolder extends RecyclerView.ViewHolder {

    ImageView shortsImage;
    TextView shortsName;
    TextView shortsDescription;
    Context viewContext;
    public ShortsViewHolder(@NonNull View itemView) {
        super(itemView);
        shortsImage=itemView.findViewById(R.id.shorts_image);
        shortsDescription =itemView.findViewById(R.id.shorts_description);
        shortsName=itemView.findViewById(R.id.shorts_name);

        viewContext=itemView.getContext();
    }

    public void populate(ShortsModel model)
    {
        shortsDescription.setText(model.getShortsDescription());
        shortsName.setText(model.getShortsTitle());
        Glide.with(viewContext).load(model.getShortsImageUrl()).into(shortsImage);
    }

}
