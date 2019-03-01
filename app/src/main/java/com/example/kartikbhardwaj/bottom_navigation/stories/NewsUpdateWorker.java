package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NewsUpdateWorker extends Worker {
    Realm realmInstance;

    public NewsUpdateWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);

    }


    @NonNull
    @Override
    public Result doWork() {
        Log.e("NewsUpdateWorker","Worker was called!");
        initRealm();
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final String newsURL = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=f6bddf738e64468280f0a7173b265b41";
        JsonObjectRequest request1 = new JsonObjectRequest(newsURL, null, future, future);
        requestQueue.add(request1);

        try {
            try {
                JSONArray jsonArray = future.get(60,TimeUnit.SECONDS).getJSONArray("articles");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject article = jsonArray.getJSONObject(i);
                    addToRealm(new NewsModel(
                            article.getString("title"),
                            article.getString("urlToImage"),
                            article.getString("publishedAt"),
                            article.getJSONObject("source").getString("name"),
                            article.getString("description"),
                            article.getString("url"),
                            article.getString("author")
                    ));
                }
                realmInstance.close();
                Log.e("NewsUpdateWorker", "Worker returning after success");
                return Result.success();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        finally {
            if(!realmInstance.isClosed()){
                realmInstance.close();
            }

            Log.e("NewsUpdateWorker", "Worker returning after failure");
            return Result.failure();
        }


    }

    private void initRealm() {
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("application.realm")
                .schemaVersion(0)
                .build();
        realmInstance = Realm.getInstance(realmConfig);

    }

    private void addToRealm(final NewsModel newsArticle) {
        realmInstance.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(newsArticle);
            }
        });

    }

}
