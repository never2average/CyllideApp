package com.example.kartikbhardwaj.bottom_navigation.backgroundservices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStart extends BroadcastReceiver {

    QuizAlarmManager alarm = new QuizAlarmManager();
    @Override
    public void onReceive(Context context, Intent intent)
    {

        Intent startServiceIntent = new Intent(context, GetLatestQuizIDService.class);
        context.startService(startServiceIntent);
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            alarm.setAlarm(context);
        }
    }
}
