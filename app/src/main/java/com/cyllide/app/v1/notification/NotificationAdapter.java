package com.cyllide.app.v1.notification;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyllide.app.v1.R;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>
{
    List<NotificationModel> notifList;
    Context context;

    public NotificationAdapter(List<NotificationModel> notifList, Context context) {
        this.notifList = notifList;
        this.context= context;
    }



    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private String notifName, notifTime;
        private TextView notifNameTv, notifTimeTv;
        private ImageView tick;
        private LinearLayout notifll;


        public NotificationViewHolder(View itemView) {
            super(itemView);
            notifNameTv = itemView.findViewById(R.id.tv_notif_name);
            notifTimeTv = itemView.findViewById(R.id.tv_notif_date);
            tick = itemView.findViewById(R.id.tick);
            notifll = itemView.findViewById(R.id.notifll);
        }
    }










    @NonNull
    @Override
    public NotificationAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // view_g => name of the layout file
        View view = inflater.inflate(R.layout.view_notification, parent, false);
        NotificationViewHolder holder = new NotificationViewHolder(view);
        return holder;
    }//link xml to recycler view






    @Override//means whatever we are extending is changed to put our own stuff
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, final int position) {
        NotificationModel notif = NotificationActivity.notifs.get(position);
        //holder.populate(notif);

        holder.notifNameTv.setText(notif.getNotifName());
        holder.notifTimeTv.setText(notif.getNotifTime());
        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationActivity.notifs.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,NotificationActivity.notifs.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return NotificationActivity.notifs.size();
    }
}
