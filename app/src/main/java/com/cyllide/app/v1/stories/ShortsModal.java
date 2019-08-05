package com.cyllide.app.v1.stories;

public class ShortsModal {

    private String shortsDescription,ShortsName,ShortsImageUrl;

    public ShortsModal(String shortsDescription, String shortsName, String shortsImageUrl) {
        this.shortsDescription = shortsDescription;
        ShortsName = shortsName;
        ShortsImageUrl = shortsImageUrl;
    }

    public String getShortsDescription() {
        return shortsDescription;
    }

    public void setShortsDescription(String shortsDescription) {
        this.shortsDescription = shortsDescription;
    }

    public String getShortsName() {
        return ShortsName;
    }

    public void setShortsName(String shortsName) {
        ShortsName = shortsName;
    }

    public String getShortsImageUrl() {
        return ShortsImageUrl;
    }

    public void setShortsImageUrl(String shortsImageUrl) {
        ShortsImageUrl = shortsImageUrl;
    }
}
