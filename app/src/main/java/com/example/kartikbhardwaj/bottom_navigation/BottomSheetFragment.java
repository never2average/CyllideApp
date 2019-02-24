package com.example.kartikbhardwaj.bottom_navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.kartikbhardwaj.bottom_navigation.howitworks.HowItWorksFragment;
import com.example.kartikbhardwaj.bottom_navigation.notification.NotificationActivity;
import com.example.kartikbhardwaj.bottom_navigation.stories.StoriesActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    LinearLayout profile,home,referals,stats,hoeitworks,notification;
    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_bottom_sheet, container, false);
        profile = view.findViewById(R.id.profile);
        home = view.findViewById(R.id.home);
        referals = view.findViewById(R.id.referrals);
        stats = view.findViewById(R.id.stats);
        notification=view.findViewById(R.id.notification);
        hoeitworks = view.findViewById(R.id.how_it_works);
        final Context context = getContext();

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), NotificationActivity.class);
                getContext().startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), Profile_Activity.class);
                getContext().startActivity(intent);
            }
        });

        referals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapFragment();
            }
        });


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swaphomeFragment();
            }
        });
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapstatFragment();
            }
        });
        hoeitworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swaphiwFragment();
            }
        });


        return view;


    }

    private void swaphiwFragment(){
        HowItWorksFragment newGamefragment = new HowItWorksFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void swapstatFragment(){
        StatsFragment newGamefragment = new StatsFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void swapFragment(){
        ReferralFragment newGamefragment = new ReferralFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void swaphomeFragment(){
        HomeFragment newGamefragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, newGamefragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }
}