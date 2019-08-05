package com.cyllide.app.v1.stories;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentPagerAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StoriesPagerAdapter extends FragmentPagerAdapter {



    int tabCount;

    public StoriesPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ShortsFragment shortsFragment = new ShortsFragment();
                return shortsFragment;
            case 1:
                NewsFragment newsFragment = new NewsFragment();
                return newsFragment;
            case 2:
                StoriesFragment storiesFragment = new StoriesFragment();
                return storiesFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}