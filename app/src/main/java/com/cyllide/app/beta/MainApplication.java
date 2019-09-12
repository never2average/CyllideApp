package com.cyllide.app.beta;

import android.app.Application;
import android.util.Log;

import com.cyllide.app.beta.forum.questionlist.QuestionListUpdateWorker;


import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    public static final String NEWS_UPDATE_TAG = "update news data in cache";
    public static final String QUESTION_UPDATE_TAG = "fetch new questions";
    private static final int NEWS_UPDATE_FREQUENCY_MINUTES = 30;
    private static final int QUESTION_UPDATE_FREQUENCY_MINUTES = 30;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        setUpNewsUpdateWorker();
        setUpQuestionUpdateWorker();
    }

    public static void setUpNewsUpdateWorker() {
        Constraints networkConstraint = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
    }

    public static void setUpQuestionUpdateWorker(){
        Constraints networkConstraint = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest questionUpdater = new PeriodicWorkRequest.Builder(
                QuestionListUpdateWorker.class, QUESTION_UPDATE_FREQUENCY_MINUTES, TimeUnit.MINUTES)
                .setConstraints(networkConstraint)
                .addTag(QUESTION_UPDATE_TAG)
                .build();
        WorkManager.getInstance().enqueueUniquePeriodicWork(QUESTION_UPDATE_TAG,
                ExistingPeriodicWorkPolicy.KEEP, questionUpdater);
        Log.e("MainApplication", "Scheduled New Questions Worker!");
    }

    public static void cancelNewsUpdateWork(){
        WorkManager.getInstance().cancelAllWorkByTag(NEWS_UPDATE_TAG);
    }
}
