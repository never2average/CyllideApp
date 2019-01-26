package com.example.kartikbhardwaj.bottom_navigation.Contests;

public class WeeklyModel {
    private String weeklyName,weeklyDescription,weeklyImageURL;

    public WeeklyModel(String weeklyName, String weeklyImageURL, String weeklyDescription)
    {
        this.weeklyDescription=weeklyDescription;
        this.weeklyImageURL=weeklyImageURL;
        this.weeklyName=weeklyName;
    }

    public String getWeeklyDescription() {
        return weeklyDescription;
    }

    public String getWeeklyName() {
        return weeklyName;
    }

    public String getWeeklyImageURL() {
        return weeklyImageURL;
    }

    public void setWeeklyDescription(String weeklyDescription) {
        this.weeklyDescription = weeklyDescription;
    }

    public void setWeeklyImageURL(String weeklyImageURL) {
        this.weeklyImageURL = weeklyImageURL;
    }

    public void setWeeklyName(String weeklyName) {
        this.weeklyName = weeklyName;
    }
}
