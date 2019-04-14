package com.cyllide.app.v1.stories;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.MainApplication;
import com.cyllide.app.v1.R;
import com.facebook.drawee.backends.pipeline.Fresco;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.content.ContentValues.TAG;

public class NewsFragment extends Fragment {

    private Realm realmInstance;
    private RecyclerView newsRV;
    RequestQueue storiesQueue;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("NewsFragment", "Realm instance initialized");
        realmInstance = Realm.getDefaultInstance();

    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        final Context context = getContext();
        SnapHelper snapHelper = new PagerSnapHelper();
        newsRV = view.findViewById(R.id.fragment_news_rv);
        newsRV.setHasFixedSize(true);
        newsRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        fetchStories();
        return view;


    }

    private void readCachedNews() {
        RealmResults<NewsModel> data = realmInstance.where(NewsModel.class)
                .sort("mongoID", Sort.DESCENDING)
                .findAll();
        if (data.size() == 0) {
            Log.e("NewsFragment","Setting up worker to fetch news");
            //Replace the previously scheduled task so that it runs as soon as there is a network connection
            MainApplication.setUpNewsUpdateWorker();
        } else {
            Log.e("NewsFragment", "Reading data from memory, have " + data.size() + " items");
        }
        if (getActivity() != null) {
            final NewsAdapter mAdapter = new NewsAdapter(data);
            newsRV.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("NewsFragment", "NewsFragment About To Be Destroyed, closing Realm");
        realmInstance.close();

    }

    public void fetchStories()
    {
        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("token", AppConstants.token);
        final String newsURL = getResources().getString(R.string.apiBaseURL)+"stories/view";
        storiesQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, newsURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Stories", response);
                try {
                List<StoriesModel> storiesModelArrayList = new ArrayList<>();
                JSONArray storiesJSONArray = new JSONObject(response).getJSONArray("data");
                for(int i =0; i<storiesJSONArray.length();i++) {

                        storiesModelArrayList.add(new StoriesModel(
                                storiesJSONArray.getJSONObject(i).getString("contentTitle"),
                                storiesJSONArray.getJSONObject(i).getString("contentPic"),
                                storiesJSONArray.getJSONObject(i).getString("contentSummary"),
                                storiesJSONArray.getJSONObject(i).getString("contentAuthor"),
                                storiesJSONArray.getJSONObject(i).getString("contentType"),
                                storiesJSONArray.getJSONObject(i).getString("contentMarkdownLink")
                        ));

                    }
                    StoriesAdapter storiesAdapter = new StoriesAdapter(storiesModelArrayList);
                    newsRV.setAdapter(storiesAdapter);
//                    Log
                }
                catch (JSONException e) {
                    Log.d("NewsFragment",e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Stories Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        storiesQueue.add(request);

    }
}