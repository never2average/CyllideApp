package com.cyllide.app.v1.forum;

import com.cyllide.app.v1.forum.questionlist.QuestionListModel;

import java.util.Comparator;

public class CustomComparatorMostViewed implements Comparator<QuestionListModel> {
    @Override
    public int compare(QuestionListModel o1, QuestionListModel o2) {
        return o1.getQuestionNumViews().compareTo(o2.getQuestionNumViews());
    }
}
