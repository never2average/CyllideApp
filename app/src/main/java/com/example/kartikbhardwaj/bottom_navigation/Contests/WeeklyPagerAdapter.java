package com.example.kartikbhardwaj.bottom_navigation.Contests;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class WeeklyPagerAdapter extends FragmentPagerAdapter {



    int tabCount;

    public WeeklyPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                WeeklyFragment weeklyFragment = new WeeklyFragment();
                return weeklyFragment;
            case 1:
                MonthlyFragment monthlyFragment = new MonthlyFragment();
                return monthlyFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
