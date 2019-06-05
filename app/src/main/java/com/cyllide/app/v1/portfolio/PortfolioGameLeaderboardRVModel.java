package com.cyllide.app.v1.portfolio;

public class PortfolioGameLeaderboardRVModel {
    private String playerName;
    private String playerProfileURL;
    private int playerNumDaysStreak;

    public PortfolioGameLeaderboardRVModel(String playerName, String playerProfileURL, int playerNumDaysStreak) {
        this.playerName = playerName;
        this.playerProfileURL = playerProfileURL;
        this.playerNumDaysStreak = playerNumDaysStreak;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerProfileURL() {
        return playerProfileURL;
    }

    public void setPlayerProfileURL(String playerProfileURL) {
        this.playerProfileURL = playerProfileURL;
    }

    public int getPlayerNumDaysStreak() {
        return playerNumDaysStreak;
    }

    public void setPlayerNumDaysStreak(int playerNumDaysStreak) {
        this.playerNumDaysStreak = playerNumDaysStreak;
    }
}
