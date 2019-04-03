package com.cyllide.app.v1.stories;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NewsModel extends RealmObject {
    private String newsName;
    private String newsDescription;
    private String newsImageURL;
    private String newsUrl;
    private String newsAuthor;
//    private int newsID;
    @PrimaryKey
    private String mongoID;

    public String getMongoID() {
        return mongoID;
    }

    public void setMongoID(String mongoID) {
        this.mongoID = mongoID;
    }

    //Required for Realm
    public NewsModel(){

    }

    public NewsModel(String newsName, String newsImageURL, String newsDescription, String newsUrl, String newsAuthor, String mongoID)
    {
        this.newsDescription=newsDescription;
        this.newsUrl = newsUrl;
        this.newsImageURL=newsImageURL;
        this.newsName=newsName;
        this.newsAuthor=newsAuthor;
        this.mongoID = mongoID;
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

//    public int getNewsID() {
//        return newsID;
//    }

//    public void setNewsID(int newsID) {
//        this.newsID = newsID;
//    }
}
