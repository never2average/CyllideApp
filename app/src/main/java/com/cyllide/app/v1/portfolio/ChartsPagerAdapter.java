package com.cyllide.app.v1.portfolio;


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
}