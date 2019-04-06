package com.cyllide.app.v1.stories;

public class StoriesModel {
    private String storyName,storyDescription,storyImageURL, contentAuthor,contentType, contentMarkdownLink;

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

    public StoriesModel(String storyName, String storyImageURL,
                        String storyDescription, String contentAuthor, String contentType, String contentMarkdownLink)
    {
        this.storyDescription=storyDescription;
        this.storyImageURL=storyImageURL;
        this.storyName=storyName;
        this.contentAuthor = contentAuthor;
        this.contentType = contentType;
        this.contentMarkdownLink = contentMarkdownLink;

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
