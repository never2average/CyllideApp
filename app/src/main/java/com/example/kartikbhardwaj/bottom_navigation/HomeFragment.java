package com.example.kartikbhardwaj.bottom_navigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;


//import com.example.kartikbhardwaj.bottom_navigation.stories.NewsData;
import com.example.kartikbhardwaj.bottom_navigation.contests.MonthlyActivity;
import com.example.kartikbhardwaj.bottom_navigation.portfolio.MyPortfolio;
import com.example.kartikbhardwaj.bottom_navigation.forum.ForumActivity;
import com.example.kartikbhardwaj.bottom_navigation.quiz.QuizRulesActivity;
import com.example.kartikbhardwaj.bottom_navigation.stories.StoriesActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import com.google.android.material.card.MaterialCardView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HomeFragment extends Fragment {
    Calendar startTime =Calendar.getInstance();
    Dialog quizPopup;
    TextView timer;





    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    MaterialCardView stories, contest, portfolios, quiz, forum;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getContext());
        return inflater.inflate(R.layout.home_fragment,null);
    }
    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        startTime.set(Calendar.HOUR_OF_DAY,startTime.get(Calendar.HOUR_OF_DAY)+2);



        super.onViewCreated(view, savedInstanceState);
        stories = view.findViewById(R.id.storiescard);
        contest = view.findViewById(R.id.contestcard);
        portfolios = view.findViewById(R.id.portfoliocard);
        quiz = view.findViewById(R.id.quizcard);
        forum = view.findViewById(R.id.forumcard);
        final Context context = getContext();
        quizPopup=new Dialog(view.getContext());


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

                ConnectivityManager conMgr =(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo=conMgr.getActiveNetworkInfo();

                if(netInfo ==null||!netInfo.isConnected()||!netInfo.isAvailable())
                {
                    Toast.makeText(getContext(),"Check your Network Connection",Toast.LENGTH_LONG).show();

                } else {
                    Intent intent=new Intent(getContext(), MonthlyActivity.class);
                startActivity(intent);
                }
                }
        });
        portfolios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager conMgr =(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo=conMgr.getActiveNetworkInfo();

                if(netInfo ==null||!netInfo.isConnected()||!netInfo.isAvailable())
                {
                    Toast.makeText(getContext(),"Poor Network Connection",Toast.LENGTH_LONG).show();

                } else {
                    Intent portfolioIntent =new Intent(getContext(),MyPortfolio.class);
                    startActivity(portfolioIntent);


                }

            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("AUTHENTICATION",Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "Not found!");

                ConnectivityManager conMgr =(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo=conMgr.getActiveNetworkInfo();


                if(netInfo ==null||!netInfo.isConnected()||!netInfo.isAvailable())
                {
                    Toast.makeText(getContext(),"Check your Network Connection",Toast.LENGTH_LONG).show();


                } else {
                    Intent intent;
//                if(token.equals("Not found!")){
//                    intent = new Intent(getContext(), PhoneAuth.class);
//                }
//                else{
                    intent = new Intent(getContext(), QuizRulesActivity.class);
//                }

                getContext().startActivity(intent);


                }


            }

        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ConnectivityManager conMgr =(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo=conMgr.getActiveNetworkInfo();

                if(netInfo ==null||!netInfo.isConnected()||!netInfo.isAvailable())
                {
                    Toast.makeText(getContext()," Check your Network Connection",Toast.LENGTH_LONG).show();

                } else {
                    Intent intent = new Intent(getContext(), ForumActivity.class);
                getContext().startActivity(intent);}



            }
        });
        }

}
