package com.vanshika.springboot.model;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "interview_details")
public class InterviewDetails {

	@EmbeddedId
	private CompId id;

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private LocalDateTime notificationSentAt;

	public InterviewDetails() {
	}

	public InterviewDetails(int interviewId, int userId, LocalDateTime startDate, LocalDateTime endDate) {
		this.id = new CompId(userId, interviewId);
		this.startDate = startDate;
		this.endDate = endDate;
		this.notificationSentAt = LocalDateTime.now();
	}

	public CompId getId() {
		return id;
	}

	public void setId(CompId id) {
		this.id = id;
	}

	public int getInterviewId() {
		return id != null ? id.getInterviewId() : 0;
	}

	public int getUserId() {
		return id != null ? id.getUserId() : 0;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public LocalDateTime getNotificationSentAt() {
		return notificationSentAt;
	}

	public void setNotificationSentAt(LocalDateTime notificationSentAt) {
		this.notificationSentAt = notificationSentAt;
	}
}
