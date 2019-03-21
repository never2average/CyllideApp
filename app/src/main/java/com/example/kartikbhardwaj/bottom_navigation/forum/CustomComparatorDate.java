package com.example.kartikbhardwaj.bottom_navigation.forum;

import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListModel;

import java.util.Comparator;

public class CustomComparatorDate implements Comparator<QuestionListModel> {
    @Override
    public int compare(QuestionListModel o1, QuestionListModel o2) {

        return o2.getQuestionLastUpdateTime().compareTo(o1.getQuestionLastUpdateTime());
    }
}
