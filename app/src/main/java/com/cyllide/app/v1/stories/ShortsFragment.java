package com.cyllide.app.v1.stories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShortsFragment extends Fragment {

private RecyclerView shortsRV;
ArrayList<ShortsModel> list =new ArrayList<ShortsModel>();
    ShortsAdapter  adapter=new ShortsAdapter(list);
    RequestQueue requestQueue;
    String requestURL;
    Map<String,String> articleDataMap = new HashMap<>();


    public ShortsFragment() {
        // Required empty public constructor
    }
    String name[]={"kartik","anshuman","priyesh","rohit","aditya","prasanna"};
    String descrip[]={"fghkdfjgkfgj","kskfksjhgdffdj","sfsdjsafjsf","kkgfslfghskfjg","kkfgkfjgsakfgj","slkdfskdfljsfdkgkj"};
    String descrip1[]={"fghkdfjgkfgj","kskfksjhgdffdj","sfsdjsafjsf","kkgfslfghskfjg","kkfgkfjgsakfgj","slkdfskdfljsfdkgkj"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_shorts, container, false);
        shortsRV=view.findViewById(R.id.shorts_rv);
        for(int i=0;i<6;i++){
            list.add(new ShortsModel(name[i],descrip[i],descrip1[1]));

        }
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(shortsRV);
        shortsRV.setAdapter(adapter);
        fetchArticleData();
        shortsRV.setHasFixedSize(true);
        shortsRV.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }

    void fetchArticleData(){
        requestQueue = Volley.newRequestQueue(getContext());
        requestURL = getResources().getString(R.string.apiBaseURL)+"inshorts";
        articleDataMap.put("token", AppConstants.token);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("RESPONSE",response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders(){return articleDataMap;}
        };
        requestQueue.add(stringRequest);
    }



}
