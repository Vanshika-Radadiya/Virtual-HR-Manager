package com.vanshika.springboot.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
@Embeddable
public class CompId implements Serializable{
	private Integer userId;
	private Integer interviewId;
	
	
	
	public CompId() {
		super();
	}


	public CompId(Integer userId, Integer interviewId) {
		super();
		this.userId = userId;
		this.interviewId = interviewId;
	}
	
	
}
