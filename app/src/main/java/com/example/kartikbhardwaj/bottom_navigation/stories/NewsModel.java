package com.example.kartikbhardwaj.bottom_navigation.stories;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class NewsModel extends RealmObject {
    private String newsName;
    private String newsDescription;
    private String newsImageURL;
    private String newsUrl;
    private String newsAuthor;
    @PrimaryKey
    @Required
    private int newsID;

    //Required for Realm
    public NewsModel(){

    }

    public NewsModel(String newsName, String newsImageURL, String newsDescription, String newsUrl, String newsAuthor)
    {
        this.newsDescription=newsDescription;
        this.newsUrl = newsUrl;
        this.newsImageURL=newsImageURL;
        this.newsName=newsName;
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


    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public void setNewsAuthor(String newsAuthor) {
        this.newsAuthor = newsAuthor;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }
}
