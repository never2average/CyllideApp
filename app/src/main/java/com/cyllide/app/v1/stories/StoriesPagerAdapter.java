package com.cyllide.app.v1.stories;



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

        StoriesFragment storiesFragment = new StoriesFragment();
        return storiesFragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
