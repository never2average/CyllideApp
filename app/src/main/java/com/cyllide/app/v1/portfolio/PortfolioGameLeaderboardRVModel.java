package com.cyllide.app.v1.portfolio;

public class PortfolioGameLeaderboardRVModel {
    private String playerName;
    private String playerProfileURL;
    private Integer playerNumDaysStreak;
    private String playerID;

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public PortfolioGameLeaderboardRVModel(String playerName, String playerProfileURL, int playerNumDaysStreak, String playerID) {
        this.playerName = playerName;
        this.playerProfileURL = playerProfileURL;
        this.playerNumDaysStreak = playerNumDaysStreak;
        this.playerID = playerID;
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
