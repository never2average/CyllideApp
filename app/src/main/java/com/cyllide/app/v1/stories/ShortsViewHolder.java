package com.cyllide.app.v1.stories;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.R;

public class ShortsViewHolder extends RecyclerView.ViewHolder {

    ImageView shortsImage;
    TextView shortsName;
    TextView shortsDescrip;
    Context viewContext;
    public ShortsViewHolder(@NonNull View itemView) {
        super(itemView);
        shortsImage=itemView.findViewById(R.id.shorts_image);
        shortsDescrip=itemView.findViewById(R.id.shorts_descrip);
        shortsName=itemView.findViewById(R.id.shorts_name);

        viewContext=itemView.getContext();
    }

    public void populate( ShortsModel modal)
    {
        shortsDescrip.setText(modal.getShortsDescription());
        shortsName.setText(modal.getShortsTitle());
        //uncomment when needed
       // Glide.with(viewContext).load(modal.getShortsImageUrl()).into(shortsImage);
    }

}
