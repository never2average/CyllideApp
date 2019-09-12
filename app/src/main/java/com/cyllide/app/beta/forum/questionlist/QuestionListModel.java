package com.cyllide.app.beta.forum.questionlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class QuestionListModel extends RealmObject {
    String questionText;
    //for saving in Realm
    String questionID;
    Integer questionNumViews;
    Long questionLastUpdateTime;
    //for saving in Realm
    String questionTags;

    @Ignore
    JSONArray JSONArrayQuestionTags;
    @Ignore
    JSONObject JSONObjectQuestionID;

    public JSONArray getJSONArrayQuestionTags() {
        return JSONArrayQuestionTags;
    }

    public void setJSONArrayQuestionTags(JSONArray JSONArrayQuestionTags) {
        this.JSONArrayQuestionTags = JSONArrayQuestionTags;
    }

    public JSONObject getJSONObjectQuestionID() {
        return JSONObjectQuestionID;
    }

    public void setJSONObjectQuestionID(JSONObject JSONObjectQuestionID) {
        this.JSONObjectQuestionID = JSONObjectQuestionID;
    }


    //Required for Realm
    public QuestionListModel(){

    }

    public QuestionListModel(String questionText, JSONObject questionID, Integer questionNumViews, Long questionLastUpdateTime, JSONArray questionTags) {
        this.questionText = questionText;
        this.questionID = questionID.toString();
        this.questionNumViews = questionNumViews;
        this.questionLastUpdateTime = questionLastUpdateTime;
        this.questionTags = questionTags.toString();

        JSONArrayQuestionTags = questionTags;
        JSONObjectQuestionID = questionID;

    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) throws JSONException {
        this.questionID = questionID;
        //USed when realm calls this method
        JSONObjectQuestionID = new JSONObject(questionID);
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

    public String getQuestionTags() {
        return questionTags;
    }

    public void setQuestionTags(String questionTags) throws JSONException {
        this.questionTags = questionTags;
        JSONArrayQuestionTags = new JSONArray(questionTags);
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }


}
