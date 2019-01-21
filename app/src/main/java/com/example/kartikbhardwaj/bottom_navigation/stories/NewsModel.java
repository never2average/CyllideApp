package com.example.kartikbhardwaj.bottom_navigation.stories;

public class NewsModel {
    private String newsName,newsDescription,newsImageURL,newsDate, newsSource;

    public NewsModel(String newsName, String newsDescription, String newsImageURL, String newsDate, String newsSource)
    {
        this.newsDescription=newsDescription;
        this.newsImageURL=newsImageURL;
        this.newsName=newsName;
        this.newsDate=newsDate;
        this.newsSource=newsSource;
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
}
