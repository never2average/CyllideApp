package com.cyllide.app.v1.stories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyllide.app.v1.R;

import java.util.ArrayList;

public class ShortsFragment extends Fragment {

private RecyclerView shortsRV;
ArrayList<ShortsModal> list =new ArrayList<ShortsModal>();
    ShortsAdapter  adapter=new ShortsAdapter(list);


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
            list.add(new ShortsModal(name[i],descrip[i],descrip1[1]));

        }
        shortsRV.setAdapter(adapter);
        shortsRV.setHasFixedSize(true);
        shortsRV.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        return view;
    }



}
