package com.example.kartikbhardwaj.bottom_navigation.Contests;

public class MonthlyModel {
    private String monthlyName,monthlyDescription,monthlyImageURL,monthlyDate, monthlySource;

    public MonthlyModel(String monthlyName, String monthlyDescription, String monthlyImageURL, String monthlyDate, String monthlySource)
    {
        this.monthlyDescription=monthlyDescription;
        this.monthlyImageURL=monthlyImageURL;
        this.monthlyName=monthlyName;
        this.monthlyDate=monthlyDate;
        this.monthlySource=monthlySource;
    }

    public String getMonthlyDescription() {
        return monthlyDescription;
    }

    public String getMonthlyName() {
        return monthlyName;
    }

    public String getMonthlyImageURL() {
        return monthlyImageURL;
    }

    public String getMonthlyDate() {
        return monthlyDate;
    }

    public String getMonthlySource() {
        return monthlySource;
    }

    public void setMonthlyDescription(String monthlyDescription) {
        this.monthlyDescription = monthlyDescription;
    }

    public void setMonthlyImageURL(String monthlyImageURL) {
        this.monthlyImageURL = monthlyImageURL;
    }

    public void setMonthlyName(String monthlyName) {
        this.monthlyName = monthlyName;
    }

    public void setMonthlyDate(String monthlyDate) {
        this.monthlyDate = monthlyDate;
    }

    public void setMonthlySource(String monthlySource) {
        this.monthlySource = monthlySource;
    }
}
