package com.cyllide.app.v1.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView crossBtn;
    RequestQueue notificationRequestQueue;
    Map<String,String> notificationRequestHeader = new ArrayMap<>();

    String notifname[]={"Notification1","Notification2","Notification3"};
    String notiftime[]={"22:02","11:02","09:02"};

    private List<NotificationModel> dummyData() {
        List<NotificationModel> data = new ArrayList<>(5);
        for (int i = 0; i < 3; i++) {
            data.add(new NotificationModel(notifname[i], notiftime[i]));
        }//data is the list of objects to be set in the list item
        return data;
    }

    static List<NotificationModel> notifs= new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        crossBtn=findViewById(R.id.cross_btn);

        if(notifs.isEmpty())
        {
            notifs=dummyData();
        }


        recyclerView = findViewById(R.id.notifrv);
        setNotification(recyclerView,getApplicationContext());

        NotificationAdapter adapter = new NotificationAdapter(notifs, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(NotificationActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }

    private void setNotification(RecyclerView recyclerView, Context context) {

        notificationRequestQueue = Volley.newRequestQueue(context);
        String requestEndPoint = "";
//        notificationRequestHeader.put();
        StringRequest notificationStringRequest = new StringRequest(Request.Method.GET,requestEndPoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("",response);

                } catch (Exception e) {
                    e.printStackTrace();
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
}
