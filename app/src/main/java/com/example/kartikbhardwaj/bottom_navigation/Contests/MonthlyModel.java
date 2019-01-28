package com.example.kartikbhardwaj.bottom_navigation.Contests;

public class MonthlyModel {
    private String monthlyName,monthlyCapacity,monthlyWinners,monthlyTimeRemaining, monthlyProgress, monthlyParticipantsRemaining;
    private Boolean isPremium;

    public MonthlyModel(String monthlyName, String monthlyCapacity, String monthlyWinners, String monthlyTimeRemaining, String monthlyProgress, String monthlyParticipantsRemaining, Boolean isPremium)
    {
        this.monthlyCapacity=monthlyCapacity;
        this.monthlyWinners=monthlyWinners;
        this.monthlyName=monthlyName;
        this.monthlyTimeRemaining=monthlyTimeRemaining;
        this.monthlyProgress=monthlyProgress;
        this.monthlyParticipantsRemaining=monthlyParticipantsRemaining;
        this.isPremium = isPremium;
    }

    public Boolean getPremium() {
        return isPremium;
    }

    public void setPremium(Boolean premium) {
        isPremium = premium;
    }

    public String getMonthlyName() {
        return monthlyName;
    }

    public String getMonthlyCapacity() {
        return monthlyCapacity;
    }

    public String getMonthlyWinners() {
        return monthlyWinners;
    }

    public String getMonthlyTimeRemaining() {
        return monthlyTimeRemaining;
    }

    public String getMonthlyProgress() {
        return monthlyProgress;
    }

    public String getMonthlyParticipantsRemaining() {
        return monthlyParticipantsRemaining;
    }

    public void setMonthlyName(String monthlyName) {
        this.monthlyName = monthlyName;
    }

    public void setMonthlyCapacity(String monthlyCapacity) {
        this.monthlyCapacity = monthlyCapacity;
    }

    public void setMonthlyWinners(String monthlyWinners) {
        this.monthlyWinners = monthlyWinners;
    }

    public void setMonthlyTimeRemaining(String monthlyTimeRemaining) {
        this.monthlyTimeRemaining = monthlyTimeRemaining;
    }

    public void setMonthlyProgress(String monthlyProgress) {
        this.monthlyProgress = monthlyProgress;
    }

    public void setMonthlyParticipantsRemaining(String monthlyParticipantsRemaining) {
        this.monthlyParticipantsRemaining = monthlyParticipantsRemaining;
    }


}

