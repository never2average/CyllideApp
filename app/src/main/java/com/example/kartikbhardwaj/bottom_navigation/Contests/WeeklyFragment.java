package com.example.kartikbhardwaj.bottom_navigation.Contests;

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


public class WeeklyFragment extends Fragment {
    private RecyclerView weeklyRV;

    String weeklyTitle[]={"News1","News2","News3"};
    String weeklyThumbnailSource[]={"https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg"};
    String weeklyDescription[]={"General Description shown in the other activity","General Description shown in the other activity","General Description shown in the other activity"};
    private List<WeeklyModel> dummyData() {
        List<WeeklyModel> data = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            data.add(new WeeklyModel(weeklyTitle[i],weeklyThumbnailSource[i],weeklyDescription[i]));
        }//data is the list of objects to be set in the list item
        return data;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weekly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weeklyRV = view.findViewById(R.id.fragment_weekly_rv);
        final Context context = getContext();
        weeklyRV.setHasFixedSize(true);
        weeklyRV.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();
            final Context context = getContext();
            Fresco.initialize(context);
            List<WeeklyModel> WeeklyPart = dummyData();
        if (activity != null) {
            final WeeklyAdapter mAdapter = new WeeklyAdapter(WeeklyPart);
            weeklyRV.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }
    }
}
