package com.cyllide.app.v1.portfolio;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeaderBoardViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    CircleImageView profileImag;
    TextView points;
    Context context;
LinearLayout layout;
    public LeaderBoardViewHolder(@NonNull View itemView) {
        super(itemView);
        context=itemView.getContext();
        name=itemView.findViewById(R.id.name);
        profileImag=itemView.findViewById(R.id.profilePic);
        points=itemView.findViewById(R.id.points);
        layout=itemView.findViewById(R.id.layout);
    }

    public void populate(LeaderBoardModel model){
        name.setText(model.getName());
        points.setText(model.getPoints());
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(AppConstants.username);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .endConfig()
                .buildRect(Character.toString(model.getName().charAt(0)).toUpperCase(), color);
        if(model.getProfileUri().equals("https://firebasestorage.googleapis.com/v0/b/cyllide.appspot.com/o/defaultuser.png?alt=media&token=0453d4ba-82e8-4b6c-8415-2c3761d8b345"))
        {profileImag.setImageDrawable(drawable);}

       else{ Glide.with(context).load(Uri.parse(model.getProfileUri())).into(profileImag);}
       if(model.getPosition()%2==0){
           layout.setBackgroundColor(ContextCompat.getColor(context,R.color.cyllide_grey));

       }

    }
}
