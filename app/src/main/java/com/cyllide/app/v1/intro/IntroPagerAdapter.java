package com.cyllide.app.v1.intro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import static java.security.AccessController.getContext;

public class IntroPagerAdapter extends FragmentPagerAdapter {

    int[] imageResources = {R.drawable.stories_intro,R.drawable.contest_intro,R.drawable.quiz_correct_intro,R.drawable.quiz_wins_intro};


    public IntroPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        IntroScreenFragment fragment = new IntroScreenFragment();
        Bundle args =new Bundle();
        args.putInt(IntroScreenFragment.ARG_IMAGE, imageResources[position]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return imageResources.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
