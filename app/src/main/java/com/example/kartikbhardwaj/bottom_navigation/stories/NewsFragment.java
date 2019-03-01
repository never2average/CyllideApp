package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.backends.pipeline.Fresco;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.content.ContentValues.TAG;

public class NewsFragment extends Fragment{

    RequestQueue requestQueue;
    private RecyclerView newsRV;
    ArrayList<String> newsTitle=new ArrayList<>();
    ArrayList<String> newsThumbnailSource=new ArrayList<>();
    ArrayList<String> newsDate=new ArrayList<>();
    ArrayList<String> newsDescription=new ArrayList<>();
    ArrayList<String> newsSource=new ArrayList<>();
    ArrayList<String> url=new ArrayList<>();
    ArrayList<String> author=new ArrayList<>();

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news, container, false);
        final Activity activity = getActivity();
        final Context context = getContext();
        newsRV = view.findViewById(R.id.fragment_news_rv);
        newsRV.setHasFixedSize(true);
        newsRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        Fresco.initialize(context);
        if (newsDate.size()==0){
//            jsonParse();
            readCachedNews();
        }
        return view;
    }

    private void readCachedNews() {
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<NewsModel> data = realm.where(NewsModel.class)
                            .sort("newsDate", Sort.DESCENDING)
                            .findAll();
                    if(data.size()==0){
                        jsonParse();
                    }
                    if (getActivity() != null) {
                        final NewsAdapter mAdapter = new NewsAdapter(data);
                        newsRV.setAdapter(mAdapter);
                    } else {
                        Log.e(TAG, "getActivity() returned null in onStart()");
                    }
                    NewsAdapter newsAdapter= new NewsAdapter(data);
                    newsRV.setAdapter(newsAdapter);
                }
            });
        } finally {
            realm.close();
        }
    }


    private void jsonParse() {
        requestQueue= Volley.newRequestQueue(getContext());
        final String newsURL = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=f6bddf738e64468280f0a7173b265b41";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, newsURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject article = jsonArray.getJSONObject(i);

                                newsTitle.add(article.getString("title"));
                                newsThumbnailSource.add(article.getString("urlToImage"));
                                newsSource.add(article.getJSONObject("source").getString("name"));
                                url.add(article.getString("url"));
                                author.add(article.getString("author"));
                                newsDescription.add(article.getString("description"));
                                newsDate.add(article.getString("publishedAt"));

                            }

                            for (int i = 0; i < newsTitle.size(); i++) {
                                addToRealm(new NewsModel(newsTitle.get(i),newsThumbnailSource.get(i),newsDate.get(i),newsSource.get(i),newsDescription.get(i),url.get(i),author.get(i)));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }

    private void addToRealm(final NewsModel newsArticle){
        Realm realm = Realm.getDefaultInstance();
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(newsArticle);
                }
            });
        } finally {
            realm.close();
        }
    }


}