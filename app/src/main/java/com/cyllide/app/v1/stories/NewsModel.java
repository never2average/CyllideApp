package com.cyllide.app.v1.stories;

public class NewsModel {
    private String newsName,newsDescription,newsImageURL,newsDate, newsSource, newsUrl, newsAuthor;

    public NewsModel(String newsName, String newsImageURL, String newsDate, String newsSource, String newsDescription, String newsUrl, String newsAuthor)
    {
        this.newsDescription=newsDescription;
        this.newsUrl = newsUrl;
        this.newsImageURL=newsImageURL;
        this.newsName=newsName;
        this.newsDate=newsDate;
        this.newsSource=newsSource;
        this.newsAuthor=newsAuthor;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public String getNewsName() {
        return newsName;
    }

    public String getNewsImageURL() {
        return newsImageURL;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public String getNewsSource() {
        return newsSource;
    }

    public String getNewsUrl() {return newsUrl;}

    public String getNewsAuthor() {
        return newsAuthor;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public void setNewsImageURL(String newsImageURL) {
        this.newsImageURL = newsImageURL;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public void setNewsSource(String newsSource) {
        this.newsSource = newsSource;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }
}