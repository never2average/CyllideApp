package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist;

import java.util.ArrayList;

public class QuestionListModel {
    String questionText;
    ArrayList<String> questionTags;

    public QuestionListModel(String questionText, ArrayList<String> questionTags) {
        this.questionText = questionText;
        this.questionTags = questionTags;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<String> getQuestionTags() {
        return questionTags;
    }

    public void setQuestionTags(ArrayList<String> questionTags) {
        this.questionTags = questionTags;
    }
}
