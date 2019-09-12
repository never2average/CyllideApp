package com.cyllide.app.beta.leaderboardRV;


public class LeaderboardModel {
    private String name;
    private int rank;
    private double returns;
    private String profileURL;
    private boolean isMine;

    public String getPortfolioID() {
        return portfolioID;
    }

    public void setPortfolioID(String portfolioID) {
        this.portfolioID = portfolioID;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public LeaderboardModel(String name, int rank, double returns, String profileURL, String portfolioID, String portfolioOwner, boolean isMine) {
        this.name = name;
        this.rank = rank;
        this.returns = returns;
        this.profileURL = profileURL;
        this.portfolioID = portfolioID;
        this.portfolioOwner = portfolioOwner;
        this.isMine = isMine;
    }

    private String portfolioID;

    public String getPortfolioOwner() {
        return portfolioOwner;
    }

    public void setPortfolioOwner(String portfolioOwner) {
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

