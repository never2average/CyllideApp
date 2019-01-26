package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.view.View;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.view.SimpleDraweeView;

import androidx.recyclerview.widget.RecyclerView;

public class MonthlyViewHolder extends RecyclerView.ViewHolder {

    private TextView nameTv,dateTv,sourceTv;
    private SimpleDraweeView image;
    private String name, description, imageURL, source, date;

    public MonthlyViewHolder(View itemView)
    {
        super(itemView);
        nameTv=itemView.findViewById(R.id.monthly_name);
        image=itemView.findViewById(R.id.monthly_image_view);
        dateTv=itemView.findViewById(R.id.monthly_date);
        sourceTv=itemView.findViewById(R.id.monthly_source);
    }

    public void populate(MonthlyModel news)
    {
        name=news.getMonthlyName();
        description=news.getMonthlyDescription();
        imageURL=news.getMonthlyImageURL();


        nameTv.setText(name);
        image.setImageURI(imageURL);
        dateTv.setText(date);
        sourceTv.setText(source);
    }
}
