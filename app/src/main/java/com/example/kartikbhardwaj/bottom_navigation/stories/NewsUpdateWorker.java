package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class NewsUpdateWorker extends Worker {
    public static final String NEWS_WORKER_TAG = "NewsUpdateWorker";
    private Realm realmInstance;
    private static final int NUM_NEWS_ARTICLES_CACHED = 15;

    public NewsUpdateWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);

    }


    @NonNull
    @Override
    public Result doWork() {
        Log.e(NEWS_WORKER_TAG,"Worker was called!");
        initRealm();
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String token = getApplicationContext().getSharedPreferences("AUTHENTICATION", 0)
                .getString("token", "Not found!");
        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("token", token);
        final String newsURL = "http://api.cyllide.com/api/client/stories/view";
        JsonObjectRequest request = new JsonObjectRequest(newsURL, null, future, future){
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse nr) {
                int n = nr.statusCode;
                Log.e("Res Code",""+n);
                return super.parseNetworkResponse(nr);
            }
        };
        requestQueue.add(request);
        try {
            try {
                JSONArray jsonArray = future.get(60,TimeUnit.SECONDS).getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.e(NEWS_WORKER_TAG,"Adding JSONOBJECT to realm");
                    JSONObject article = jsonArray.getJSONObject(i);
                    addToRealm(new NewsModel(
                            article.getString("contentTitle"),
                            article.getString("contentPic"),
                            article.getString("contentType"),
                            article.getString("contentMarkdownLink"),
                            article.getString("contentAuthor")
                    ));
                }
                deleteOldNews();
                realmInstance.close();
                Log.e(NEWS_WORKER_TAG, "Worker returning after success");
                return Result.success();
            } catch (JSONException e) {
                realmInstance.close();
                e.printStackTrace();
                Log.e(NEWS_WORKER_TAG, "Worker returning after failure");
                return Result.failure();
            }
        } catch (InterruptedException e) {
            realmInstance.close();
            e.printStackTrace();
            Log.e(NEWS_WORKER_TAG, "Worker returning after failure");
            return Result.failure();
        } catch (ExecutionException e) {
            realmInstance.close();
            e.printStackTrace();
            Log.e(NEWS_WORKER_TAG, "Worker returning after failure");
            return Result.failure();
        } catch (TimeoutException e) {
            realmInstance.close();
            e.printStackTrace();
            Log.e(NEWS_WORKER_TAG, "Worker returning after failure");
            return Result.failure();
        }
    }

    private void initRealm() {
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        realmInstance = Realm.getInstance(realmConfig);
    }

    private void addToRealm(final NewsModel newsArticle) {
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(NewsModel.class).max("newsID");
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }
                newsArticle.setNewsID(nextId);
                realm.insertOrUpdate(newsArticle);
            }
        });
    }

    private void deleteOldNews(){
        Log.e(NEWS_WORKER_TAG,"Deleting Old news!");
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<NewsModel> newsList = realmInstance.where(NewsModel.class).findAll();
                Log.e(NEWS_WORKER_TAG,"Current Size: "+newsList.size());
                int numOfArticlesToRemove = newsList.size() - NUM_NEWS_ARTICLES_CACHED;
                Log.e(NEWS_WORKER_TAG,"Num of articles to delete: "+numOfArticlesToRemove);
                if(numOfArticlesToRemove > 0){
                    RealmResults<NewsModel> oldNews = realmInstance.where(NewsModel.class).sort("newsID")
                            .limit(numOfArticlesToRemove).findAll();
                    if(oldNews.deleteAllFromRealm()){
                        Log.e(NEWS_WORKER_TAG,"Successfully deleted old news");
                    } else {
                        Log.e(NEWS_WORKER_TAG,"Couldn't delete old news");
                    }
                }
            }
        });



    }


}
