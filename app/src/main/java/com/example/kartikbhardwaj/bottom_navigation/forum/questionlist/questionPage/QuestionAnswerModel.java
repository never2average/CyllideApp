package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.questionPage;

public class QuestionAnswerModel {
	private String answerDetail;
	private long dateStamp;
	private String answeredBy;
	private String answerID;

	public String getAnswerID() {
		return answerID;
	}

	public void setAnswerID(String answerID) {
		this.answerID = answerID;
	}

	public int getAnswerUpVotes() {
		return answerUpVotes;
	}

	public void setAnswerUpVotes(int answerUpVotes) {
		this.answerUpVotes = answerUpVotes;
	}


	private int answerUpVotes;

	public QuestionAnswerModel(String answerID,  String answerDetail, int answerUpVotes, String answeredBy, long dateStamp)
	{
		this.answerDetail=answerDetail;
		this.answeredBy=answeredBy;
		this.dateStamp=dateStamp;
		this.answerID = answerID;
		this.answerUpVotes = answerUpVotes;
	}

	public String getAnswerDetail() {
		return answerDetail;
	}

	public String getAnsweredBy() {
		return answeredBy;
	}

	public long getDateStamp() {
		return dateStamp;
	}

	public void setAnswerDetail(String answerDetail) {
		this.answerDetail = answerDetail;
	}

	public void setAnsweredBy(String answeredBy) {
		this.answeredBy = answeredBy;
	}

	public void setDateStamp(long dateStamp) {
		this.dateStamp = dateStamp;
	}
}


