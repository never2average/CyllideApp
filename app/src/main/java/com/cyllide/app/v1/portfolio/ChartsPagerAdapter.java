package com.cyllide.app.v1.portfolio;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.fragment.app.FragmentPagerAdapter;

import com.cyllide.app.v1.portfolio.tabs.StatsFragment;
import com.cyllide.app.v1.portfolio.tabs.IncomeStatementFragment;
import com.cyllide.app.v1.portfolio.tabs.SummaryFragment;
import com.cyllide.app.v1.portfolio.tabs.CashFlowFragment;
import com.cyllide.app.v1.portfolio.tabs.BalanceSheetFragment;

public class ChartsPagerAdapter extends FragmentPagerAdapter {



    int tabCount;
    private int mCurrentPosition = -1;


    public ChartsPagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SummaryFragment newsFragment = new SummaryFragment();
                return newsFragment;
            case 1:
                StatsFragment storiesFragment = new StatsFragment();
                return storiesFragment;
            case 2:
                IncomeStatementFragment incomeStatementFragment = new IncomeStatementFragment();
                return incomeStatementFragment;
            case 3:
                CashFlowFragment cashFlowFragment = new CashFlowFragment();
                return cashFlowFragment;

            case 4:
                BalanceSheetFragment balanceSheetFragment = new BalanceSheetFragment();
                return balanceSheetFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        if (position != mCurrentPosition) {
            Fragment fragment = (Fragment) object;
            CustomViewPager pager = (CustomViewPager) container;
            if (fragment != null && fragment.getView() != null) {
                mCurrentPosition = position;
                pager.measureCurrentView(fragment.getView());
            }
        }
    }
}