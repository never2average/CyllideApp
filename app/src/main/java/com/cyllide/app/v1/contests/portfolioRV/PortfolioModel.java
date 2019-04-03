package com.cyllide.app.v1.contests.portfolioRV;

import androidx.annotation.NonNull;

public class PortfolioModel {
    private String portfolioname;
    private double returns;
    private String id;

    public PortfolioModel(String portfolioname, double returns, String id) {
        this.portfolioname = portfolioname;
        this.returns = returns;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getPortfolioName() {

        return portfolioname;
    }

    public void setPortfolioName(String portfolioname) {
        this.portfolioname = portfolioname;
    }

    public double getReturns() {
        return returns;
    }

    public void setReturns(double returns) {
        this.returns = returns;
    }

    @NonNull
    @Override
    public String toString() {
        return portfolioname;
    }
}
