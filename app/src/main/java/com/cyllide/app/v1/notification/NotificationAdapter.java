package com.cyllide.app.v1.notification;


import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.cyllide.app.v1.notification.NotificationActivity.notifs;

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
    public void onBindViewHolder(@NonNull final NotificationViewHolder holder, final int position) {
        final NotificationModel notif = notifs.get(position);
        //holder.populate(notif);

        holder.notifNameTv.setText(notif.getNotifName());
        long notifTime = Long.parseLong(notif.getNotifTime());
        Date date = new Date(notifTime);
        holder.notifTimeTv.setText(date.toString());
        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                markAsRead(notif.getNotifID());
                notifs.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, notifs.size());
            }
        });
    }
    RequestQueue notificationRequestQueue;
    Map<String,String> notificationRequestHeader = new ArrayMap<>();

    void markAsRead(String notifID){
        notificationRequestQueue = Volley.newRequestQueue(context);
        String requestEndPoint = context.getResources().getString(R.string.apiBaseURL)+"notifications/read";
        notificationRequestHeader.put("token", AppConstants.token);
        notificationRequestHeader.put("notificationID",notifID);
        StringRequest notificationStringRequest = new StringRequest(Request.Method.POST,requestEndPoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("NotificationAdapter",response);


                } catch (Exception e) {
                    Log.d("NotificationActivity",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Question Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return notificationRequestHeader;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        notificationRequestQueue.add(notificationStringRequest);
    }

    @Override
    public int getItemCount() {
        return notifs.size();
    }
}
