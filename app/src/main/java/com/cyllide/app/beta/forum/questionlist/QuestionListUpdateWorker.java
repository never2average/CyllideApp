package com.cyllide.app.beta.forum.questionlist;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.R;

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

public class QuestionListUpdateWorker extends Worker {
    public static final String QUESTION_WORKER_TAG = "QuestionUpdateWorker";
    private Realm realmInstance;

    public QuestionListUpdateWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Log.e(QUESTION_WORKER_TAG,"Worker was called!");
        initRealm();
        RequestFuture<String> future = RequestFuture.newFuture();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final Map<String, String> requestHeaders = new ArrayMap<String, String>();
        requestHeaders.put("token", AppConstants.token);
        String requestEndpoint = getApplicationContext().getResources().getString(R.string.apiBaseURL)+"query/display";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestEndpoint, future, future) {
            @Override
            public Map<String, String> getHeaders() {
                return requestHeaders;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy( 60000, 5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);


        try {
            try {//future.get() is blocking.
                JSONObject responseObject = new JSONObject(future.get(1L, TimeUnit.MINUTES));
                JSONArray jsonArray = responseObject.getJSONArray("message");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.e(QUESTION_WORKER_TAG,"Adding JSONOBJECT to realm");
                    JSONObject questionObject = jsonArray.getJSONObject(i);
                    addToRealm(new QuestionListModel(
                            questionObject.getString("queryBody"),
                            questionObject.getJSONObject("_id"),
                            questionObject.getInt("queryNumViews"),
                            questionObject.getJSONObject("queryLastUpdateTime")
                                    .getLong("$date"),
                            questionObject.getJSONArray("queryTags")));
                }
                realmInstance.close();
                Log.e(QUESTION_WORKER_TAG, "Worker returning after success");
                return Result.success();
            } catch (JSONException e) {
                realmInstance.close();
                e.printStackTrace();
                Log.e(QUESTION_WORKER_TAG, "Worker returning after failure due to JSON exception");
                return Result.failure();
            }
        } catch (InterruptedException e) {
            realmInstance.close();
            e.printStackTrace();
            Log.e(QUESTION_WORKER_TAG, "Worker returning after failure due to interruption");
            return Result.failure();
        } catch (TimeoutException e) {
            realmInstance.close();
            e.printStackTrace();
            Log.e(QUESTION_WORKER_TAG, "Worker returning after failure due to timeout");
            return Result.failure();
        } catch (ExecutionException e) {
            realmInstance.close();
            e.printStackTrace();
            Log.e(QUESTION_WORKER_TAG, "Worker returning after failure due to execution exception");
            return Result.failure();
        }

    }

    private void initRealm() {
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded().build();
        realmInstance = Realm.getInstance(realmConfig);
    }

    private void addToRealm(final QuestionListModel question) {
        Log.e(QUESTION_WORKER_TAG, "Adding news item");
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(question);
            }
        });
    }
}
