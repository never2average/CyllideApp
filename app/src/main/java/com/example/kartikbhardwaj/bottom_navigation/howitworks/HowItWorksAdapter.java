package com.example.kartikbhardwaj.bottom_navigation.howitworks;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HowItWorksAdapter extends FragmentPagerAdapter {

    public HowItWorksAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return Fragment0.newInstance();
            case 1:
                return Fragment1.newInstance();
            case 2:
                return Fragment2.newInstance();
            case 3:
                return Fragment3.newInstance();
            case 4:
                return Fragment4.newInstance();
            case 5:
                return Fragment5.newInstance();
            case 6:
                return Fragment6.newInstance();
            case 7:
                return Fragment7.newInstance();
            default:
                return Fragment0.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 8;
    }
}
