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
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


public class StoriesFragment extends Fragment {

    private RecyclerView newsRV;
    RequestQueue storiesQueue;
    LinearLayout loading;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        final Context context = getContext();
        newsRV = view.findViewById(R.id.fragment_news_rv);
        loading = view.findViewById(R.id.activity_stories_news_loading_layout);
        newsRV.setHasFixedSize(true);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(newsRV);
        newsRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        fetchStories(container);
        return view;


    }


    public void fetchStories(final ViewGroup container) {
        final Map<String, String> mHeaders = new ArrayMap<String, String>();
        mHeaders.put("token", AppConstants.token);
        final String newsURL = getResources().getString(R.string.apiBaseURL) + "stories/view";
        storiesQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, newsURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<StoriesModel> storiesModelArrayList = new ArrayList<>();
                    JSONArray storiesJSONArray = new JSONObject(response).getJSONArray("data");
                    for (int i = 0; i < storiesJSONArray.length(); i++) {

                        storiesModelArrayList.add(new StoriesModel(
                                storiesJSONArray.getJSONObject(i).getJSONObject("_id").getString("$oid"),
                                storiesJSONArray.getJSONObject(i).getString("contentTitle"),
                                storiesJSONArray.getJSONObject(i).getString("contentPic"),
                                storiesJSONArray.getJSONObject(i).getString("contentSummary"),
                                storiesJSONArray.getJSONObject(i).getString("contentAuthor"),
                                storiesJSONArray.getJSONObject(i).getString("contentType"),
                                storiesJSONArray.getJSONObject(i).getString("contentColor"),
                                storiesJSONArray.getJSONObject(i).getString("contentMarkdownLink")
                        ));

                    }
                    StoriesAdapter storiesAdapter = new StoriesAdapter(storiesModelArrayList);
                    newsRV.setAdapter(storiesAdapter);
                    loading.setVisibility(View.GONE);


                } catch (JSONException e) {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        storiesQueue.add(request);

    }
}