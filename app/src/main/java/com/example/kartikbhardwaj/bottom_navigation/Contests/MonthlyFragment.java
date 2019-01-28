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

    String monthlyName[]={"Monthly1","Monthly2","Monthly3"};
    String monthlyCapacity[]={"2093","3431", "1234"};
    String monthlyWinners[]={"10", "20", "30"};
    String monthlyTimeRemaining[]={"12hrs","10hrs","1hr"};
    String monthlyProgress[]={"33", "60", "80"};
    String monthlyParticipants[] = {"375 left out of 500 participants", "375 left out of 500 participants", "375 left out of 500 participants"};
    Boolean monthlyPremium[] = {true, true, false};

    private List<MonthlyModel> dummyData() {
        List<MonthlyModel> data = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            data.add(new MonthlyModel(monthlyName[i], monthlyCapacity[i], monthlyWinners[i], monthlyTimeRemaining[i], monthlyProgress[i], monthlyParticipants[i], monthlyPremium[i]));
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
