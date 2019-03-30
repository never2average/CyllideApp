package com.example.kartikbhardwaj.bottom_navigation.leaderboardRV;


public class LeaderboardModel {
    private String name;
    private int rank;
    private double returns;

    public double getReturns() {
        return returns;
    }

    public void setReturns(double returns) {
        this.returns = returns;
    }

    public LeaderboardModel(String name, int rank,double returns) {
        this.name   = name;
        this.rank = rank;
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

