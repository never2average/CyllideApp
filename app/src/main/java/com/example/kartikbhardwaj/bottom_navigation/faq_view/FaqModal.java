package com.example.kartikbhardwaj.bottom_navigation.faq_view;

import android.widget.TextView;

public class FaqModal {

  private String Question;
    private String Answer;

    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public FaqModal(String question, String answer) {
        Question = question;
        Answer = answer;
    }

    public void setQuestion(String question) {

        Question = question;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}


