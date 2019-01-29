package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.kartikbhardwaj.bottom_navigation.R;


public class AvailableStocksFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PortfolioActivity portfolioActivity =(PortfolioActivity) getActivity();
        //portfolioActivity.toolBarTitle.setText(R.string.Pending_Orders_title);
//        if(portfolioActivity.rvStatus.equals("1"))
//        {
//            portfolioActivity.toolBarTitle.setText(portfolioActivity.stockName);
//            portfolioActivity.rvStatus="0";
//
//
//        }
//
//        if(portfolioActivity.buttonStatus.equals("1"))
//        {   portfolioActivity.buttonStatus="0";
//            portfolioActivity.toolBarTitle.setText(portfolioActivity.stockName);
//
//        }

        return inflater.inflate(R.layout.available_stocks_fragment,null);

    }
}
