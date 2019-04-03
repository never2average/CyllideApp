package com.cyllide.app.v1.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView crossBtn;

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
            }
        });
    }
}