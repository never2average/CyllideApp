package com.example.kartikbhardwaj.bottom_navigation.contests;

public class WeeklyModel {
    private String weeklyName,weeklyCapacity,weeklyWinners,weeklyTimeRemaining, weeklyProgress, weeklyParticipantsRemaining;
    private Boolean isPremium;

    public WeeklyModel(String weeklyName, String weeklyCapacity, String weeklyWinners, String weeklyTimeRemaining, String weeklyProgress, String weeklyParticipantsRemaining, Boolean isPremium)
    {
        this.weeklyCapacity=weeklyCapacity;
        this.weeklyWinners=weeklyWinners;
        this.weeklyName=weeklyName;
        this.weeklyTimeRemaining=weeklyTimeRemaining;
        this.weeklyProgress=weeklyProgress;
        this.weeklyParticipantsRemaining=weeklyParticipantsRemaining;
        this.isPremium = isPremium;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    public String getweeklyName() {
        return weeklyName;
    }

    public String getweeklyCapacity() {
        return weeklyCapacity;
    }

    public String getweeklyWinners() {
        return weeklyWinners;
    }

    public String getweeklyTimeRemaining() {
        return weeklyTimeRemaining;
    }

    public String getweeklyProgress() {
        return weeklyProgress;
    }

    public String getweeklyParticipantsRemaining() {
        return weeklyParticipantsRemaining;
    }

    public void setweeklyName(String weeklyName) {
        this.weeklyName = weeklyName;
    }

    public void setweeklyCapacity(String weeklyCapacity) {
        this.weeklyCapacity = weeklyCapacity;
    }

    public void setweeklyWinners(String weeklyWinners) {
        this.weeklyWinners = weeklyWinners;
    }

    public void setweeklyTimeRemaining(String weeklyTimeRemaining) {
        this.weeklyTimeRemaining = weeklyTimeRemaining;
    }

    public void setweeklyProgress(String weeklyProgress) {
        this.weeklyProgress = weeklyProgress;
    }

    public void setweeklyParticipantsRemaining(String weeklyParticipantsRemaining) {
        this.weeklyParticipantsRemaining = weeklyParticipantsRemaining;
    }


}