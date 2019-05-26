package com.cyllide.app.v1.stories;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class NewsFragment extends Fragment{
    private String newsURL;
    private RequestQueue requestQueue;
    private RecyclerView newsRV;
    private Map<String, String> newsMap= new ArrayMap<String, String>();
    private LinearLayout loading;
    private int pageNo = 1;
    boolean isLoading = false;
    List<NewsModel> data = new ArrayList<>();
    NewsAdapter mAdapter = new NewsAdapter(data);

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news, container, false);
        final Activity activity = getActivity();
        final Context context = getContext();
        loading = view.findViewById(R.id.activity_stories_news_loading_layout);
        newsRV = view.findViewById(R.id.fragment_news_rv);
        newsRV.setAdapter(mAdapter);
        newsRV.setHasFixedSize(true);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(newsRV);
        newsRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


        newsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == data.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });



        Fresco.initialize(context);
        jsonParse(pageNo);
        return view;
    }


    private void loadMore() {
        data.add(null);
        mAdapter.notifyItemInserted(data.size() - 1);


        jsonParse(++pageNo);

    }


    private void jsonParse(final Integer pageNo) {
        requestQueue= Volley.newRequestQueue(getContext());
        newsMap.put("pageno", pageNo.toString());
        newsURL = getContext().getResources().getString(R.string.newsApiBaseURL);


        StringRequest request = new StringRequest(Request.Method.GET, newsURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONObject(response).getJSONArray("data");
//                    List<NewsModel> data = new ArrayList<>(10);
                    if(pageNo>1){
                        data.remove(data.size() - 1);
                    }
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject article = jsonArray.getJSONObject(i);
                        data.add(
                                new NewsModel(
                                        article.getString("title"),
                                        article.getString("image"),
                                        article.getString("pubDate"),
                                        "Economic Times",
                                        article.getString("description"),
                                        article.getString("link"),
                                        "Economic Times"
                                )
                        );
                    }
                    if (getActivity() != null) {
                        mAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, "getActivity() returned null in onStart()");
                    }
//                    NewsAdapter newsAdapter= new NewsAdapter(data);
//                    newsRV.setAdapter(newsAdapter);
                    mAdapter.notifyDataSetChanged();
                    isLoading = false;
                    loading.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Log.e(TAG, e.toString()); ;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return newsMap;
            }
        };
        requestQueue.add(request);
    }
}