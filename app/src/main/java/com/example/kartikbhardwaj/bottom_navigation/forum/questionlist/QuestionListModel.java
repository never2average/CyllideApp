package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionListModel {
    String questionText;
    JSONObject questionID;
    Integer questionNumViews;
    Long questionLastUpdateTime;
    JSONArray questionTags;

    public QuestionListModel(String questionText, JSONObject questionID, Integer questionNumViews, Long questionLastUpdateTime, JSONArray questionTags) {
        this.questionText = questionText;
        this.questionID = questionID;
        this.questionNumViews = questionNumViews;
        this.questionLastUpdateTime = questionLastUpdateTime;
        this.questionTags = questionTags;
    }

    public JSONObject getQuestionID() {
        return questionID;
    }

    public void setQuestionID(JSONObject questionID) {
        this.questionID = questionID;
    }

    public Integer getQuestionNumViews() {
        return questionNumViews;
    }

    public void setQuestionNumViews(Integer questionNumViews) {
        this.questionNumViews = questionNumViews;
    }

    public Long getQuestionLastUpdateTime() {
        return questionLastUpdateTime;
    }

    public void setQuestionLastUpdateTime(Long questionLastUpdateTime) {
        this.questionLastUpdateTime = questionLastUpdateTime;
    }

    public JSONArray getQuestionTags() {
        return questionTags;
    }

    public void setQuestionTags(JSONArray questionTags) {
        this.questionTags = questionTags;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

}
