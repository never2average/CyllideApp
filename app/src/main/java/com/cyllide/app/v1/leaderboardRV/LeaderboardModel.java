package com.cyllide.app.v1.leaderboardRV;


public class LeaderboardModel {
    private String name;
    private int rank;
    private double returns;
    private String profileURL;

    public String getPortfolioOwner() {
        return portfolioOwner;
    }

    public void setPortfolioOwner(String portfolioOwner) {
        this.portfolioOwner = portfolioOwner;
    }

    public LeaderboardModel(String name, int rank, double returns, String profileURL, String portfolioOwner) {
        this.name = name;
        this.rank = rank;
        this.returns = returns;
        this.profileURL = profileURL;
        this.portfolioOwner = portfolioOwner;
    }

    private String portfolioOwner;

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }


    public double getReturns() {
        return returns;
    }

    public void setReturns(double returns) {
        this.returns = returns;
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

