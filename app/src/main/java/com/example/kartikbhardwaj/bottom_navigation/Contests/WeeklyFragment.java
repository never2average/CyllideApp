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

public class WeeklyFragment extends Fragment{
    private RecyclerView weeklyRV;

    String weeklyName[]={"weekly1","weekly2","weekly3"};
    String weeklyCapacity[]={"2093","3431", "1234"};
    String weeklyWinners[]={"10", "20", "30"};
    String weeklyTimeRemaining[]={"12hrs","10hrs","1hr"};
    String weeklyProgress[]={"33", "60", "80"};
    Boolean weeklyPremium[] = {true, true, false};
    String weeklyParticipants[] = {"375 left out of 500 participants", "375 left out of 500 participants", "375 left out of 500 participants"};

    private List<WeeklyModel> dummyData() {
        List<WeeklyModel> data = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            data.add(new WeeklyModel(weeklyName[i], weeklyCapacity[i], weeklyWinners[i], weeklyTimeRemaining[i], weeklyProgress[i], weeklyParticipants[i], weeklyPremium[i]));
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
        List<WeeklyModel> weekly = dummyData();
        if (activity != null) {
            final WeeklyAdapter mAdapter = new WeeklyAdapter(weekly);
            weeklyRV.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }
    }
}
