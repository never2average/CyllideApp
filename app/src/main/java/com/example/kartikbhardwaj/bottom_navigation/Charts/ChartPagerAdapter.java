package com.example.kartikbhardwaj.bottom_navigation.Charts;

import android.os.Debug;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ChartPagerAdapter extends FragmentPagerAdapter {

    int tabCount;


    public ChartPagerAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        tabCount = numberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Summary1Fragment summary1Fragment = new Summary1Fragment();
                Log.e("Made summary1Fragment","summary1Frag");
                return summary1Fragment;

            default:
                Summary1Fragment summary2Fragment = new Summary1Fragment();
                return summary2Fragment;
        }

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
