package com.cyllide.app.beta.stories;

public class ShortsModel {

    private String shortsDescription, ShortsTitle,ShortsImageUrl;

    public ShortsModel(String shortsDescription, String shortsTitle, String shortsImageUrl) {
        this.shortsDescription = shortsDescription;
        ShortsTitle = shortsTitle;
        ShortsImageUrl = shortsImageUrl;
    }

    public String getShortsDescription() {
        return shortsDescription;
    }

    public void setShortsDescription(String shortsDescription) {
        this.shortsDescription = shortsDescription;
    }

    public String getShortsTitle() {
        return ShortsTitle;
    }

    public void setShortsTitle(String shortsTitle) {
        ShortsTitle = shortsTitle;
    }

    public String getShortsImageUrl() {
        return ShortsImageUrl;
    }

    public void setShortsImageUrl(String shortsImageUrl) {
        ShortsImageUrl = shortsImageUrl;
    }
}
