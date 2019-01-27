package com.example.kartikbhardwaj.bottom_navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.kartikbhardwaj.bottom_navigation.Contests.WeeklyActivity;
//import com.example.kartikbhardwaj.bottom_navigation.stories.NewsData;
import com.example.kartikbhardwaj.bottom_navigation.stories.NewsData;
import com.example.kartikbhardwaj.bottom_navigation.stories.StoriesActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    SimpleDraweeView stories, contest, portfolios, quiz;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getContext());
        return inflater.inflate(R.layout.home_fragment,null);
    }
    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stories = view.findViewById(R.id.stories);
        contest = view.findViewById(R.id.contest);
        portfolios = view.findViewById(R.id.portfolios);
        quiz = view.findViewById(R.id.quiz);
        final Context context = getContext();

    }
    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();
        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),StoriesActivity.class);
                getContext().startActivity(intent);
            }
        });
        contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),WeeklyActivity.class);
                startActivity(intent);
                }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),NewsData.class);
                getContext().startActivity(intent);
            }
        });
        }

}
