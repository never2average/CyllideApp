package com.example.kartikbhardwaj.bottom_navigation.stories;



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
                NewsFragment newsFragment = new NewsFragment();
                return newsFragment;
            case 1:
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
