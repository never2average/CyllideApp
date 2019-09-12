package com.cyllide.app.beta.stories;

public class StoriesModel {
    private String storyName,storyDescription,storyImageURL, contentAuthor,contentType, contentMarkdownLink;
    public String mongoID;

    public String getContentColor() {
        return contentColor;
    }

    public void setContentColor(String contentColor) {
        this.contentColor = contentColor;
    }

    public String contentColor;

    public String getMongoID() {
        return mongoID;
    }

    public void setMongoID(String mongoID) {
        this.mongoID = mongoID;
    }

    public String getContentAuthor() {
        return contentAuthor;
    }

    public void setContentAuthor(String conentAuthor) {
        this.contentAuthor = conentAuthor;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentMarkdownLink() {
        return contentMarkdownLink;
    }

    public void setContentMarkdownLink(String contentMarkdownLink) {
        this.contentMarkdownLink = contentMarkdownLink;
    }

    public StoriesModel(String mongoID, String storyName, String storyImageURL,
                        String storyDescription, String contentAuthor, String contentType,
                        String contentColor, String contentMarkdownLink)
    {
        this.storyDescription=storyDescription;
        this.storyImageURL=storyImageURL;
        this.storyName=storyName;
        this.contentAuthor = contentAuthor;
        this.contentType = contentType;
        this.contentMarkdownLink = contentMarkdownLink;
        this.contentColor = contentColor;
        this.mongoID = mongoID;

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
