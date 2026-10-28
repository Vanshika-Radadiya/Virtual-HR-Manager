package com.vanshika.springboot.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "interview_assignment", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"interviewId", "userId"})
})
public class InterviewAssignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long assignmentId;
	private int interviewId;
	private int userId;
	private LocalDateTime startDate;
	private boolean completed;

	protected InterviewAssignment() {
	}

	public InterviewAssignment(int interviewId, int userId, LocalDateTime startDate) {
		this.interviewId = interviewId;
		this.userId = userId;
		this.startDate = startDate;
	}

	public Long getAssignmentId() {
		return assignmentId;
	}

	public int getInterviewId() {
		return interviewId;
	}

	public int getUserId() {
		return userId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
}
