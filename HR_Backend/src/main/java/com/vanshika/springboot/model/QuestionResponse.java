package com.vanshika.springboot.model;

import java.util.Map;

public class QuestionResponse {
	private String que;
	private String category;
	private int level;
	private String key;
	public QuestionResponse(String que, String category, int level, String key) {
		super();
		this.que = que;
		this.category = category;
		this.level = level;
		this.key = key;
	}
	public String getQue() {
		return que;
	}
	public void setQue(String que) {
		this.que = que;
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
	
	
}
