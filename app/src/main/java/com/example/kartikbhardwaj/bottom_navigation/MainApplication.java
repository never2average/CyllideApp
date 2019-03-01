package com.example.kartikbhardwaj.bottom_navigation;

import android.app.Activity;
import android.app.Application;

import com.example.kartikbhardwaj.bottom_navigation.stories.NewsUpdateWorker;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    public static final String NEWS_UPDATE_TAG = "update news data in cache";
    private static final int UPDATE_FREQUENCY_MINUTES = 15;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("application.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
        setUpNewsUpdateWorker();
    }

    public static void setUpNewsUpdateWorker() {
        Constraints networkConstraint = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest newsUpdater = new PeriodicWorkRequest.Builder(NewsUpdateWorker.class,
                UPDATE_FREQUENCY_MINUTES, TimeUnit.MINUTES)
                .setConstraints(networkConstraint)
                .addTag(NEWS_UPDATE_TAG)
                .build();
        WorkManager.getInstance().enqueue(newsUpdater);

    }

    public static void cancelNewsUpdateWork(){
        WorkManager.getInstance().cancelAllWorkByTag(NEWS_UPDATE_TAG);
    }
}
