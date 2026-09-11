package com.vanshika.springboot.model;

import org.hibernate.annotations.IdGeneratorType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name="Record")
public class Record {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long recordId;
	private int userId;
	private int interviewId;
	private String que;
	private String ans;
	private int score;
	

	public Record(int userId, int interviewId, String que, String ans, int score) {
		super();
		this.userId = userId;
		this.interviewId = interviewId;
		this.que = que;
		this.ans = ans;
		this.score = score;
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
	public String getQue() {
		return que;
	}
	public void setQue(String que) {
		this.que = que;
	}
	public String getAns() {
		return ans;
	}
	public void setAns(String ans) {
		this.ans = ans;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	
	
}
