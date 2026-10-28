package com.vanshika.springboot.model;

import java.io.Serializable;
import java.util.Objects;

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

	public Integer getUserId() {
		return userId;
	}

	public Integer getInterviewId() {
		return interviewId;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CompId compId)) {
			return false;
		}
		return Objects.equals(userId, compId.userId)
				&& Objects.equals(interviewId, compId.interviewId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, interviewId);
	}
}
