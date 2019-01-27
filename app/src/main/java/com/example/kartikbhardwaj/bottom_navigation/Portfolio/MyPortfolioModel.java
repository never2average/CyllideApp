package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import android.widget.TextView;

public class MyPortfolioModel {

   private String portfolioname;
    private String returns;

    public MyPortfolioModel(String portfolioname, String returns) {
        this.portfolioname = portfolioname;
        this.returns = returns;
    }

    public String getPortfolioname() {

        return portfolioname;
    }

    public void setPortfolioname(String portfolioname) {
        this.portfolioname = portfolioname;
    }

    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }
}
