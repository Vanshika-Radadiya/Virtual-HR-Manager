package com.vanshika.springboot.model;

import java.util.Map;

public class AnswerResponse {
	private String answer;
	private String category;
	private int level;
	private String key;
	private int userId;
	private int interviewId;
	public AnswerResponse(String answer, String category, int level, String key, int userId, int interviewId) {
		super();
		this.answer = answer;
		this.category = category;
		this.level = level;
		this.key = key;
		this.userId = userId;
		this.interviewId = interviewId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getInterviewId() {
		return interviewId;
	}
	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}
	
	
	
}
