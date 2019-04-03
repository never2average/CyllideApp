package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.questionPage;

public class QuestionAnswerModel {
	private String answerDetail;
	private long dateStamp;
	private String answeredBy;
	private String answerID;
    private String profileURL;

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }


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
	//private int answerDownVotes;


	public QuestionAnswerModel(String answerID,  String answerDetail, int answerUpVotes,String answeredBy, long dateStamp, String profileURL)
	{
		this.answerDetail=answerDetail;
		this.answeredBy=answeredBy;
		this.dateStamp=dateStamp;
		this.answerID = answerID;
		this.answerUpVotes = answerUpVotes;
		this.profileURL = profileURL;
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


