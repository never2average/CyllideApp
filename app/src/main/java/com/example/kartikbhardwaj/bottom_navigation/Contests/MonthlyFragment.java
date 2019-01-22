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

public class MonthlyFragment extends Fragment{
    private RecyclerView monthlyRV;

    String monthlyTitle[]={"Monthly1","Monthly2","Monthly3"};
    String monthlyThumbnailSource[]={"https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg","https://www.desktopbackground.org/download/1366x768/2014/08/21/812557_civil-engineering-wallpapers_1600x1200_h.jpg"};
    String monthlyDate[]={"2018-09-15","2018-09-21","2018-12-18"};
    String monthlySource[]={"Times","Of","India"};
    String monthlyDescription[]={"General Description shown in the other activity","General Description shown in the other activity","General Description shown in the other activity"};

    private List<MonthlyModel> dummyData() {
        List<MonthlyModel> data = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            data.add(new MonthlyModel(monthlyTitle[i],monthlyThumbnailSource[i],monthlyDate[i],monthlySource[i],monthlyDescription[i]));
        }//data is the list of objects to be set in the list item
        return data;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monthly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        monthlyRV = view.findViewById(R.id.fragment_monthly_rv);
        final Context context = getContext();
        monthlyRV.setHasFixedSize(true);
        monthlyRV.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();
        final Context context = getContext();
        Fresco.initialize(context);
        List<MonthlyModel> monthly = dummyData();
        if (activity != null) {
            final MonthlyAdapter mAdapter = new MonthlyAdapter(monthly);
            monthlyRV.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }
    }
}
