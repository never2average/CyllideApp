package com.cyllide.app.v1.forum;

import com.cyllide.app.v1.forum.questionlist.questionPage.QuestionAnswerModel;

import java.util.Comparator;

public class CustomComparatorAnswerUpVotes implements Comparator<QuestionAnswerModel> {
    @Override
    public int compare(QuestionAnswerModel o2, QuestionAnswerModel o1) {
        Long o1long = new Long(o1.getAnswerUpVotes());
        Long o2long = new Long(o2.getAnswerUpVotes());
        return o1long.compareTo(o2long);


    }
}
