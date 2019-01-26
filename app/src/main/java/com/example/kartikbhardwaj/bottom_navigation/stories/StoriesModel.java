package com.example.kartikbhardwaj.bottom_navigation.stories;

public class StoriesModel {
    private String storyName,storyDescription,storyImageURL;

    public StoriesModel(String storyName, String storyImageURL, String storyDescription)
    {
        this.storyDescription=storyDescription;
        this.storyImageURL=storyImageURL;
        this.storyName=storyName;
    }

    public String getStoryDescription() {
        return storyDescription;
    }

    public String getStoryName() {
        return storyName;
    }

    public String getStoryImageURL() {
        return storyImageURL;
    }

    public void setStoryDescription(String storyDescription) {
        this.storyDescription = storyDescription;
    }

    public void setStoryImageURL(String storyImageURL) {
        this.storyImageURL = storyImageURL;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }
}
