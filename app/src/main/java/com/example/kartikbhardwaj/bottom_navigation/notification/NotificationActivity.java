package com.example.kartikbhardwaj.bottom_navigation.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kartikbhardwaj.bottom_navigation.R;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;

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
    }
}
