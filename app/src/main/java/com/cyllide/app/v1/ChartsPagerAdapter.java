package com.cyllide.app.v1;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cyllide.app.v1.FuturesChartsFragment;
import com.cyllide.app.v1.OptionsChartsFragment;
import com.cyllide.app.v1.OverviewChartsFragment;
import com.cyllide.app.v1.intro.ForumChartsFragment;
import com.cyllide.app.v1.intro.NewsAndResearchChartsFragment;

public class ChartsPagerAdapter extends FragmentPagerAdapter {



    int tabCount;

    public ChartsPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                OverviewChartsFragment newsFragment = new OverviewChartsFragment();
                return newsFragment;
            case 1:
                FuturesChartsFragment storiesFragment = new FuturesChartsFragment();
                return storiesFragment;
            case 2:
                OptionsChartsFragment optionsChartsFragment = new OptionsChartsFragment();
                return optionsChartsFragment;
            case 3:
                NewsAndResearchChartsFragment newsAndResearchChartsFragment = new NewsAndResearchChartsFragment();
                return newsAndResearchChartsFragment;
            case 4:
                ForumChartsFragment forumChartsFragment = new ForumChartsFragment();
                return  forumChartsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}