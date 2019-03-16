package com.example.kartikbhardwaj.bottom_navigation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.example.kartikbhardwaj.bottom_navigation.Contests.WeeklyActivity;
//import com.example.kartikbhardwaj.bottom_navigation.stories.NewsData;
import com.example.kartikbhardwaj.bottom_navigation.Portfolio.PortfolioActivity;
import com.example.kartikbhardwaj.bottom_navigation.phone_authentication.PhoneAuth;
import com.example.kartikbhardwaj.bottom_navigation.quiz.QuizRulesActivity;
import com.example.kartikbhardwaj.bottom_navigation.stories.StoriesActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import android.view.ViewGroup;
import android.widget.TextView;

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

    CardView stories, contest, portfolios, quiz;
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
                Intent intent=new Intent(getContext(),WeeklyActivity.class);
                startActivity(intent);
                }
        });
        portfolios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), PortfolioActivity.class);
                startActivity(intent);
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("AUTHENTICATION",Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token", "Not found!");
                Intent intent;
                if(token.equals("Not found!")){
                    intent = new Intent(getContext(), PhoneAuth.class);
                }
                else{
                    intent = new Intent(getContext(), QuizRulesActivity.class);
                }

                getContext().startActivity(intent);
                }

        });
        }

}
