package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.questionPage;

public class QuestionAnswerModel {
	private String answerDetail, dateStamp, answeredBy;

	public QuestionAnswerModel(String answerDetail, String answeredBy, String dateStamp)
	{
		this.answerDetail=answerDetail;
		this.answeredBy=answeredBy;
		this.dateStamp=dateStamp;
	}

	public String getAnswerDetail() {
		return answerDetail;
	}

	public String getAnsweredBy() {
		return answeredBy;
	}

	public String getDateStamp() {
		return dateStamp;
	}

	public void setAnswerDetail(String answerDetail) {
		this.answerDetail = answerDetail;
	}

	public void setAnsweredBy(String answeredBy) {
		this.answeredBy = answeredBy;
	}

	public void setDateStamp(String dateStamp) {
		this.dateStamp = dateStamp;
	}
}


