package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.recyclerview.widget.RecyclerView;

public class WeeklyViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTv;
    private SimpleDraweeView image;
    private String name, description, imageURL;

    public WeeklyViewHolder(View itemView)
    {
        super(itemView);
        nameTv=itemView.findViewById(R.id.weekly_name);
        image=itemView.findViewById(R.id.weekly_image_view);
    }

    public void populate(WeeklyModel Weekly)
    {
        name=Weekly.getWeeklyName();
        description=Weekly.getWeeklyDescription();
        imageURL=Weekly.getWeeklyImageURL();

        nameTv.setText(name);
        image.setImageURI(imageURL);
    }
}
