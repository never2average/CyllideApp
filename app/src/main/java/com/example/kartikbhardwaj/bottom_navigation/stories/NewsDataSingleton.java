package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NewsDataSingleton {
    private static NewsDataSingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private NewsDataSingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NewsDataSingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NewsDataSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}