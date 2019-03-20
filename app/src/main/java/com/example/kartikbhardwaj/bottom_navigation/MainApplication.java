package com.example.kartikbhardwaj.bottom_navigation;

import android.app.Application;
import android.util.Log;

import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListUpdateWorker;
import com.example.kartikbhardwaj.bottom_navigation.stories.NewsUpdateWorker;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    public static final String NEWS_UPDATE_TAG = "update news data in cache";
    public static final String QUESTION_UPDATE_TAG = "fetch new questions";
    private static final int NEWS_UPDATE_FREQUENCY_MINUTES = 30;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        setUpNewsUpdateWorker();
    }

    public static void setUpNewsUpdateWorker() {
        Constraints networkConstraint = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest newsUpdater = new PeriodicWorkRequest.Builder(NewsUpdateWorker.class,
                NEWS_UPDATE_FREQUENCY_MINUTES, TimeUnit.MINUTES)
                .setConstraints(networkConstraint)
                .addTag(NEWS_UPDATE_TAG)
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(NEWS_UPDATE_TAG,
                ExistingPeriodicWorkPolicy.REPLACE, newsUpdater);
        Log.e("MainApplication", "Scheduled New News Worker!");
    }

    public static void setUpQuestionUpdateWorker(){
        Constraints networkConstraint = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        OneTimeWorkRequest questionUpdater = new OneTimeWorkRequest.Builder(QuestionListUpdateWorker.class)
                .setConstraints(networkConstraint)
                .addTag(QUESTION_UPDATE_TAG)
                .build();
        WorkManager.getInstance().enqueueUniqueWork(QUESTION_UPDATE_TAG,
                ExistingWorkPolicy.KEEP, questionUpdater);
        Log.e("MainApplication", "Scheduled New Questions Worker!");
    }

    public static void cancelNewsUpdateWork(){
        WorkManager.getInstance().cancelAllWorkByTag(NEWS_UPDATE_TAG);
    }
}
