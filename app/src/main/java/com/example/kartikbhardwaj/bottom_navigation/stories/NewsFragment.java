package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class NewsFragment extends Fragment{
    private RecyclerView newsRV;

    String newsTitle[]={"News1","News2","News3"};
    String newsThumbnailSource[]={"https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg"};
    String newsDate[]={"2018-09-15","2018-09-21","2018-12-18"};
    String newsSource[]={"Times","Of","India"};
    String url[]={"www.google.com","www.google.com","www.google.com"};
    String newsDescription[]={"General Description shown in the other activity which is uselessly long just to show that it is working fine and withourt any problems.","General Description shown in the other activity just to show that it is working fine and withourt any problems.","General Description shown in the other activity just to show that it is working fine and withourt any problems."};

    private List<NewsModel> dummyData() {
        List<NewsModel> data = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            data.add(new NewsModel(newsTitle[i],newsThumbnailSource[i],newsDate[i],newsSource[i],newsDescription[i],url[i]));
        }//data is the list of objects to be set in the list item
        return data;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
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
