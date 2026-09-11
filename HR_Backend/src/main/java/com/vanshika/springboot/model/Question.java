package com.vanshika.springboot.model;

import java.util.Map;

public class Question {
	private String que;
	private Map<String,Integer>keywords;
	public Question(String que, Map<String, Integer> keywords) {
		super();
		this.que = que;
		this.keywords = keywords;
	}
	public String getQue() {
		return que;
	}
	public void setQue(String que) {
		this.que = que;
	}
	public Map<String, Integer> getKeywords() {
		return keywords;
	}
	public void setKeywords(Map<String, Integer> keywords) {
		this.keywords = keywords;
	}
	
	
}
