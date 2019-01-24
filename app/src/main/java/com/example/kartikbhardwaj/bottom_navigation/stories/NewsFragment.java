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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class NewsFragment extends Fragment{

    private RecyclerView newsRV;
    ArrayList<String> newsTitle=new ArrayList<>();
    ArrayList<String> newsThumbnailSource=new ArrayList<>();
    ArrayList<String> newsDate=new ArrayList<>();
    ArrayList<String> newsDescription=new ArrayList<>();
    ArrayList<String> newsSource=new ArrayList<>();
    ArrayList<String> url=new ArrayList<>();
    ArrayList<String> author=new ArrayList<>();

<<<<<<< HEAD
    String newsTitle[]={"News1","News2","News3"};
    String newsThumbnailSource[]={"https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg"};
    String newsDate[]={"2018-09-15","2018-09-21","2018-12-18"};
    String newsSource[]={"Times","Of","India"};
    String url[]={"www.google.com","www.google.com","www.google.com"};
    String newsDescription[]={"General Description shown in the other activity which is uselessly long just to show that it is working fine and withourt any problems.","General Description shown in the other activity just to show that it is working fine and withourt any problems.","General Description shown in the other activity just to show that it is working fine and withourt any problems."};
    String newsAuthor[]={"Author 1","Author 2","Author 3"};

    private List<NewsModel> dummyData() {
        List<NewsModel> data = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            data.add(new NewsModel(newsTitle[i],newsThumbnailSource[i],newsDate[i],newsSource[i],newsDescription[i],url[i],newsAuthor[i]));
=======

    private List<NewsModel> dummyData() {
        List<NewsModel> data = new ArrayList<>(12);
        for (int i = 0; i < newsTitle.size(); i++) {
            data.add(new NewsModel(newsTitle.get(i),newsThumbnailSource.get(i),newsDate.get(i),newsSource.get(i),newsDescription.get(i),url.get(i)));
>>>>>>> upstream/master
        }//data is the list of objects to be set in the list item
        return data;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        String newsURL = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=f6bddf738e64468280f0a7173b265b41";

        StringRequest request = new StringRequest(newsURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonResponse= null;
                try {

                    Log.d("abc",response);
                int arrlen=jsonResponse.getJSONArray("articles").length();
                JSONArray responseArray=jsonResponse.getJSONArray("articles");
                for(int i=0;i<arrlen;i++)
                {
                    newsTitle.add(responseArray.getJSONObject(i).getJSONObject("title").toString());
                    newsDescription.add(responseArray.getJSONObject(i).getJSONObject("description").toString());
                    url.add(responseArray.getJSONObject(i).getJSONObject("url").toString());
                    author.add(responseArray.getJSONObject(i).getJSONObject("author").toString());
                    newsDate.add(responseArray.getJSONObject(i).getJSONObject("publishedAt").toString());
                    newsSource.add(responseArray.getJSONObject(i).getJSONObject("source").getJSONObject("name").toString());
                    newsThumbnailSource.add(responseArray.getJSONObject(i).getJSONObject("urlToImage").toString());
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Something Went Wrong!",Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);




        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsRV = view.findViewById(R.id.fragment_news_rv);
        final Context context = getContext();
        newsRV.setHasFixedSize(true);
        newsRV.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();
        final Context context = getContext();
        Fresco.initialize(context);
        List<NewsModel> news = dummyData();
        if (activity != null) {
            final NewsAdapter mAdapter = new NewsAdapter(news);
            newsRV.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }
    }
}