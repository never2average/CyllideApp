package com.cyllide.app.v1.portfolio;

public class LeaderBoardModel {
    String name;
    String points;
    String profileUri;
    Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public LeaderBoardModel(String name, String points, String profileUri, Integer position) {
        this.name = name;
        this.points = points;
        this.profileUri = profileUri;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }
}
